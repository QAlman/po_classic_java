package web.objs.elements.containers;

import web.objs.elements.buttons.Button;
import web.objs.elements.fields.WebList;

import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class StoreSelectionPopup {
    private final WebList listOfStores;
    private final Button favouriteStoreButton;
    private final Button saveChanges;
    private final Button changeCityButton;
    private final Button closePopUpButton;

    public StoreSelectionPopup() {
        listOfStores = WebList.fromElement($(byClassName("stores-list")));
        favouriteStoreButton = new Button($(byText("Выбрать")));
        saveChanges = new Button($(byText("Выбрать магазин")));
        changeCityButton  = new Button($(byClassName("stores-flow__button")));
        closePopUpButton = new Button($(byClassName("store-picker__close-icon")));
    }



    public StoreSelectionPopup selectElement(String name) {
        listOfStores.returnAsElement().$(byText(name)).click();
        return this;
    }

    public Button saveChanges(){
        return saveChanges;
    }

    public Button getFavouriteStoreButton() {
        return favouriteStoreButton;
    }

    public Button changeCityButton() {
        return changeCityButton;
    }

    public Button closePopUpButton() {
        return closePopUpButton;
    }
}
