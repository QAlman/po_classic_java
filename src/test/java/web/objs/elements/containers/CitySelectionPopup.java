package web.objs.elements.containers;

import com.codeborne.selenide.SelenideElement;
import web.objs.elements.buttons.Button;
import web.objs.elements.fields.WebList;

import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class CitySelectionPopup {
//    private WebList listOfCities;
    private final Button changeCityButton;

    public CitySelectionPopup() {
//        listOfCities = WebList.fromElement($(byClassName("cities-list__container")));
        changeCityButton = new Button($(byClassName("store-picker-city")));
    }


    public void selectCity(String name) {
        WebList.fromElement($(byClassName("cities-list__container"))).returnAsElement().$(byText(name)).click();
    }

    public Button changeCityButton() {
        return changeCityButton;
    }

}
