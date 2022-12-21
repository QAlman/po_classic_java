package web.objs.pages.site;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class StampsPage {
    public StampsPage(WebDriver driver) {
        driver.manage().logs();
    }

    public SelenideElement giveStampsButton() {
        return $(byText("Подарить фишки"));
    }

    public SelenideElement phoneNumberInputField() {
        return $(byXpath("//div/label[text()='Телефон']/following:: input[1]"));
    }

    public SelenideElement plusButton() {
        return $(byClassName("give-stamps-field__button-icon--add"));
    }

    public SelenideElement minusButton() {
        return $(byClassName("give-stamps-field__button-icon--remove"));
    }

    public SelenideElement stampsValueField() {
        return $(byClassName("give-stamps-field__value"));
    }

    public SelenideElement stampsBalanceField() {
        return $(byClassName("give-stamps-field__balance-value"));
    }
    public SelenideElement alertMessage() { return $(byClassName("alert__message")); }

    public SelenideElement nextButton() {
        return $(byText("Далее"));
    }

    public SelenideElement codeNumberInputField() {
        return $(byXpath("//div/label[text()='Код из смс']/following:: input[1]"));
    }

    public SelenideElement confirmButton() {
        return $(byText("Подтвердить"));
    }

    public SelenideElement incorrectCodeMessage() {
        return $(byText("Неверный код"));
    }

    public SelenideElement popUPSuccessTransferStamps() {
        return $(byText("Фишки переведены"));
    }

    public SelenideElement okButton() {
        return $(byText("ОК"));
    }

    public SelenideElement stampsBalanceOnPanel() {
        return $(byClassName("stamps-balance-panel__value"));
    }

}

