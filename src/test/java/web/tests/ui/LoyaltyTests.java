package web.tests.ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import common.cnst.Tags;
import common.dto.User;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import io.restassured.http.Cookies;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import web.objs.pages.remotesystems.Kibana;
import web.objs.pages.site.*;
import web.ops.api.UserAPI;
import web.ops.logic.site.LoginPageOperations;
import web.ops.logic.site.LoyaltyPageOperations;
import web.ops.logic.site.MainPageOperations;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.*;
import static web.ops.helpers.CommonHelpers.deleteString;
import static web.ops.util.datautils.ConfigPropertiesUtil.getProperty;
import static web.ops.util.datautils.SQLUtil.queryWithoutResult;

public class LoyaltyTests extends BaseUIClass {


    private final UserAPI userAPI = new UserAPI();


    @Before
    public void setUp() {
        mainPage = new MainPage(driver);
        loyaltyPage = new LoyaltyPage(driver);
        loginPage = new LoginPage(driver);
        epiServerPage = new EpiServerPage(driver);
        personalCabinetPage = new PersonalCabinetPage(driver);

        mainPageOperations = new MainPageOperations(driver);
        loginPageOperations = new LoginPageOperations(driver);
        loyaltyPageOperations = new LoyaltyPageOperations(driver);
    }

    @Test
    @DisplayName("Поп-ап ПРО юзера в НПЛ")
    @Owner(value = "Максим")
    @Tag(Tags.CLEAN_CACHE)
    public void proUserNPLPopUpTest() {
        String proUserNumber = testData.getVariable("proUserNumber");

        log.info("Login as PRO user");
        mainPageOperations
                .goToLoginPage()
                .acceptCookies();
        loginPageOperations.logInWithPass(proUserNumber, "Qwe123123!");

        loyaltyPage.loyaltyBanner().waitUntil(Condition.visible, Long.parseLong(getProperty("driver.wait.timeout"))).isDisplayed();
        log.info("Get UserID");
        String userID = (Objects.requireNonNull(loginPage.getUserID().getAttribute("innerText"))
                .replaceAll("(SelectedStore.*)|(\\D)", ""));
        Assert.assertNotEquals("UserID not found check xpath", "", userID);

        log.info("Update IsShownProUser");
        queryWithoutResult("UPDATE [stagesite-crm].[dbo].[tblUserLoyaltyStatus]\n" +
                "   SET [IsShownProUser] = 0\n" +
                " where ClientId =" + userID);

        log.info("Go to loyalty page");
        loyaltyPage.loyaltyBanner().click();
        Assert.assertTrue("pro popUp not displaed", loyaltyPage.proPopUp().isDisplayed());

        log.info("Go to condition of NPL");
        loyaltyPage.conditionsOfProgram().click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        log.info("Check condition of NPL page");
        Assert.assertEquals("Правила программы лояльности \"ВСЁ ВКЛЮЧЕНО!\"", loyaltyPage.articleTitle().getText());
        driver.switchTo().window(tabs.get(0));

        log.info("Click clear button");
        loyaltyPage.clearButton().click();

        log.info("Check NPL page does not contain loyalty info page");
        Assert.assertFalse(loyaltyPage.loyaltyInfoTabRight().isDisplayed());

        log.info("Check bonus game is not available. Redirect loyalty lk");
        open("https://stage.lentatest.com/loyalty/loyalty-lk/bonus-game/");
        Assert.assertTrue(loyaltyPage.loyaltyBalanceTabLeft().isDisplayed());

        log.info("Check select categories is not available. Redirect loyalty lk");
        open("https://stage.lentatest.com/loyalty/loyalty-lk/select-categiries/");
        Assert.assertTrue(loyaltyPage.loyaltyBalanceTabLeft().isDisplayed());

    }

    @Test
    @DisplayName("туториал НПЛ")
    @Owner(value = "Максим")
    @Tag(Tags.CLEAN_CACHE)
    public void tutorialNPLTest() {
        log.info("Create user with city by rest");
        User user = userAPI.registerUserWithDefaultCity();
        log.info("Set categories by user");
        Cookies cookies = userAPI.loginAndGetCookies(user);
        userAPI.post_setCategories(cookies);
        log.info("Created user");

        log.info("Login " + user.getPhone());
        mainPageOperations
                .goToLoginPage()
                .acceptCookies();
        loginPageOperations.logInWithPass(user.getPhone().substring(1), user.getPassWord());

        log.info("Go to loyalty page");
        loyaltyPage.loyaltyBanner().click();

        log.info("Close email pop-up");
        loyaltyPage.popUpClose().click();


        log.info("Check tutorial NPL slide one");
        sleep(2000);
        Assert.assertEquals("Механика бонусной игры", loyaltyPage.tutorialHeader().getText());
        Assert.assertEquals("Выберите категории, которые чаще всего покупаете", loyaltyPageOperations.getTutorialText(1));
//        Assert.assertTrue(loyaltyPage.tutorialArrowNext().isDisplayed());
//        Assert.assertFalse(loyaltyPage.tutorialArrowPrev().isDisplayed());


        log.info("Click button next");
        loyaltyPage.buttonOnTutorial().click();
        sleep(2000);

        log.info("Check tutorial NPL slide two");
//        Assert.assertTrue(loyaltyPage.tutorialArrowPrev().isDisplayed());
        Assert.assertEquals("Ежемесячно совершайте покупки и выполняйте задания уровня", loyaltyPageOperations.getTutorialText(2));

        log.info("Click arrow next");
//        loyaltyPage.tutorialArrowNext().click();
        loyaltyPage.buttonOnTutorial().click();
        sleep(2000);

        log.info("Check tutorial NPL slide three");
        Assert.assertEquals("Получайте 5% баллами за каждую категорию", loyaltyPageOperations.getTutorialText(3));
//        Assert.assertFalse(loyaltyPage.tutorialArrowNext().isDisplayed());

//
//        log.info("Click arrow prev");
//        loyaltyPage.tutorialArrowPrev().click();
//        sleep(2000);
//
//        log.info("Check tutorial NPL slide two");
//        Assert.assertEquals("Ежемесячно совершайте покупки и выполняйте задания уровня", loyaltyPageOperations.getTutorialText(2));
//
//        log.info("Click button next");
//        loyaltyPage.buttonOnTutorial().click();
//        sleep(2000);

        log.info("Click button clear");
        loyaltyPage.buttonOnTutorial().click();
        sleep(2000);


        log.info("Check tutorial is closed");
        Assert.assertFalse(loyaltyPage.tutorialHeader().isDisplayed());

        log.info("Refresh page");
        refresh();

        log.info("Close email pop-up");
        loyaltyPage.popUpClose().click();

        log.info("Check tutorial is closed");
        Assert.assertFalse(loyaltyPage.tutorialHeader().isDisplayed());

        log.info("Open tutorial from bonus game");
        loyaltyPage.bonusGameButtonFromLoyaltyLK().click();
        loyaltyPage.tutorialNPLFromBonusGame().click();

        log.info("Check tutorial NPL slide one");
        sleep(2000);
        Assert.assertEquals("Механика бонусной игры", loyaltyPage.tutorialHeader().getText());

        log.info("Close tutorial by cross");
        loyaltyPage.popUpClose().click();

        log.info("Check tutorial is closed");
        Assert.assertFalse(loyaltyPage.tutorialHeader().isDisplayed());
    }

    @Test
    @DisplayName("Подтверждение емейла через поп-ап в НПЛ")
    @Owner(value = "Максим")
    @Tag(Tags.CLEAN_CACHE)
    public void confirmEmailTest() {
        log.info("Create user with city by rest");
        defaultUser = userAPI.registerUserWithDefaultCity();
        log.info("Set categories by user");
        Cookies cookies = userAPI.loginAndGetCookies(defaultUser);
        userAPI.post_setCategories(cookies);
        log.info("Created user");

        log.info("Login " + defaultUser.getPhone());
        mainPageOperations
                .goToLoginPage()
                .acceptCookies();
        loginPageOperations.logInWithPass(defaultUser.getPhone().substring(2), defaultUser.getPassWord());

        log.info("Go to loyalty page");
        loyaltyPage.loyaltyBanner().click();


        log.info("Send email");
        loyaltyPage.emailInputField().sendKeys(deleteString);
        loyaltyPage.emailInputField().sendKeys(defaultUser.getEmail());
        /**Ожидаем пока пройдет 120 секунд от предыдущей отправки емейла, во время регистрации**/
//        sleep(120000);
        loyaltyPage.sendAgainButton().click();


        log.info("Check email sent successfully");
        Assert.assertEquals("Письмо отправлено на " + defaultUser.getEmail(),
                loyaltyPage.headerEmailPopUpSent().waitUntil(Condition.visible, Long.parseLong(getProperty("driver.wait.timeout"))).getText());

        log.info("Open kibana in second tab");
        ((JavascriptExecutor) driver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        open("https://kibana.lentatest.com/s/lenta-app/app/kibana#/discover?_g=()"
                + "&_a=(columns:!(_source),index:a721b7d0-e132-11e9-b382-db3cafc99065,interval:auto,query:(language:kuery,query:'\""
                + defaultUser.getEmail() + "\"%20and%20\"Email%20Gateway%20stub\"'),sort:!('@timestamp',desc))");


        log.info("Login to kibana");
        Kibana.login(kibanaUser, kibanaPassword);


        log.info("Find message with confirm link");
        WebElement kibanaMessage = null;
        try {
            kibanaMessage = Kibana.getMessage(driver);
        } catch (InterruptedException e) {
            log.info("Kibana crash");
        }
        if (kibanaMessage == null) {
            log.info("Kibana hasn't message");
            Assert.fail();
        }

        log.info("Go to confirm link " + kibanaMessage.getText().replaceAll(".*link: | \\w.*", ""));
        open(kibanaMessage.getText().replaceAll(".*link: | \\w.*", ""));

        log.info("Check email confirm");
        Assert.assertEquals("Email успешно подтверждён", loyaltyPage.emailConfirm().waitUntil(Condition.visible, Long.parseLong(getProperty("driver.wait.timeout"))).getText());

        log.info("Open first tab and refresh");
        driver.switchTo().window(tabs.get(0));
        driver.navigate().refresh();

        log.info("Check email confirm and email`s pop-up doesn't open anymore");
        Assert.assertEquals("Механика бонусной игры", loyaltyPage.headerTutorialPopUp().waitUntil(Condition.visible, Long.parseLong(getProperty("driver.wait.timeout"))).getText());

    }

    @Test
    @DisplayName("выбор категорий после регистрации без выбора категорий")
    @Owner(value = "Максим")
    @Tag(Tags.CLEAN_CACHE)
    public void selectCategoriesAfterRegistrationWitOutCategoriesTest() {
        log.info("Create user with city by rest");
        defaultUser = userAPI.registerUserWithDefaultCity();
        log.info("Created user");


        log.info("Login " + defaultUser.getPhone());
        mainPageOperations
                .goToLoginPage()
                .acceptCookies();
        loginPageOperations.logInWithPass(defaultUser.getPhone().substring(2), defaultUser.getPassWord());

        log.info("Get list selected categories");
        List<SelenideElement> selectedCategoriesBeforeChange = loyaltyPageOperations.getListSelectedCategories(loyaltyPage.getCategoriesMap());

        log.info("Check categories is not select");
        selectedCategoriesBeforeChange.forEach(Assert::assertNull);

        log.info("Get list five unique categories");
        List<SelenideElement> unSelectedFiveUniqueCategoriesBeforeChange = loyaltyPageOperations.getFiveUniqueRandomUnSelectedElement(loyaltyPage.getCategoriesMap());
        unSelectedFiveUniqueCategoriesBeforeChange.iterator().forEachRemaining(element -> log.info(element));


        log.info("Select five categories");
        for (SelenideElement selenideElement : unSelectedFiveUniqueCategoriesBeforeChange) {
            selenideElement.click();
        }

        log.info("Get list categories after select");
        List<SelenideElement> selectedCategoriesAfterChange = loyaltyPageOperations.getListSelectedCategories(loyaltyPage.getCategoriesMap());
        selectedCategoriesAfterChange.iterator().forEachRemaining(element -> log.info(element));

        log.info("Check selected categories");
        List<String> listNamesSelectedCategoriesAfterChange = new LinkedList<>();
        for (int i = 0; i < unSelectedFiveUniqueCategoriesBeforeChange.size(); i++) {
            Assert.assertEquals(unSelectedFiveUniqueCategoriesBeforeChange.get(i), selectedCategoriesAfterChange.get(i));
            listNamesSelectedCategoriesAfterChange.add(selectedCategoriesAfterChange.get(i).getText());
        }

        log.info("Save categories");
        loyaltyPage.acceptCategories().click();

        checkActuallySaveCategories(listNamesSelectedCategoriesAfterChange);

    }


    @Test
    @DisplayName("ИЗменение одной категории на будущий месяц")
    @Owner(value = "Максим")
    @Tag(Tags.CLEAN_CACHE)
    public void changeOneCategoriesTests() {
        log.info("Create user with city by rest");
        defaultUser = userAPI.registerUserWithDefaultCity();
        log.info("Set categories by user");
        Cookies cookies = userAPI.loginAndGetCookies(defaultUser);
        userAPI.post_setCategories(cookies);
        log.info("Created user");

        log.info("Login");
        mainPageOperations
                .goToLoginPage()
                .acceptCookies();
        loginPageOperations.logInWithPass(defaultUser.getPhone().substring(2), defaultUser.getPassWord());

        log.info("Go to change categories screen");
        personalCabinetPage.bonusGameFromLK().click();
        loyaltyPage.changeCategoriesButtonInBonusGame().click();

        loyaltyPageOperations.selectFiveCategories();
        loyaltyPage.changeCategoriesButtonInBonusGame().click();

        log.info("Get list selected categories");
        List<SelenideElement> selectedCategoriesBeforeChange = loyaltyPageOperations.getListSelectedCategories(loyaltyPage.getCategoriesMap());
        selectedCategoriesBeforeChange.iterator().forEachRemaining(element -> log.info(element));

        log.info("Get list five unique categories");
        List<SelenideElement> unSelectedFiveUniqueCategoriesBeforeChange = loyaltyPageOperations.getFiveUniqueRandomUnSelectedElement(loyaltyPage.getCategoriesMap());

        log.info("Unselect category number 1");
        selectedCategoriesBeforeChange.get(0).click();

        log.info("Choose new category");
        unSelectedFiveUniqueCategoriesBeforeChange.get(0).click();

        log.info("Get list categories after select");
        List<SelenideElement> selectedCategoriesAfterChange = loyaltyPageOperations.getListSelectedCategories(loyaltyPage.getCategoriesMap());
        selectedCategoriesAfterChange.iterator().forEachRemaining(element -> log.info(element));

        List<String> listNamesSelectedCategoriesAfterChange = new LinkedList<>();

        log.info("Check selected categories");
        Assert.assertNotEquals(selectedCategoriesBeforeChange.get(0), selectedCategoriesAfterChange.get(0));
        listNamesSelectedCategoriesAfterChange.add(selectedCategoriesAfterChange.get(0).getText());

        for (int i = 1; i < selectedCategoriesBeforeChange.size(); i++) {
            Assert.assertEquals(selectedCategoriesBeforeChange.get(i), selectedCategoriesAfterChange.get(i));
            listNamesSelectedCategoriesAfterChange.add(selectedCategoriesAfterChange.get(i).getText());
        }


        log.info("Save categories");
        loyaltyPage.acceptCategories().click();

        log.info("Go to change categories screen");
        loyaltyPage.changeCategoriesButtonInBonusGame().click();

        checkActuallySaveCategories(listNamesSelectedCategoriesAfterChange);
    }

    @Test
    @DisplayName("Проверка кнопки выбора категорий")
    @Description(value = "1) Кнопка не нажимается если выбрано меньше 5 категорий " +
            "2) Кнопка не нажимается если выбраны те же категории " +
            "3) Кнопка нажимается если категории те же, но в другом порядке " +
            "4) ")
    @Owner(value = "Максим")
    @Tag(Tags.CLEAN_CACHE)
    public void checkAcceptCategoriesButtonAndChangeFiveCategoriesTests() {
        log.info("Create user with city by rest");
        defaultUser = userAPI.registerUserWithDefaultCity();
        log.info("Set categories by user");
        Cookies cookies = userAPI.loginAndGetCookies(defaultUser);
        userAPI.post_setCategories(cookies);
        log.info("Created user");

        log.info("Login");
        mainPageOperations
                .goToLoginPage()
                .acceptCookies();
        loginPageOperations.logInWithPass(defaultUser.getPhone().substring(2), defaultUser.getPassWord());

        log.info("Go to change categories screen");
        personalCabinetPage.bonusGameFromLK().click();
        loyaltyPage.changeCategoriesButtonInBonusGame().click();

        loyaltyPageOperations.selectFiveCategories();
        loyaltyPage.changeCategoriesButtonInBonusGame().click();

        log.info("Get list selected categories");
        List<SelenideElement> selectedCategoriesBeforeChange = loyaltyPageOperations.getListSelectedCategories(loyaltyPage.getCategoriesMap());
        selectedCategoriesBeforeChange.iterator().forEachRemaining(element -> log.info(element));

        log.info("Get list five unique unselected categories");
        List<SelenideElement> unSelectedFiveUniqueCategoriesBeforeChange = loyaltyPageOperations.getFiveUniqueRandomUnSelectedElement(loyaltyPage.getCategoriesMap());
        unSelectedFiveUniqueCategoriesBeforeChange.iterator().forEachRemaining(element -> log.info(element));

        List<String> listNamesSelectedCategoriesAfterChange = new LinkedList<>();


        log.info("Unselect category number 1 and 2");
        log.info(selectedCategoriesBeforeChange);
        selectedCategoriesBeforeChange.get(0).click();
        selectedCategoriesBeforeChange.get(1).click();

        log.info("Select category number 2 then 1");
        selectedCategoriesBeforeChange.get(1).click();
        selectedCategoriesBeforeChange.get(0).click();


        log.info("Check button is clickable");
        Assert.assertNotEquals("true", loyaltyPage.acceptCategories().getAttribute("Disabled"));


        log.info("Unselect category number 1 and 2");
        selectedCategoriesBeforeChange.get(0).click();
        selectedCategoriesBeforeChange.get(1).click();

        log.info("Select category number 1 then 2");
        selectedCategoriesBeforeChange.get(0).click();
        selectedCategoriesBeforeChange.get(1).click();


        log.info("Check button is NOT clickable");
        Assert.assertEquals("true", loyaltyPage.acceptCategories().getAttribute("Disabled"));


        log.info("Unselect all categories");
        for (int i = 0; i < 5; i++) {
            selectedCategoriesBeforeChange.get(i).click();
        }


        log.info("Select five new categories");
        for (int i = 0; i < unSelectedFiveUniqueCategoriesBeforeChange.size() - 1; i++) {
            unSelectedFiveUniqueCategoriesBeforeChange.get(i).click();
            listNamesSelectedCategoriesAfterChange.add(unSelectedFiveUniqueCategoriesBeforeChange.get(i).getText());
            Assert.assertEquals("true", loyaltyPage.acceptCategories().getAttribute("Disabled"));
        }

        unSelectedFiveUniqueCategoriesBeforeChange.get(4).click();
        listNamesSelectedCategoriesAfterChange.add(unSelectedFiveUniqueCategoriesBeforeChange.get(4).getText());

        log.info("Save categories");
        loyaltyPage.acceptCategories().click();

        log.info("Go to change categories screen");
        loyaltyPage.changeCategoriesButtonInBonusGame().click();

        checkActuallySaveCategories(listNamesSelectedCategoriesAfterChange);
    }

    @Test
    @DisplayName("Изменения порядка категорий на следующий месяц")
    @Owner(value = "Максим")
    @Tag(Tags.CLEAN_CACHE)
    public void reorderingCategoriesTest() {
        log.info("Create user with city by rest");
        defaultUser = userAPI.registerUserWithDefaultCity();
        log.info("Set categories by user");
        Cookies cookies = userAPI.loginAndGetCookies(defaultUser);
        userAPI.post_setCategories(cookies);
        log.info("Created user");


        log.info("Login");
        mainPageOperations
                .goToLoginPage()
                .acceptCookies();
        loginPageOperations.logInWithPass(defaultUser.getPhone().substring(2), defaultUser.getPassWord());

        log.info("Go to change categories screen");
        personalCabinetPage.bonusGameFromLK().click();
        loyaltyPage.changeCategoriesButtonInBonusGame().click();
        loyaltyPageOperations.selectFiveCategories();
        loyaltyPage.changeCategoriesButtonInBonusGame().click();

        log.info("Get list selected categories");

        List<SelenideElement> selectedCategoriesBeforeChange = loyaltyPageOperations.getListSelectedCategories(loyaltyPage.getCategoriesMap());
        selectedCategoriesBeforeChange.iterator().forEachRemaining(element -> log.info(element));

        log.info("Get list five unique unselected categories");
        List<SelenideElement> unSelectedFiveUniqueCategoriesBeforeChange = loyaltyPageOperations.getFiveUniqueRandomUnSelectedElement(loyaltyPage.getCategoriesMap());
        unSelectedFiveUniqueCategoriesBeforeChange.iterator().forEachRemaining(element -> log.info(element));

        List<String> listNamesSelectedCategoriesAfterChange = new LinkedList<>();


        log.info("Unselect category number 1 and 2");
        selectedCategoriesBeforeChange.get(0).click();
        selectedCategoriesBeforeChange.get(1).click();
        Assert.assertEquals("true", loyaltyPage.acceptCategories().getAttribute("Disabled"));

        log.info("Select category number 2 then 1");
        selectedCategoriesBeforeChange.get(1).click();
        listNamesSelectedCategoriesAfterChange.add(selectedCategoriesBeforeChange.get(1).getText());
        selectedCategoriesBeforeChange.get(0).click();
        listNamesSelectedCategoriesAfterChange.add(selectedCategoriesBeforeChange.get(0).getText());

        for (int i = 2; i < selectedCategoriesBeforeChange.size(); i++) {
            listNamesSelectedCategoriesAfterChange.add(selectedCategoriesBeforeChange.get(i).getText());
        }

        log.info("Save categories");
        loyaltyPage.acceptCategories().click();

        log.info("Go to change categories screen");
        loyaltyPage.changeCategoriesButtonInBonusGame().click();

        checkActuallySaveCategories(listNamesSelectedCategoriesAfterChange);
    }

    public void checkActuallySaveCategories(List<String> listNamesSelectedCategoriesAfterChange) {
        log.info("Get actually save categories");
        loginPage.categoriesPage().waitUntil(Condition.visible, Long.parseLong(getProperty("driver.wait.timeout"))).isDisplayed();
        List<SelenideElement> actuallySaveCategories = loyaltyPageOperations.getListSelectedCategories(loyaltyPage.getCategoriesMap());
        actuallySaveCategories.iterator().forEachRemaining(element -> log.info(element));

        log.info("Compare actually save categories and categories after select");
        for (int i = 0; i < listNamesSelectedCategoriesAfterChange.size(); i++) {
            Assert.assertEquals(listNamesSelectedCategoriesAfterChange.get(i), actuallySaveCategories.get(i).getText());

        }
    }
}

