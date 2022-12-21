package web.objs.elements.containers;

import web.objs.elements.buttons.Button;
import web.objs.elements.buttons.RadioButton;
import web.objs.elements.fields.InputField;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class DeliveryPopUp {
    private Button pickUp;
    private RadioButton delivery;
    private InputField adress;
    private Button save;
    private Button cancel;

    public DeliveryPopUp (){
        pickUp = new Button($(byXpath("//span[text()='Магазины / Самовывоз']/..")));
        delivery = new RadioButton($(byXpath("//span[text()='Доставка']/..")));
        adress = new InputField($(byXpath("//input[@type='text']")));
        save = new Button($(byText("Сохранить")));

    }

    public Button getPickUp() {
        return pickUp;
    }

    public InputField getAdress() {
        return adress;
    }

    public Button getSaveButton() {
        return save;
    }
}
