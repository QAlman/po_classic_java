package web.tests.ui;

import com.codeborne.selenide.Condition;
import common.builder.UserBuilder;
import common.cnst.Tags;
import common.dto.User;
import common.service.UserService;
import io.qameta.allure.Owner;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import web.objs.pages.site.LoginPage;
import web.objs.pages.site.LoyaltyPage;
import web.objs.pages.site.MainPage;
import web.objs.pages.site.PersonalCabinetPage;
import web.ops.api.UserAPI;
import web.ops.logic.site.LoginPageOperations;
import web.ops.logic.site.MainPageOperations;

import static com.codeborne.selenide.Selenide.open;
import static web.ops.util.datautils.ConfigPropertiesUtil.getProperty;


public class RegistrationTests extends BaseUIClass {
    private static final Logger log = Logger.getLogger(OptionsWithoutLoginTests.class.getName());
    private final UserService userService = new UserService();

    private void createUser() {
        UserBuilder userBuilder = new UserBuilder();
        String phone = userService.generatePhoneNumber();

        defaultUser = userBuilder
                .withFirstName("Пользак")
                .withLastName("НовойПЛ")
                .withBirthDate("28081993")
                .withEmail(phone + "@mail.ru")
                .withPhone(phone)
                .withDefaultPassword()
                .withSex("W")
                .withUserId("000000")
                .execute();
    }

    @Before
    public void setUp() {

        loginPage = new LoginPage(driver);
        personalCabinetPage = new PersonalCabinetPage(driver);
        mainPage = new MainPage(driver);

        mainPageOperations = new MainPageOperations(driver);
        loginPageOperations = new LoginPageOperations(driver);
        loyaltyPage = new LoyaltyPage(driver);

        createUser();

    }

    @Test
    @DisplayName("Стандартный флоу регистрации")
    @Owner(value = "Максим")
    @Tag(Tags.CLEAN_CACHE)
    public void standardRegisterUserTest() {
        mainPageOperations.initiateStandardRegistrationProcess()
                .acceptCookies();
        loginPageOperations.registrationFlow(defaultUser);
        categoriesSelect();
        selectCityAfterRegistration();
    }

    @Test
    @DisplayName("Регистрация без выбора категорий")
    @Owner(value = "Максим")
    @Tag(Tags.CLEAN_CACHE)
    public void registerWithOutCategoriesTest() {
        mainPageOperations.initiateStandardRegistrationProcess()
                .acceptCookies();
        loginPageOperations.registrationFlow(defaultUser);
        skipCategoriesSelect();
        Assert.assertTrue(mainPage.getHeader().getPersonalCabinetButton().returnAsElement().isDisplayed());
    }

    @Test
    @DisplayName("Регистрация  на планшете")
    @Owner(value = "Максим")
    @Tag(Tags.CLEAN_CACHE)
    public void registrationByTablet() {
        open(host + "/ppk");
        loginPage.reRegistration().click();
        loginPageOperations.registrationFlowForTablet(defaultUser);
        loginPage.name().waitUntil(Condition.not(Condition.visible), Long.parseLong(getProperty("driver.wait.timeout"))).isDisplayed();
        loginPage.submitButton().click();
        Assert.assertEquals("Спасибо за регистрацию! Ваша Карта №1i выпущена", loginPage.registrationEndTitleText().getText());
        loginPage.submitButton().click();
        Assert.assertTrue(loginPage.reRegistration().waitUntil(Condition.visible, Long.parseLong(getProperty("driver.wait.timeout"))).isDisplayed());

    }

    @Test
    @DisplayName("Подтверждение регистрации  после регистрации на планшете")
    @Owner(value = "Максим")
    @Tag(Tags.CLEAN_CACHE)
    public void confirmRegistrationByTablet() {
        UserAPI userAPI = new UserAPI();
        User user = userAPI.registerUserByTablet();
        log.info(user.getPhone());
        mainPageOperations.initiateStandardRegistrationProcess()
                .acceptCookies();
        loginPage.phoneNumberInputField().sendKeys(user.getPhone().substring(1));
        loginPage.nextButton().click();
        loginPage.submit().waitUntil(Condition.visible, Long.parseLong(getProperty("driver.wait.timeout"))).isDisplayed();
//        Assert.assertEquals("Вход / Регистрация", loginPage.registrationTitleText().getText());
        loginPage.tempPassword().sendKeys("000000");
        loginPage.submit().click();
        loginPageOperations.submitPassword(user);
        categoriesSelect();
        selectCityAfterRegistration();
    }


    @Test
    @DisplayName("Регистрация с лендинга МТС")
    @Owner(value = "Максим")
    @Tag(Tags.CLEAN_CACHE)
    public void registerByLanding_MTSTest() {
        log.info("Open MTS landing");
        open(host + testData.getVariable("landingLink"));
        mainPageOperations.acceptCookies();
        loginPageOperations.registrationFlow(defaultUser);
        categoriesSelect();
        Assert.assertNotNull(loginPage.welcomeScreenHeader().getText());
        loginPage.buttonOnWelcomePage().click();
        selectCityAfterRegistration();
    }


    @Test
    @DisplayName("Регистрация с лендинга БК с авторидеректом в конце на https://www.google.com/")
    @Owner(value = "Максим")
    @Tag(Tags.CLEAN_CACHE)
    public void registerByLanding_BKTest() {
        log.info("Open MTS landing");
        open(host + testData.getVariable("landingLink"));
        mainPageOperations.acceptCookies();
        loginPageOperations.registrationFlow(defaultUser);
        categoriesSelect();
        Assert.assertNotNull(loginPage.welcomeScreenHeader().getText());
        loginPage.buttonOnWelcomePage().click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.google.com/");
        log.info("open main page");
        open(host);
        Assert.assertTrue(mainPage.getHeader().getPersonalCabinetButton().returnAsElement().isDisplayed());
    }


    public void skipCategoriesSelect() {
        Assert.assertTrue(loginPage.categoriesPage().waitUntil(Condition.visible, Long.parseLong(getProperty("driver.wait.timeout"))).isDisplayed());
        log.info("leave registration page");
        open(host);
    }

    public void categoriesSelect() {
        Assert.assertTrue(loginPage.categoriesPage().waitUntil(Condition.visible, Long.parseLong(getProperty("driver.wait.timeout"))).isDisplayed());
        loginPageOperations.selectCategories(driver);
        log.info("tap continue button");
        loginPage.continueButton().click();
    }

    public void selectCityAfterRegistration() {
//        Assert.assertNotNull(loginPage.welcomeScreenHeader().getText());
//        loginPageOperations.selectActiveShop(firstStore);
//        try {
//            mainPage.closeButton().click();
//        } catch (Throwable e){
//            log.info("popup not found");
//        }

//        Assert.assertTrue(mainPage.getHeader().getPersonalCabinetButton().returnAsElement().isDisplayed());
        Assert.assertTrue(loyaltyPage.emailInputField().isDisplayed());
    }


}
