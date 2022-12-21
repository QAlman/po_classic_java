package web.ops.logic.site;

import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import web.objs.pages.site.GoodPage;

public class GoodPageOperations {
    private final GoodPage goodPage;
    private final Logger log = Logger.getLogger(this.getClass().getName());

    public GoodPageOperations(WebDriver driver) {
        this.goodPage = new GoodPage(driver);
    }

    public GoodPageOperations setFeedback() {
        log.info("Open feedback tab");
        goodPage.feedBackTab().click();
        SelenideElement feedBackButton = goodPage.createFeedBackButton();
        feedBackButton.click();
        log.info("create feedback button");
        log.info("set username");
        goodPage.userName().sendKeys("Александрос");
        log.info("set comment");
        goodPage.feedbackField().sendKeys("Не оч");
        log.info("set rank");
        goodPage.setRank().click();
        log.info("submit feedback");
        goodPage.submitFeedBack().click();
        return this;
    }


}
