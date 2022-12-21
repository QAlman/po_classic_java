package web.objs.pages.site;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class EpiServerPage {
    public EpiServerPage(WebDriver driver) {
        driver.manage().logs();
    }


    public SelenideElement comments() {
        return $(byLinkText("Comments"));
    }

    public SelenideElement more() {
        return $(byLinkText("More…"));
    }

    public SelenideElement feedback() {
        return $(byLinkText("Отзывы"));
    }
    public SelenideElement name() { return $(byId("LoginControl_UserName")); }
    public SelenideElement password() { return $(byId("LoginControl_Password")); }
    public SelenideElement logInButton() { return $(byId("LoginControl_Button1")); }



    public SelenideElement publishLastFeedBack() {
        SelenideElement element = $(byClassName("rt-tr-group"));
        element.$(byClassName("ReactTable-checkbox-cell")).click();
        return $(byText("Опубликовать"));
    }
}
