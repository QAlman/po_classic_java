package web.objs.elements.containers;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import web.objs.elements.fields.WebList;
import web.objs.elements.buttons.Button;
import web.objs.elements.fields.InputField;

import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static web.ops.util.datautils.ConfigPropertiesUtil.getProperty;

public class Header {
    private final InputField searchField;
    private final Button favourites;
    private final Button mainPage;
    private final Button catalog;
    private final Button find;
    private final SelenideElement searchPopupContainer;
    private final SelenideElement activeAddress;
    private final Button personalCabinet;
    private final WebDriver driver;
    private final Button currentCity;
    private final Button changeDeliveryButton;
    private final CitySelectionPopup citySelectionPopup;
    private final StoreSelectionPopup storeSelectionPopup;
    private final DeliveryPopUp deliveryPopUp;
    private final Button chooseDeliveryOrPickUpButton;
    private Button cart;
    private Button close;


    public Header(WebDriver driver) {
        searchField = new InputField($(byClassName("catalog-search__field")));
        favourites = new Button($(byClassName("header__favorite")));
        mainPage = new Button($(byClassName("header__logo")));
        close = new Button($(byClassName("close-control")));
        catalog = new Button($(byClassName("header__catalog")));
        find = new Button($(byClassName("catalog-search__icon")));
        searchPopupContainer = $(byClassName("catalog-search__skus"));
        activeAddress = $(byClassName("address-container__address"));
        personalCabinet = new Button($(byClassName("header__profile")));
        currentCity = new Button($(byClassName("current-store__city-container")));
        changeDeliveryButton = new Button($(byText("Изменить")));
        chooseDeliveryOrPickUpButton = new Button($(byText("Выбрать доставку или магазин")));
        citySelectionPopup = new CitySelectionPopup();
        storeSelectionPopup = new StoreSelectionPopup();
        deliveryPopUp = new DeliveryPopUp();
        cart = new Button($(byClassName("header__basket")));


        this.driver = driver;
    }


    public Button getChooseDeliveryOrPickUpButton() {
        return chooseDeliveryOrPickUpButton;
    }

    public Button getCloseButton() {
        return close;
    }

    public SelenideElement getActiveAddress() {
        return activeAddress;
    }

    public InputField getSearchField() {
        return searchField;
    }

    public Button getFavouritesButton() {
        return favourites;
    }

    public Button getMainPageButton() {
        return mainPage;
    }

    public Button getCatalogButton() {
        return catalog;
    }

    public SelenideElement getSearchPopupContainer() {
        return searchPopupContainer;
    }

    public Button getPersonalCabinetButton() {
        return personalCabinet;
    }

    public Button getFindButton() {
        return find;
    }

    public Button getCurrentCity() {
        return currentCity;
    }

    public Button getChangeDeliveryButton() {
        return changeDeliveryButton;
    }

    public CitySelectionPopup getCitySelectionPopup() {
        return citySelectionPopup;
    }

    public StoreSelectionPopup getStoreSelectionPopup() {
        return storeSelectionPopup;
    }

    public DeliveryPopUp getDeliveryPopup() {
        return deliveryPopUp;
    }

//======================================================================================================================

    public WebList getUserMenu() {
        personalCabinet.clickButton();
        ((JavascriptExecutor) driver).executeScript("document.getElementsByClassName('header__profile-menu')[0].style.display=\"unset\"");
        return WebList.fromElement($(byClassName("header__profile-menu-inner")));
    }

    public void searchSmthInCatalog(String value) {
        searchField.setValue(value);
        searchPopupContainer.waitUntil(Condition.visible, Long.parseLong(getProperty("driver.wait.timeout")));
    }


    public void loginAndRegistrationButtonClick() {
        personalCabinet.clickButton();
    }

    public Button getCart() {
        return cart;
    }

    public void setCart(Button cart) {
        this.cart = cart;
    }
}
