package web.objs.pages.site;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selenide.$$;

public class ReceiptsPage {
    WebDriver driver;

    public ReceiptsPage(WebDriver driver) {
        this.driver = driver;
    }

    public List<SelenideElement> receiptsCollection() {
        return $$(byCssSelector("div.recipe-start-page-list"));
    }

}
