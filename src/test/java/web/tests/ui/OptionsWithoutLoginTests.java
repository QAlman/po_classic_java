package web.tests.ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ex.ElementNotFound;
import common.cnst.Tags;
import common.dto.Good;
import io.qameta.allure.Owner;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import web.objs.pages.site.GoodPage;
import web.objs.pages.site.MainPage;
import web.ops.logic.site.ListOfPurchasesOperations;
import web.ops.logic.site.MainPageOperations;

import java.util.ArrayList;
import java.util.Objects;

import static web.ops.util.datautils.ConfigPropertiesUtil.getProperty;


public class OptionsWithoutLoginTests extends BaseUIClass {
    /* TODO:
    надо добавить метод по добавлению новых товаров ибо идет привязка к данным и тесты падают
     */

    @Before
    public void setUp() {
        goodPage = new GoodPage(driver);
        mainPage = new MainPage(driver);

        mainPageOperations = new MainPageOperations(driver);
        listOfPurchasesOperations = new ListOfPurchasesOperations(driver);
    }

    @Test
    @DisplayName("Изменение города")
    @Owner(value = "Антон")
    @Tag(Tags.CLEAN_CACHE)
    public void changeActiveCityAndStoreTest() {
        mainPageOperations
                .changeCurrentCity("Москва и МО", "ул. Привольная, д. 8");

        mainPage.getHeader().getChangeDeliveryButton().clickButton();
        Assert.assertEquals("Москва и МО", mainPage.getHeader().getCitySelectionPopup().changeCityButton().getText());
        mainPage.getHeader().getCloseButton().clickButton();
        Assert.assertEquals("ул. Привольная, д. 8", mainPage.getHeader().getActiveAddress().getText());
    }

    @Test
    @DisplayName("Поиск несуществуещего товара")
    @Owner(value = "Максим")
    @Tag(Tags.CLEAN_CACHE)
    public void searchNonExistentGoodTest() {
        log.info("Input in search bar non-existent goods and tap Enter");
        mainPageOperations
                .findGoodsWithSearchBarAndTapEnter(testData.getVariable("nonExistantGood"));
        log.info("Check page type. Expected Result NoSearchResults");

        Assert.assertTrue(mainPage.noSearchResultsPage().isDisplayed());
    }

    @Test
    @DisplayName("Поиск товара в серчбаре")
    @Owner(value = "Максим")
    @Tag(Tags.CLEAN_CACHE)
    public void searchGoodsWithSearchBarTest() {
        String good = "картошка";

        mainPageOperations
                .findGoodsWithSearchBarAndTapEnter(good);

        log.info("Check page type. Unexpected Result NoSearchResults");
        Assert.assertFalse(mainPage.noSearchResultsPage().isDisplayed());

        int resultNumber = Integer.parseInt(Objects.requireNonNull(mainPage.numberOfProductInSearchResult().waitUntil(Condition.visible, Long.parseLong(getProperty("driver.wait.timeout"))).getText()));
        Assert.assertTrue(resultNumber > 0);

        log.info("Найдено " + resultNumber + " единиц(ы) товара в каталоге по запросу " + good);
    }


    @Test
    @DisplayName("Поиск товара в разных городах")
    @Owner(value = "Антон")
    @Tag(Tags.CLEAN_CACHE)
    public void lookUpForGoodFromCatalogueInDifferentCitiesTest() {
        mainPageOperations
                .acceptCookies()
                .selectGoodFromCategoryCatalog(testData.getVariable("category"));

        log.info("Get good context");
        Good goodFromFirstShop = goodPage.getGoodContext();

        mainPageOperations
                .changeCurrentCity("Москва и МО", "ул. Привольная, д. 8");
        mainPage.getHeader().getActiveAddress().shouldHave(Condition.text("ул. Привольная, д. 8"));
        try {
            log.info("Get good context");
            Good goodFromSecondShop = goodPage.getGoodContext();

            Assert.assertNotEquals(goodFromFirstShop.getAddress(), goodFromSecondShop.getAddress());
        } catch (ElementNotFound e) {
            Assert.assertTrue(goodPage.productIsOutOfStock().isDisplayed());
            log.info("Good out of stock in second shop");
        }
    }

    @Test
    @DisplayName("Добавление товара в список покупок")
    @Owner(value = "Антон")
    @Tag(Tags.CLEAN_CACHE)
    public void addGoodsToListOfPurchasesTest() {
        mainPageOperations
                .acceptCookies()
                .goToFavourites();

        listOfPurchasesOperations
                .switchToPurchasesList()
                .addPurchase(testData.getVariable("purchase1"))
                .addPurchase(testData.getVariable("purchase2"))
                .addPurchase(testData.getVariable("purchase3"));

        Assert.assertTrue(listOfPurchasesOperations.findInCatalog("Сосиска"));
        Assert.assertTrue(listOfPurchasesOperations.findInCatalog("канистра"));

    }

    @Test
    @DisplayName("Поиск товара в каталоге")
    @Owner(value = "Антон")
    @Tag(Tags.CLEAN_CACHE)
    public void findGoodsInCatalogueTest() {
        mainPageOperations
                .acceptCookies()
                .goToFavourites();

        listOfPurchasesOperations
                .switchToPurchasesList()
                .addPurchase(testData.getVariable("purchase1"))
                .findElementInCatalogue("Хлеб");

        ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(1));

        Assert.assertTrue(listOfPurchasesOperations.isGoodPresentsIncatalogue());
    }
}
