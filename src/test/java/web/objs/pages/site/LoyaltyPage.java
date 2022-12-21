package web.objs.pages.site;

import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import web.objs.elements.fields.WebList;

import java.util.*;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class LoyaltyPage {
    WebDriver driver;

    public LoyaltyPage(WebDriver driver) {
        this.driver = driver;
    }

    private final Logger log = Logger.getLogger(this.getClass().getName());


    public SelenideElement sendAgainButton() { return $(byText("Отправить повторно")); }
    public SelenideElement acceptCategories() { return $(byClassName("select-categories-page__button")); }
    public SelenideElement changeCategoriesButtonInBonusGame() {return $(byClassName("bonus-game-page__changing-groups-link"));  }
    public SelenideElement tutorialArrowNext() {return $(byClassName("slider-component__arrow--next"));  }
    public SelenideElement tutorialArrowPrev() {return $(byClassName("slider-component__arrow--prev"));  }
    public SelenideElement tutorialHeader() {return $(byClassName("tutorial__header"));  }
    public SelenideElement popUpClose() {return $(byClassName("popup__close"));  }
    public SelenideElement bonusGameButtonFromLoyaltyLK() {return $(byClassName("loyalty-info-page__level-container-link"));  }
    public SelenideElement tutorialNPLFromBonusGame() {return $(byClassName("bonus-game-page__level-info-icon"));  }
    public SelenideElement buttonOnTutorial()  { return $(byXpath("//div[@class='tutorial__controls']/button/span")); }
    public SelenideElement slideOne() { return $(byText("Выберите категории, которые чаще всего покупаете")); }
    public SelenideElement slideTwo() { return $(byText("Ежемесячно совершайте покупки и выполняйте задания уровня")); }
    public SelenideElement slideThree() { return $(byText("Получайте 5% баллами за каждую категорию")); }
    public WebList categoriesList() { return new WebList(byClassName("categories-loyalty-list")); }
    public SelenideElement proPopUp() { return $(byText("Вы зарегистрированы в программе «ЛЕНТА ПРО»")); }
    public SelenideElement conditionsOfProgram() { return $(byText("Условия программы")); }
    public SelenideElement clearButton() { return $(byText("Понятно")); }
    public SelenideElement articleTitle() {return $(byClassName("article__title")); }
    public SelenideElement loyaltyInfoTabRight() {return $(byClassName("loyalty-info-page__level-progress-description")); }
    public SelenideElement loyaltyBalanceTabLeft() {return $(byClassName("loyalty-info-page__balance-container")); }


    public Map<String, List<SelenideElement>> getCategoriesMap() {
        WebList categoriesList =categoriesList();
        List<SelenideElement> namesCategories = categoriesList.getChildElements(byXpath(".//div[@class = 'category-loyalty__name']"));
        List<SelenideElement> selectedCategories = categoriesList.getChildElements(byXpath(".//div[contains(@class, 'category-loyalty__number')]"));
        Map<String, List<SelenideElement>> categories = new LinkedHashMap<>();

        for (int i = 0; i < namesCategories.size(); i++) {
            try {
                categories.get(selectedCategories.get(i).getText() + "element").add(namesCategories.get(i));
            } catch (Exception e) {
                categories.put(selectedCategories.get(i).getText() + "element", new ArrayList<>(Arrays.asList(namesCategories.get(i))));
            }
        }
        return categories;
    }

    public SelenideElement ProjectLLogo() {return $(byClassName("header__logo")); }

    public SelenideElement loyaltyBanner() {return $(byClassName("loyalty-banner-block__picture")); }
    public SelenideElement headerEmailPopUpSent() {return $(byClassName("confirm-email__title--sent")); }
    public SelenideElement headerTutorialPopUp() {return $(byClassName("tutorial__header")); }
    public SelenideElement emailConfirm() {return $(byXpath("//a[@class='breadcrumbs__link']/span[text()='Email успешно подтверждён']")); }
    public SelenideElement emailInputField() {return $(byClassName("input-field__control")); }

}
