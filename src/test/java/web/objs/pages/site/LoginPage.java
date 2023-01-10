package web.objs.pages.site;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import web.objs.pages.BasePage;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage {
    WebDriver driver;

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }


    public SelenideElement name(){ return $(byXpath("//div/input[@name='firstName']")); }

    public SelenideElement passwordField(){return $(byName("password"));}

    public SelenideElement confirmPasswordField(){return $(byName("confirmedPassword"));}

    public SelenideElement nextButton() {
        return $(byText("Далее"));
    }

    public SelenideElement continueButton() {
        return $(byText("Продолжить"));
    }

    public SelenideElement phoneNumberInputField() {
        return $(byXpath("//div[@class='input-field__inner-wrap']//input[@name='phone']"));
    }
    public SelenideElement loginByPassword() {
        return $(byText("Войти по паролю"));
    }
    public SelenideElement tempPassword() {
        return $(byAttribute("type","password"));
    }
    public SelenideElement smsCodeField() {
        return $(byAttribute("name","code"));
    }

    public SelenideElement reRegistration(){ return  $(byText("Заменить / Перерегистрация"));
    }

    public SelenideElement submit() { return $(byClassName("registration__button")); }
    public SelenideElement submitButton() { return $(byXpath("//span[@class='button__inner']" +
            "[text()[contains(., 'Отправить')" +
            "or contains(., 'Продолжить')" +
            "or contains(., 'Подтвердить')" +
            "or contains(., 'Завершить')]]")); }
    public SelenideElement continueWithOutEmailButton() { return $(byClassName("page__header-skip-email")); }

    public SelenideElement code() {
        return $(byXpath("//div/label[text()[contains(., 'код')  or contains(., 'Код')]]/following:: input[1]"));
    }

    public SelenideElement surnameField() {return $(byAttribute("name", "lastName")); }
    public SelenideElement birthTime() { return $(byAttribute("name", "dateOfBirth")); }
    public SelenideElement eMail() { return $(byAttribute("name", "emailAddress")); }
    public SelenideElement maleSexButton() {return $(byText("Мужской"));  }
    public SelenideElement femaleSexButton() {return $(byText("Женский"));    }
    public SelenideElement welcomeScreenHeader() { return $(byClassName("welcome-screen__greeting-header")); }
    public SelenideElement welcomeScreenText() { return $(byClassName("welcome-screen__request")); }
    public SelenideElement buttonOnWelcomePage() {  return $(byClassName("welcome-screen__button")); }
    public SelenideElement throbber() {  return $(byClassName("spinner")); }
    public SelenideElement registrationTitleText() {  return $(byClassName("registration__title-text")); }
    public SelenideElement registrationEndTitleText() {  return $(byClassName("registration-end__title-text")); }
    public SelenideElement shopAddress(String shop) {return $(byText(shop));   }
    public SelenideElement getUserID() { return $(byXpath("//script[following-sibling::meta][last()-5]")); }
    public SelenideElement alertForm() {return $(byText("Неправильный код проверки. Повторите попытку ещё раз."));}
    public SelenideElement selectCategoriesButton() { return $(byText("Выбрать категории")); }
    public SelenideElement selectShopButton() { return $(byClassName("store-preview__button--submit")); }
    public SelenideElement iHaventProjectLsCard() { return $(byXpath("//*[text()[contains(., 'нет старой карты «Project»')]]")); }
    public SelenideElement categoriesPage() { return $(byXpath(".//div[contains(@class, 'category-loyalty__number')]")); }

}
