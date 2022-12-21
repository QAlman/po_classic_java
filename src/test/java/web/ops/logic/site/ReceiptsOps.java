package web.ops.logic.site;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import web.objs.pages.site.ReceiptsPage;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selectors.byXpath;


public class ReceiptsOps {
    private final ReceiptsPage receiptsPage;

    public ReceiptsOps(WebDriver driver) {
        receiptsPage = new ReceiptsPage(driver);
    }

    public List<List<SelenideElement>> getElementsWithLinkCollection() {
        List<List<SelenideElement>> listOfLinks = new ArrayList<>();
        receiptsPage.receiptsCollection().forEach(element -> listOfLinks.add(element.$$(byXpath(".//a[starts-with(@href,'https')]"))));
        return listOfLinks;
    }


}
