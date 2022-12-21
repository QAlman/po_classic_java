package web.tests.ui;

import com.codeborne.selenide.Condition;
import common.cnst.Tags;
import common.dto.User;
import common.service.UserService;
import io.qameta.allure.Owner;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import web.objs.pages.site.LoginPage;
import web.objs.pages.site.MainPage;
import web.objs.pages.site.PersonalCabinetPage;
import web.objs.pages.site.ProfilePage;
import web.ops.api.UserAPI;
import web.ops.helpers.CommonHelpers;
import web.ops.logic.site.LoginPageOperations;
import web.ops.logic.site.MainPageOperations;
import web.ops.logic.site.ProfilePageOperations;

import static web.ops.helpers.UIHelpers.login;
import static web.ops.util.datautils.ConfigPropertiesUtil.getProperty;

public class PersonalCabinetTests extends BaseUIClass {

    @Before
    public void setUp() {
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);
        personalCabinetPage = new PersonalCabinetPage(driver);

        mainPageOperations = new MainPageOperations(driver);
        loginPageOperations = new LoginPageOperations(driver);
        profilePageOperations = new ProfilePageOperations(driver);
    }

    @Test
    @DisplayName("Логаут")
    @Owner(value = "Максим")
    @Tag(Tags.CLEAN_CACHE)
    public void logOutTest() {
        login(mainPageOperations, loginPageOperations);
        mainPage.getHeader().getCart().hoverButton();
        mainPageOperations.goToProfilePage();
        loginPageOperations.logOut(personalCabinetPage);

        Assert.assertEquals("Вход/Регистрация", mainPage.getHeader().getPersonalCabinetButton().getText());
    }

    @Test
    @DisplayName("Изменение пола пользователя")
    @Owner(value = "Антон")
    @Tag(Tags.EXISTING_COOCKIES)
    public void changeSexTest() {
        mainPageOperations.goToProfilePage();
        String oldSex= profilePage.getSex().getText();

        profilePageOperations
                .editProfileData()
                .setRandomSex(driver)
                .submit();

        Assert.assertNotSame(profilePage.getSex().getText(), oldSex);
    }

    @Test
    @DisplayName("Изменение Отчества")
    @Owner(value = "Антон")
    @Tag(Tags.CLEAN_CACHE)
    public void changeSecNameTest(){
        String secName = CommonHelpers.generateRandomCyrilicString(7);
        
        User user = new UserAPI().registerUser(new UserService().generateDefaultUser());

        mainPageOperations.goToLoginPage();
        loginPageOperations.logInWithPass(user.getPhone().substring(2),user.getPassWord());
        mainPage.getHeader().getCart().hoverButton();

        mainPageOperations.goToProfilePage();

        profilePageOperations
                .editProfileData()
                .setFieldWithValue("Отчество", secName)
                .submit();

        Assert.assertTrue(profilePage.getDataOfTheField("Полное имя").getText().contains(secName));
    }

    @Test
    @DisplayName("Изменение семейного положкения")
    @Owner(value = "Антон")
    @Tag(Tags.EXISTING_COOCKIES)
    public void changeMarriageTest() {
        mainPageOperations.goToProfilePage();

        String oldMarriage = profilePage.getMarriage().getText();

        profilePageOperations
                .editProfileData()
                .setMarriage(driver)
                .submit();

        Assert.assertNotSame(profilePage.getSex().getText(), oldMarriage);
    }

    @Test
    @DisplayName("Проверка Истории покупок")
    @Owner(value = "Антон")
    @Tag(Tags.EXISTING_COOCKIES)
    public void checkPurchasesHistoryTest() {
        mainPageOperations.goToPurchasesHistoryPage();

        Assert.assertTrue(profilePage.history().isDisplayed());
    }

    @Test
    @DisplayName("Логин с паролем")
    @Owner(value = "Максим")
    @Tag(Tags.CLEAN_CACHE)
    public void logInTest() {
        login(mainPageOperations, loginPageOperations);
        mainPage.getHeader().getCart().hoverButton();
        mainPageOperations.goToProfilePage();

        Assert.assertEquals(personalCabinetPage.getUserContext().getFirstName(), "Александрос");
    }

    @Test
    @DisplayName("Логин с кодом из смс")
    @Owner(value = "Максим")
    @Tag(Tags.CLEAN_CACHE)
    public void logInWithSMSTest() {
        mainPageOperations
                .goToLoginPage()
                .acceptCookies();
        Assert.assertEquals(loginPage.registrationTitleText().shouldBe(Condition.visible).getText(), "Вход / Регистрация");
        loginPageOperations.logInWithSMS(getProperty("site.username"));
        mainPage.getHeader().getCart().hoverButton();
        mainPageOperations.goToProfilePage();

        Assert.assertEquals(personalCabinetPage.getUserContext().getFirstName(), "Александрос");
    }

    @Test
    @DisplayName("Логин не зарегистрированным пользователем")
    @Owner(value = "Максим")
    @Tag(Tags.CLEAN_CACHE)
    public void logInByNewUserTest() {
        UserService userService = new UserService();
        mainPageOperations
                .goToLoginPage()
                .acceptCookies();
        Assert.assertEquals(loginPage.registrationTitleText().getText(), "Вход / Регистрация");
        loginPage.phoneNumberInputField().sendKeys(userService.generatePhoneNumber());
        loginPage.nextButton().click();
        Assert.assertTrue(loginPage.smsCodeField().waitUntil(Condition.visible, Long.parseLong(getProperty("driver.wait.timeout"))).isDisplayed());
        Assert.assertEquals(loginPage.registrationTitleText().getText(), "Регистрация");
    }

}
