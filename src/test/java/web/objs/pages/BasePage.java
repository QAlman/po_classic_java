package web.objs.pages;

import org.openqa.selenium.WebDriver;
import web.objs.elements.containers.DeliveryPopUp;
import web.objs.elements.containers.Header;
import web.objs.elements.containers.StoreSelectionPopup;
import web.objs.elements.fields.WebList;

import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selenide.$;

public class BasePage {
    protected WebDriver driver;

    public BasePage(WebDriver driver){
        this.driver = driver;
    }

    public Header getHeader(){
        return new Header(driver);
    }

}
