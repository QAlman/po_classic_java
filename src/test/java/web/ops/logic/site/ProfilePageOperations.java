package web.ops.logic.site;

import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import web.objs.pages.site.ProfilePage;

import static web.ops.helpers.UIHelpers.clearInputFieldData;
import static web.ops.helpers.UIHelpers.scrollToElement;

public class ProfilePageOperations {
    ProfilePage profilePage;
    private final Logger log = Logger.getLogger(this.getClass().getName());

    public ProfilePageOperations(WebDriver driver) {
        this.profilePage = new ProfilePage(driver);
    }

    public ProfilePageOperations setFieldWithValue(String fieldName, String secName) {
        log.info("Setting field " + fieldName + " with value " + secName);
        SelenideElement element = profilePage.inputField(fieldName);
        clearInputFieldData(element);
        element.sendKeys(secName);
        return this;
    }

    public void submit() {
        log.info("Submit changes");
        profilePage.submitChanges().click();
    }

    public ProfilePageOperations editProfileData() {
        log.info("Switch to edit mode");
        profilePage.editProfileData().click();
        return this;
    }

    public ProfilePageOperations setSex(String value, WebDriver driver) throws InterruptedException {
        SelenideElement sex = profilePage.sex(value);
        scrollToElement((JavascriptExecutor) driver, sex);
        sex.click();
        Thread.sleep(5000);

        return this;
    }


    public ProfilePageOperations setRandomSex(WebDriver driver) {
        log.info("change sex (male/female)");
        SelenideElement sex = profilePage.sex();
        scrollToElement((JavascriptExecutor) driver, sex);
        sex.click();

        return this;
    }

    public ProfilePageOperations setMarriage(WebDriver driver) {
        log.info("Change mariage");
        SelenideElement marriage = profilePage.marriage();
        scrollToElement((JavascriptExecutor) driver, marriage);
        marriage.click();
        return this;
    }
}
