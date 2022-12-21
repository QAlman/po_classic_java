package web.ops.logic.site;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ex.ElementNotFound;
import common.dto.User;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import web.objs.pages.site.LoginPage;
import web.objs.pages.site.PersonalCabinetPage;

import java.util.List;

import static java.lang.Thread.sleep;
import static web.ops.helpers.UIHelpers.*;
import static web.ops.util.datautils.ConfigPropertiesUtil.getProperty;

public class LoginPageOperations {
    LoginPage loginPage;
    WebDriver driver;
    private final Logger log = Logger.getLogger(this.getClass().getName());

    public LoginPageOperations(WebDriver driver) {
        this.loginPage = new LoginPage(driver);
        this.driver = driver;
    }

    public LoginPageOperations logInWithPass(String userName, String password) {
        loginPage.phoneNumberInputField().sendKeys(userName);
        loginPage.nextButton().click();
        loginPage.loginByPassword().click();
        loginPage.passwordField().sendKeys(password);
        loginPage.nextButton().click();
        return this;
    }

    public LoginPageOperations logInWithSMS(String userName) {
        loginPage.phoneNumberInputField().sendKeys(userName);
        loginPage.nextButton().click();
        loginPage.loginByPassword().waitUntil(Condition.visible, Long.parseLong(getProperty("driver.wait.timeout"))).isDisplayed();
        Assert.assertEquals(loginPage.registrationTitleText()
                .waitUntil(Condition.text("Вход"), Long.parseLong(getProperty("driver.wait.timeout"))).getText(), "Вход");
        enterRegistrationCodeWithAutoComplete("000000");
        return this;
    }



    public void registrationError() {
        /**Очередной метод-костыль, поле может принять код не с первого раза**/
        try {
            loginPage.alertForm().getText();
            loginPage.submit().click();
            log.info("verification number is not accepted");
        } catch (Throwable ex) {
            log.info("verification number is accepted");
        }
    }

    public LoginPageOperations enterRegistrationCode(String responseCode) {
        log.info("enter registration code");
        loginPage.code().sendKeys(responseCode);
        loginPage.submitButton().click();
        return this;
    }

    public LoginPageOperations enterRegistrationCodeWithAutoComplete(String responseCode) {
        log.info("enter registration code");
        loginPage.code().sendKeys(responseCode);
        loginPage.throbber().shouldBe(Condition.visible);
        return this;
    }

    public LoginPageOperations submitUserData(User user) {
        log.info("send user data " + user);
        scrollToElement((JavascriptExecutor) driver, loginPage.surnameField());
        loginPage.surnameField().sendKeys(user.getSurname());
        clearBirthTimeField(loginPage.birthTime());
        loginPage.birthTime().sendKeys(user.getBirthdate());
        try {
            loginPage.eMail().sendKeys(user.getEmail());
        } catch(ElementNotFound e){
            log.info("email field does not exits");
        }

        loginPage.name().sendKeys(user.getFirstName());
        if (user.getSex().equals("W")) {
            loginPage.femaleSexButton().click();
        } else {
            loginPage.maleSexButton().click();
        }
        loginPage.submitButton().click();
        return this;
    }

    public LoginPageOperations submitPassword(User user){
        loginPage.passwordField().waitUntil(Condition.visible, Long.parseLong(getProperty("driver.wait.timeout"))).sendKeys(user.getPassWord());
        loginPage.confirmPasswordField().sendKeys(user.getPassWord());
        scrollToElement((JavascriptExecutor) driver, loginPage.submitButton());
        loginPage.submitButton().click();
        return this;
    }

    public void selectCategories(WebDriver driver) {
        log.info("Select 5 categories");
        List<WebElement> categories = driver.findElements(new By.ByXPath("//div[@class='category-loyalty__name']"));
        categories.get(2).click();
        categories.get(3).click();
        categories.get(4).click();
        categories.get(5).click();
        categories.get(8).click();
        loginPage.selectCategoriesButton().click();

    }

    public void logOut(PersonalCabinetPage personalCabinetPage) {
        log.info("Logout user");
        scrollToElement((JavascriptExecutor) driver, personalCabinetPage.getUserName());
        personalCabinetPage.getUserName().waitUntil(Condition.visible,Long.parseLong(getProperty("driver.wait.timeout"))).click();
        personalCabinetPage.quitButton().click();
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void selectActiveShop(String shop) {
        log.info("select active shop");
        loginPage.buttonOnWelcomePage().waitUntil(Condition.visible, Long.parseLong(getProperty("driver.wait.timeout"))).click();
            loginPage.shopAddress(shop).click();
        loginPage.selectShopButton().waitUntil(Condition.visible, Long.parseLong(getProperty("driver.wait.timeout"))).click();
    }

    public void registrationFlow(User user) {
        log.info("Input phone number");
        scrollToElement((JavascriptExecutor) driver, loginPage.phoneNumberInputField());
        loginPage.phoneNumberInputField().waitUntil(Condition.visible, Long.parseLong(getProperty("driver.wait.timeout"))).sendKeys(user.getPhone().substring(2));
        log.info("Tap confirm button");
        loginPage.submit().click();
        log.info("Input validation code");
        enterRegistrationCode("000000");
        registrationError();
        try {
            loginPage.iHaventProjectLsCard().click();
            log.info("Tap i haven`t ProjectL`s card");
        } catch (ElementNotFound e) {
            log.info("Screen with input card`s number not found");
        }
        submitUserData(user);
        submitPassword(user);
    }


    public void registrationFlowForTablet(User user) {
        log.info("Input phone number");
        scrollToElement((JavascriptExecutor) driver, loginPage.phoneNumberInputField());
        loginPage.phoneNumberInputField().waitUntil(Condition.visible, Long.parseLong(getProperty("driver.wait.timeout"))).sendKeys(user.getPhone().substring(2));
        log.info("Tap confirm button");
        loginPage.submitButton().click();
        log.info("Input validation code");
        enterRegistrationCode("000000");
        log.info("Tap i haven`t ProjectL`s card");
        loginPage.iHaventProjectLsCard().click();
        loginPage.continueWithOutEmailButton().click();
        submitUserData(user);
    }
}
