package web.objs.pages.site;

import com.codeborne.selenide.SelenideElement;
import common.dto.Good;
import org.openqa.selenium.WebDriver;
import web.objs.pages.BasePage;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class GoodPage extends BasePage {


    public GoodPage(WebDriver driver) {
        super(driver);
    }

    private SelenideElement goodAddress() {
        /**ФЛС изменили название класса для товаров в еком. Так как сейчас работает оба способа, делаю костыль**/
        if ($(byClassName("sku-store-container__store-name")).isDisplayed()) {
            return $(byClassName("sku-store-container__store-name"));
        } else {
            return $(byClassName("sku-store-container__store-search-toggler"));
        }
    }

    private SelenideElement goodPriceWithCard() {
        return $(byClassName("sku-price"));
    }

    private SelenideElement goodBrand() {
        return $(byClassName("sku-card-tab-params__value"));
    }

    private SelenideElement goodWeight() {
        return $(byXpath("//div[2]/dd"));
    }

    private SelenideElement goodProducer() {
        return $(byXpath("//div[2]/div[3]/div/dd"));
    }

    private SelenideElement goodProducerCountry() {
        return $(byXpath("//div[2]/div[3]/div/dd"));
    }

    private SelenideElement goodName() {
        return $(byClassName("sku-page__title"));
    }

    public Good getGoodContext() {
        Good good = new Good();
        good.setName(goodName().getText());
        good.setAddress(goodAddress().getText());
        good.setPrice(goodPriceWithCard().getText());
        good.setBrand(goodBrand().getText());
        good.setWeight(goodWeight().getText());
        good.setProducer(goodProducer().getText());
        good.setProducerCountry(goodProducerCountry().getText());
        return good;
    }


    public SelenideElement createFeedBackButton() {
        return $(byClassName("comments__form-toggler"));
    }

    public SelenideElement feedBackTab() {
        return $(byText("Отзывы"));
    }

    public SelenideElement userName() {
        return $(byXpath("(//input[@type='text'])[2]"));
    }

    public SelenideElement feedbackField() {
        return $(byClassName("textarea-field__control"));
    }

    public SelenideElement setRank() {
        SelenideElement rating = $(byClassName("comments-form__rating-stars"));
        return rating.$(byClassName("rating__star"));
    }

    public SelenideElement submitFeedBack() {
        return $(byText("Отправить"));
    }

    public SelenideElement productIsOutOfStock() {
        return $(byText("Товар доступен в других магазинах \"Project\""));
    }

    public SelenideElement feedBackComment() {
        return $(byClassName("comment"));
    }
}
