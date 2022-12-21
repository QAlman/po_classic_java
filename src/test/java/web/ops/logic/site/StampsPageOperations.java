package web.ops.logic.site;


import com.codeborne.selenide.Condition;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import web.objs.pages.site.StampsPage;
import web.ops.api.UserAPI;

import java.util.Objects;

import static com.codeborne.selenide.Selenide.sleep;
import static web.ops.helpers.CommonHelpers.deleteString;
import static web.ops.util.datautils.ConfigPropertiesUtil.getProperty;

public class StampsPageOperations {

    private final StampsPage stampsPage;
    private final Logger log = Logger.getLogger(this.getClass().getName());
    private final UserAPI userAPI = new UserAPI();
    WebDriver driver;

    public StampsPageOperations(WebDriver driver) {

        this.stampsPage = new StampsPage(driver);
        this.driver = driver;
    }

    public int getStampsBalance() {
        log.info("Get stamp`s balance");
        return Integer.parseInt(Objects.requireNonNull(stampsPage.stampsBalanceField().getText())
                .replaceAll("[^\\d]", ""));
    }

    public int getStampsValue() {
        log.info("Get stamp`s value");
        return Integer.parseInt(Objects.requireNonNull(stampsPage.stampsValueField().getText())
                .replaceAll("[^\\d]", ""));

    }

    public void checkPlusMinusButton() {
        log.info("Check plus button");
        int stampsBalance = getStampsBalance();
        int stampsValue = getStampsValue();
        if (stampsBalance - stampsValue >= 10) {
            log.info("tap plus button");
            stampsPage.plusButton().click();
            int resultValue = getStampsValue();
            Assert.assertEquals(5, resultValue - stampsValue);
            log.info("Check minus button");
            log.info("tap minus button");
            stampsPage.minusButton().click();
            int finalResultValue = getStampsValue();
            Assert.assertEquals(5, finalResultValue);
        } else {
            log.info("Not enough stamps for check plus and minus button");
        }
    }

    public void transferStamps(String recipientNumber) {
        log.info("tap button give stamps");
        stampsPage.giveStampsButton().waitUntil(Condition.visible, Long.parseLong(getProperty("driver.wait.timeout"))).click();

        log.info("input phone number");
        stampsPage.phoneNumberInputField().waitUntil(Condition.visible, Long.parseLong(getProperty("driver.wait.timeout"))).sendKeys("9" + recipientNumber);

        log.info("Get start stamp`s balance");
        checkPlusMinusButton();

        log.info("tap next");
        stampsPage.nextButton().click();

        log.info("input incorrect verification code");
        stampsPage.codeNumberInputField().sendKeys("000002");

        log.info("tap confirm");
        stampsPage.confirmButton().click();

        log.info("check display incorrect message");
        Assert.assertTrue(String.valueOf(stampsPage.incorrectCodeMessage().isDisplayed()), true);

        log.info("input verification code");
        stampsPage.codeNumberInputField().sendKeys(deleteString);
        stampsPage.codeNumberInputField().sendKeys("000000");

        log.info("tap confirm");
        stampsPage.confirmButton().click();

        stampsPage.codeNumberInputField().shouldNotBe(Condition.visible);
    }

    public void transferSelfStamps(String recipientNumber) {
        log.info("tap button give stamps");
        stampsPage.giveStampsButton().click();

        log.info("input phone number");
        stampsPage.phoneNumberInputField().waitUntil(Condition.visible, Long.parseLong(getProperty("driver.wait.timeout"))).sendKeys("9" + recipientNumber);

        log.info("Get start stamp`s balance");
        checkPlusMinusButton();

        log.info("tap next");
        stampsPage.nextButton().click();
    }

    public int getStampsBalanceByRest(Cookies cookies) {
        log.info("Get stamps by rest");
        userAPI.get_getStampsBalance(cookies);
        sleep(5000);
        Response responses = userAPI.get_getStampsBalance(cookies);

        return Integer.parseInt((responses.getBody().asString()));
    }

    public void transferStampsByRest(Cookies senderCookies, String recipientNumber, int stamps) {
//        Response code = userAPI.post_requestValidationCodeForStamps(senderCookies, recipientNumber, stamps);
        userAPI.post_transferStamps(senderCookies, recipientNumber, stamps);

    }
}


