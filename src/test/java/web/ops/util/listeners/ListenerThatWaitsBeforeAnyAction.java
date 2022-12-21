package web.ops.util.listeners;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

import java.util.concurrent.TimeUnit;

public class ListenerThatWaitsBeforeAnyAction extends AbstractWebDriverEventListener {
    private final long timeout;

    public ListenerThatWaitsBeforeAnyAction(long timeout, TimeUnit unit) {
        this.timeout = TimeUnit.MILLISECONDS.convert(Math.max(0, timeout), unit);
    }

    public void beforeNavigateTo(String url, WebDriver driver) {
        sleep();
    }


    public void beforeNavigateBack(WebDriver driver) {
        sleep();
    }


    public void beforeNavigateForward(WebDriver driver) {
        sleep();
    }

    public void beforeClickOn(WebElement element, WebDriver driver) {
        sleep();
    }

    public void beforeChangeValueOf(WebElement element, WebDriver driver) {
        sleep();
    }

    public void beforeScript(String script, WebDriver driver) {
        sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
