package web.ops.util.listeners;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import web.ops.helpers.UIHelpers;

public class WebDriverActionWaitingListener extends AbstractWebDriverEventListener {

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        UIHelpers.scrollToElement((JavascriptExecutor) driver, element);
        super.beforeChangeValueOf(element, driver, keysToSend);
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.elementToBeClickable(element));

            UIHelpers.scrollToElement((JavascriptExecutor) driver, element);


        super.beforeClickOn(element, driver);
    }
}
