package web.objs.pages.site;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import web.objs.pages.BasePage;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage extends BasePage {

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public ElementsCollection catalogGoods() {
        return $$(By.className("sku-card-small-container"));
    }

    public WebElement addToFavouritesButton() {
        return $(byText("Выбрать магазин"));
    }

    public SelenideElement selectCategoryInCatalogue(String category) {
        return $(byText(category));
    }

    public SelenideElement AcceptCookieButton() {
        return $(byClassName("cookie-usage-notice__button"));
    }
    public SelenideElement cityInStorePicker() {
        return $(byClassName("store-picker-city__label"));
    }

    public SelenideElement toCommercialOfferPage() {
        return $(byLinkText("Поставщикам"));
    }

    public SelenideElement noSearchResultsPage() {
        return $(byXpath("//header[@data-page-type=\"NoSearchResults\"]"));
    }

    public SelenideElement numberOfProductInSearchResult() {
        return $(byXpath("//li[@class=\"tabs__tab tabs__tab--active\"]/span[@class=\"tabs__tab-counter tabs__tab-counter--desktop\"]"));
    }

    public SelenideElement toReceiptsPage(){
        return $(byText("Рецепты"));
    }

    public SelenideElement closeButton(){ return $(byClassName("popup__close")); }
}
