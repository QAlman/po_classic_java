package web.objs.pages.site;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import web.objs.elements.buttons.RadioButton;

import java.util.Objects;

import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProfilePage {

    public ProfilePage(WebDriver driver) {
        driver.manage().logs();
    }

    public SelenideElement editProfileData() {
        return $(byText("Редактировать"));
    }

    public SelenideElement inputField(String fieldName) {
        ElementsCollection fields = $$(byClassName("input-field"));
        for (SelenideElement field : fields) {
            if (Objects.requireNonNull(field.getText()).contains(fieldName)) {

                return field;
            }
        }
        throw new NoSuchElementException(fieldName);
    }

    public SelenideElement submitChanges() {
        return $(byText("Сохранить"));
    }

    public SelenideElement getDataOfTheField(String fieldName) {
        ElementsCollection fields = $$(byClassName("user-profile-field"));
        for (SelenideElement field : fields) {
            if (Objects.requireNonNull(field.$(byClassName("user-profile-field__label")).getText()).contains(fieldName)) {

                return field.$(byClassName("user-profile-field__value"));
            }
        }
        throw new NoSuchElementException(fieldName);
    }

    public SelenideElement sex(String value) {
        return new RadioButton(getSex()).getRadioButtonElementByName(value);
    }

    public SelenideElement sex() {
        return new RadioButton(getSex()).getDeselectedElement();
    }

    public SelenideElement marriage() {
        return new RadioButton(getMarriage()).getDeselectedElement();
    }

    public SelenideElement getSex() {
        SelenideElement debugVarToFindElement  = $(byClassName("user-profile-field"));
        System.out.println(debugVarToFindElement);
        ElementsCollection elements= $$(By.className("user-profile-field"));

        for (SelenideElement element : elements) {
            if ((element.getText()).contains("Пол")
                    && !(element.getText().contains("Полн")))
            {
                /**
                 * Do not touhch the output
                 */
                System.out.println(element);
                return element;
            }
        }

        throw new NoSuchElementException("Блок \"Пол\" не найден");
    }

    public SelenideElement getMarriage() {
        SelenideElement debugVarToFindElement  = $(byClassName("user-profile-section"));
        System.out.println(debugVarToFindElement);
        ElementsCollection elements = $$(By.className("user-profile-section"));
        /**
         * Do not toughch the output
         */
        System.out.println(elements);

        for (SelenideElement element : elements) {
            if (Objects.requireNonNull(element.getText()).contains("Семейное положение")) {
                return element;
            }
        }

        throw new NoSuchElementException("Блок семейного положения не найден");
    }

    public SelenideElement history() {
        return $(byClassName("user-history"));
    }
}
