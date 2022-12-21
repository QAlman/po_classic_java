package web.ops.helpers;

import web.ops.logic.site.LoginPageOperations;
import web.ops.logic.site.MainPageOperations;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Selenide.open;
import static java.lang.Thread.sleep;
import static web.ops.util.datautils.ConfigPropertiesUtil.getProperty;


public class UIHelpers {
    public static void login(MainPageOperations mainPageOperations, LoginPageOperations loginPageOperations) {
        mainPageOperations
                .goToLoginPage()
                .acceptCookies();
        loginPageOperations.logInWithPass(
                getProperty("site.username"),
                getProperty("site.password"));
    }

    public static void scrollToElement(JavascriptExecutor driver, WebElement sex) {
        driver.executeScript(
                        "window.scroll("+(sex.getLocation().x-200)+","+(sex.getLocation().y-200)+")", sex.getAttribute("class"));

    }

    public static void clearInputFieldData(WebElement field) {
        for (int i = 0; i < field.getText().length(); i++) {
            field.sendKeys(Keys.BACK_SPACE);
        }
    }

    public static void clearBirthTimeField(WebElement field) {
        for (int i = 0; i < 10; i++) {
            field.sendKeys(Keys.BACK_SPACE);
        }
    }

    public static void openPage(WebDriver driver, String url) {
        try {
            sleep(5000);
            open(url);
            sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

