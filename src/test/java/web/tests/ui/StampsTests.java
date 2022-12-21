package web.tests.ui;

import com.google.gson.JsonElement;
import common.cnst.Tags;
import common.dto.User;
import io.qameta.allure.Owner;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import io.restassured.http.Cookies;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import web.objs.pages.site.MainPage;
import web.objs.pages.site.PersonalCabinetPage;
import web.objs.pages.site.StampsPage;
import web.ops.api.UserAPI;
import web.ops.logic.site.LoginPageOperations;
import web.ops.logic.site.MainPageOperations;
import web.ops.logic.site.PersonalCabinetOperations;
import web.ops.logic.site.StampsPageOperations;

public class StampsTests extends BaseUIClass {
    private static final Logger log = Logger.getLogger(OptionsWithoutLoginTests.class.getName());
    private final UserAPI userAPI = new UserAPI();
    private User sender = new User();
    private User recipient = new User();


    @Before
    public void setUp() {

        mainPage = new MainPage(driver);
        stampsPage = new StampsPage(driver);
        personalCabinetPage = new PersonalCabinetPage(driver);

        mainPageOperations = new MainPageOperations(driver);
        loginPageOperations = new LoginPageOperations(driver);
        stampsPageOperations = new StampsPageOperations(driver);
        personalCabinetOperations = new PersonalCabinetOperations(driver);

        sender.setPhone(testData.getVariable("sender"));
        sender.setPassWord("Qwe123123!");

        recipient.setPassWord("Qwe123123!");
        recipient.setPhone(testData.getVariable("recipient"));

    }

    /**
     * Разблокировать ассерты ниже, когда будет выполнена задача US_51358.
     * Нужно раз в РЦ докапываться до Игоря, Паши и исполнителя, иначе не пофиксят
     * На данный момент баланс не меняется после перевода и ассерты падают
     **/
    @Test
    @DisplayName("Перевод фишек")
    @Owner(value = "Максим")
    @Tag(Tags.CLEAN_CACHE)
    public void sendStampsTest() {
        Cookies sendersCookies = userAPI.loginAndGetCookies(sender);
        userAPI.post_setCategories(sendersCookies);
        int startSendersStampsBalance = stampsPageOperations.getStampsBalanceByRest(sendersCookies);

        Cookies recipientCookies = userAPI.loginAndGetCookies(recipient);
        userAPI.post_setCategories(recipientCookies);
        int startRecipientStampsBalance = stampsPageOperations.getStampsBalanceByRest(recipientCookies);

        if (startSendersStampsBalance < 15) {
            log.info("Sender has not enough stamps. Transfer stamps by rest");
            stampsPageOperations.transferStampsByRest(userAPI.loginAndGetCookies(recipient), sender.getPhone(), 100);
        }

        log.info("deleting all messages from the sender");
        personalCabinetOperations.deleteAllMessage(recipient);

        log.info("deleting all messages from the recipient");
        personalCabinetOperations.deleteAllMessage(sender);

        log.info("Login");
        mainPageOperations
                .goToLoginPage()
                .acceptCookies();
        loginPageOperations.logInWithPass(sender.getPhone().substring(1), sender.getPassWord());

        personalCabinetOperations.goToStampsFromLK();

        stampsPageOperations.transferStamps(recipient.getPhone().substring(2));
        Cookies sendersFinalCookies = userAPI.loginAndGetCookies(sender);
        log.info("Get final stamp`s balance");
        int finalSendersStampsBalance = stampsPageOperations.getStampsBalanceByRest(sendersFinalCookies);
        int finalRecipientStampsBalance = stampsPageOperations.getStampsBalanceByRest(recipientCookies);

        /**Разкоментить когда  пофиксят кэш, подробности читать над тестом**/
        Assert.assertEquals("Баланс не изменился после перевода", 5,
                startSendersStampsBalance - finalSendersStampsBalance);
        Assert.assertEquals("Баланс не изменился после перевода", 5,
                finalRecipientStampsBalance - startRecipientStampsBalance);


        log.info("Check subject and text in the sender's message");
        JsonElement sendersMessage =personalCabinetOperations.getMessages(sendersCookies).get(0);
        Assert.assertEquals("Перевод фишек", sendersMessage.getAsJsonObject().get("Subject").getAsString());
        Assert.assertEquals("Успешно выполнен перевод 5 фишек на номер: " + recipient.getPhone(),
                sendersMessage.getAsJsonObject().get("LastMessage").getAsString());

        JsonElement recipientMessage =personalCabinetOperations.getMessages(recipientCookies).get(0);
        Assert.assertEquals("Перевод фишек", recipientMessage.getAsJsonObject().get("Subject").getAsString());
        Assert.assertEquals("Получено 5 фишек c номера: " + sender.getPhone(),
                recipientMessage.getAsJsonObject().get("LastMessage").getAsString());

    }

    @Test
    @DisplayName("Перевод фишек самому себе")
    @Owner(value = "Максим")
    @Tag(Tags.CLEAN_CACHE)
    public void sendSelfStampsTest() {
        log.info("Login");
        mainPageOperations
                .goToLoginPage()
                .acceptCookies();
        loginPageOperations.logInWithPass(sender.getPhone().substring(1), sender.getPassWord());
        personalCabinetOperations.goToStampsFromLK();
        Cookies sendersCookies = userAPI.loginAndGetCookies(sender);

        if (stampsPageOperations.getStampsBalanceByRest(sendersCookies) < 5) {
            log.info("Not enough stamps. Stamps<5");
            Assert.fail();
        }

        stampsPageOperations.transferSelfStamps(sender.getPhone().substring(2));
        log.info("check Self Stamps Transfer Blocked");
        Assert.assertEquals("Перевод самому себе запрещен", stampsPage.alertMessage().getText());
    }

}

