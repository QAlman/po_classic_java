package web.ops.logic.site;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import web.objs.elements.containers.Header;
import web.objs.pages.site.MainPage;

import static web.ops.helpers.CommonHelpers.generateRandomNumberInRange;
import static web.ops.util.datautils.ConfigPropertiesUtil.getProperty;

public class MainPageOperations {
    MainPage mainPage;
    JavascriptExecutor driver;
    private final Logger log = Logger.getLogger(this.getClass().getName());

    public MainPageOperations(WebDriver driver) {
        this.mainPage = new MainPage(driver);
        this.driver = (JavascriptExecutor) driver;
    }

    public MainPageOperations acceptCookies() {
        Configuration.timeout = 5000;
        log.info("look up for cookies popup");
        try {
            mainPage.AcceptCookieButton().click();
            log.info("submit cookies usage");
        } catch (Throwable ex) {
            log.info("Cookie popup did not find");
        }

        Configuration.timeout = Long.parseLong(getProperty("driver.wait.timeout"));
        return this;
    }

    public MainPageOperations selectOrDeselectFavouriteShop(String storeName) {
        mainPage
                .getHeader()
                .getChangeDeliveryButton()
                .clickButton();

        mainPage.getHeader().getDeliveryPopup().getSaveButton().clickButton();

        log.info("Open list of stores and finding store " + storeName);
        mainPage
                .getHeader()
                .getStoreSelectionPopup()
                .selectElement(storeName);

        log.info("store found");
        log.info("Adding to favourites list");
        mainPage.addToFavouritesButton().click();
        return this;
    }

    public MainPageOperations selectGoodFromCategoryCatalog(String category) {
        acceptCookies();
        log.info("Hover over catalog button, open list of catalogues");
        mainPage.getHeader().getCatalogButton().hoverButton();
        try {
            log.info("Select category " + category);
            mainPage.selectCategoryInCatalogue(category).waitUntil(Condition.visible, Long.parseLong(getProperty("driver.wait.timeout"))).click();
        } catch (ElementNotFound e) {
            log.info("TRY AGAIN. Hover over catalog button, open list of catalogues");
            mainPage.getHeader().getCatalogButton().hoverButton();
            log.info("TRY AGAIN. Select category " + category);
            mainPage.selectCategoryInCatalogue(category).waitUntil(Condition.visible, Long.parseLong(getProperty("driver.wait.timeout"))).click();
        }
        int randomNum;
        if (generateRandomNumberInRange(mainPage.catalogGoods().size()) == 9) {
            randomNum = 8;
        } else {
            randomNum = generateRandomNumberInRange(mainPage.catalogGoods().size());
        }
        SelenideElement randomGoods = mainPage.catalogGoods().get(randomNum);
        log.info("Find good " + randomGoods.getText());
        randomGoods.click();
        log.info("Found");
        return this;
    }

    public MainPageOperations goToLoginPage() {
        log.info("Open login");
        mainPage.getHeader()
                .loginAndRegistrationButtonClick();

        return this;
    }

    public MainPageOperations initiateStandardRegistrationProcess() {
        log.info("Open registration page");
        mainPage.getHeader()
                .loginAndRegistrationButtonClick();

        acceptCookies();
        return this;
    }

    public MainPageOperations goToProfilePage() {
        log.info("Open user personal cabinet");
        try {
            mainPage.getHeader()
                    .getUserMenu()
                    .getListElementByInnerText("Мои данные")
                    .click();
        } catch (Exception e) {
            log.info("try catch");
            mainPage.getHeader()
                    .getUserMenu()
                    .getListElementByInnerText("Мои данные")
                    .click();
        }

        return this;
    }

    public MainPageOperations goToPurchasesHistoryPage() {
        log.info("Go to purchases history");
        mainPage
                .getHeader()
                .getUserMenu()
                .getListElementByInnerText("История покупок")
                .click();
        return this;
    }

    public MainPageOperations findGoodsWithSearchBarAndTapEnter(String good) {
        Header header = mainPage.getHeader();
        log.info("Input in search bar goods");
        header.searchSmthInCatalog(good);
        log.info("Checking the quantity of goods in Pop-up");
        Assert.assertTrue(header.getSearchPopupContainer().isDisplayed());
        log.info("Tap ENTER");
        header.getFindButton().clickButton();
        return this;
    }

    public MainPageOperations goToFavourites() {
        log.info("Go to favourites");
        mainPage.getHeader()
                .getFavouritesButton()
                .clickButton();
        return this;
    }

    public MainPageOperations goToReceipts() {
        log.info("go to receipts");
        mainPage.toReceiptsPage().click();
        return this;
    }

    public MainPageOperations changeCurrentCity(String cityName, String store) {
        mainPage.getHeader()
                .getChangeDeliveryButton()
                .clickButton();

        mainPage.getHeader()
                .getCitySelectionPopup()
                .changeCityButton()
                .clickButton();

        mainPage.getHeader()
                .getCitySelectionPopup()
                .selectCity(cityName);

        mainPage.getHeader()
                .getDeliveryPopup()
                .getPickUp()
                .clickButton();

        mainPage.getHeader()
                .getStoreSelectionPopup()
                .changeCityButton()
                .clickButton();

        mainPage.getHeader()
                .getStoreSelectionPopup()
                .selectElement(store)
                .saveChanges()
                .clickButton();

        return this;
    }
}
