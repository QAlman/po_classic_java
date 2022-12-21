package web.objs.pages.site;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import web.objs.pages.BasePage;

import java.util.Objects;

import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static web.ops.helpers.UIHelpers.scrollToElement;

public class ListOfPurchasesPage extends BasePage {

    public ListOfPurchasesPage(WebDriver driver) {
        super(driver);
    }

    public SelenideElement inputField() {
        return $(byClassName("custom-skus-adding-form__control"));
    }

    public SelenideElement listOfGoods() {
        return $(byClassName("custom-skus-list-in-favourites__list"));
    }

    public SelenideElement singleGood(String element) {
        ElementsCollection selenideElements = listOfGoods().$$(byClassName("custom-sku-in-favourites"));
        for (SelenideElement selenideElement : selenideElements) {
            if (Objects.requireNonNull(selenideElement.getText()).contains(element)) {
                scrollToElement((JavascriptExecutor) driver, selenideElement);
                System.out.println(selenideElement);
                return selenideElement;
            }
        }
        throw new NoSuchElementException(element);
    }

    public SelenideElement firstEntryOfSearchingGoodInCatalog() {
        return $(By.className("sku-card-small-container"));
    }

    public SelenideElement listTab() {
        return $(byText("Список"));
    }
}
