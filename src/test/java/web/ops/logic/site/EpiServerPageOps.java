package web.ops.logic.site;

import com.codeborne.selenide.ex.ElementNotFound;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import web.objs.pages.site.EpiServerPage;

import static java.lang.Thread.sleep;

public class EpiServerPageOps {
    EpiServerPage epiServerPage;

    private final Logger log = Logger.getLogger(this.getClass().getName());

    public EpiServerPageOps(WebDriver driver) {
        this.epiServerPage = new EpiServerPage(driver);
    }

    public void applyComments() {
        try {
            log.info("Go to comments. Small screen");
            epiServerPage.comments().click();
        } catch (ElementNotFound e) {
            log.info("Go to comments. Small screen");
            epiServerPage.more().click();
            epiServerPage.comments().click();
        }
        log.info("Apply comments");
        epiServerPage.feedback().click();
        epiServerPage.publishLastFeedBack().click();
    }

    public EpiServerPageOps logInEpi(String userName, String password) {
        epiServerPage.name().sendKeys(userName);
        epiServerPage.password().sendKeys(password);
        epiServerPage.logInButton().click();

        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }
}

