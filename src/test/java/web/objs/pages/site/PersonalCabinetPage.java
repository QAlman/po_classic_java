package web.objs.pages.site;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import common.dto.User;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import web.objs.pages.BasePage;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static web.ops.util.datautils.ConfigPropertiesUtil.getProperty;

public class PersonalCabinetPage extends BasePage {
    WebDriver driver;
    public PersonalCabinetPage(WebDriver driver) {
        super(driver);
        this.driver=driver;
        driver.manage().logs();
    }


    public User getUserContext() {
        User user = new User();
        user.setFirstName(getUserName().getText());
        user.setCardNumber(getCardNumber().getText());
        user.setPhone(getProperty("site.username"));
        user.setPassWord(getProperty("site.password"));
        return user;
    }

    public SelenideElement getUserName() {
        ProjectLLogo().waitUntil(Condition.visible, Long.parseLong(getProperty("driver.wait.timeout"))).isDisplayed();
        ((JavascriptExecutor)driver).executeScript("document.getElementsByClassName('header__profile-menu--authorized')[0].style.display=\"unset\"");
        return $(byClassName("header__profile-name-text"));
    }


    public SelenideElement getCardNumber() {
        return $(byClassName("left-menu__card-number"));
    }

    public SelenideElement quitButton() {
        return $(byText("Выйти"));
    }
    public SelenideElement ProjectLLogo() {return $(byClassName("header__logo")); }

    public SelenideElement messageButton() {
        return $(byText("Сообщения"));
    }

    public SelenideElement stampsLinkFromLK() {
        return $(byXpath("//a[@ga-event-label='Переход на вкладку с фишками']"));
    }

    public SelenideElement bonusGameFromLK() {
        return $(byXpath("//div[@class='left-menu__link-wrapper']/child:: a[@ga-event-label='Бонусная игра']"));
    }

    public SelenideElement lastUnreadMessage() {
        return $(byXpath("//div[@class='inbox-list']/child:: a[1]"));
    }

    public SelenideElement subjectMessage() {
        return $(byClassName("inbox-details__subject"));
    }

    public SelenideElement textMessage() {
        return $(byClassName("inbox-details-item__text"));
    }

    public SelenideElement deleteMessageButton() {
        return $(byXpath("//div[@class='inbox-list-item__body-content']/following:: div[1]"));
    }

    public SelenideElement lastPageMessage() {
        return $(byXpath("//a[@class='link pager__page-link '][last()]"));
    }

    public SelenideElement numberOfLustPage() {
        return $(byClassName("pager__page-link--active"));
    }
}