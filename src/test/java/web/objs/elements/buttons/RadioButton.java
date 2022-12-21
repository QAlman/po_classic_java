package web.objs.elements.buttons;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import java.util.Objects;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

public class RadioButton {
    Logger log = Logger.getLogger(this.getClass().getName());

    SelenideElement radiobuttonsParentElement;

    public RadioButton(By by) {
        radiobuttonsParentElement = $(by);
    }

    public RadioButton(SelenideElement element) {
        radiobuttonsParentElement = $(element);
    }

    public SelenideElement getRadioButtonElementByName(String value) {
        ElementsCollection buttons = radiobuttonsParentElement.$$(byXpath(".//*"));
        //log.info(buttons);

        //log.info(selenideElement);
        return buttons.stream()
                .filter(button -> button.getText().contains(value) && button.getAttribute("class").contains("radio-button"))
                .findAny()
                .get();
    }

    public SelenideElement getRadioButtonElementByAttr(String attr, String value) {
        ElementsCollection buttons = radiobuttonsParentElement.$$(byXpath(".//*"));

        return buttons.stream()
                .filter(button -> button.getAttribute(attr).contains(value))
                .findAny()
                .get();
    }

    public SelenideElement getSelectedElement() {
        ElementsCollection buttons = radiobuttonsParentElement.$$(byXpath(".//*"));

        return buttons.stream()
                .filter(button -> button.getAttribute("class").contains("radio-button") && button.getAttribute("class").contains("selected"))
                .findAny()
                .get();
    }

    public SelenideElement getDeselectedElement() {
        ElementsCollection buttons = radiobuttonsParentElement.$$(byXpath(".//*"));
        //log.info(buttons);
        //buttons.iterator().forEachRemaining(button-> log.info(button.getAttribute("class")));

        return buttons.stream()
                .filter(button -> Objects.nonNull(button.getAttribute("class")))
                .filter(button -> !button.getAttribute("class").endsWith("selected"))
                .filter(button -> !button.getAttribute("class").endsWith("label"))
                .filter(button -> button.getAttribute("class").contains("radio-button"))
                .findAny()
                .get();
        //return null;
    }


    public SelenideElement toSelenideElement() {
        return radiobuttonsParentElement;
    }
}
