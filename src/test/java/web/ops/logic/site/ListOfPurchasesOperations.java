package web.ops.logic.site;

import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import web.objs.pages.site.ListOfPurchasesPage;

import static com.codeborne.selenide.Selectors.byText;

public class ListOfPurchasesOperations {
    private final ListOfPurchasesPage listOfPurchasesPage;
    private final Logger log = Logger.getLogger(this.getClass().getName());

    public ListOfPurchasesOperations(WebDriver driver) {
        this.listOfPurchasesPage = new ListOfPurchasesPage(driver);
    }

    public ListOfPurchasesOperations addPurchase(String goodName) {
        log.info("Send value " + goodName + " to input field");
        listOfPurchasesPage.inputField().setValue(goodName);
        listOfPurchasesPage.inputField().sendKeys(Keys.RETURN);
        return this;
    }

    public ListOfPurchasesOperations findElementInCatalogue(String goodName) {
        log.info("Find good " + goodName + " in catalogue");
        SelenideElement element = listOfPurchasesPage.singleGood(goodName);
        element.find(byText("Найти в каталоге")).click();
        return this;

    }

    public boolean isGoodPresentsIncatalogue() {
        log.info("Check good presence");
        return listOfPurchasesPage.firstEntryOfSearchingGoodInCatalog().getText() != null;
    }


    public boolean findInCatalog(String goodName) {
        log.info("Lookup for good " + goodName + " in list");
        return listOfPurchasesPage.singleGood(goodName).getText() != null;
    }

    public ListOfPurchasesOperations switchToPurchasesList() {
        log.info("Switch to purchases list");
        listOfPurchasesPage.listTab().click();
        return this;
    }
}
