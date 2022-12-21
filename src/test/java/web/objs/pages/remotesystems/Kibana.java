package web.objs.pages.remotesystems;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class Kibana {

    public static void login(String username, String password) {
        $(byName("username")).waitUntil(Condition.visible, 10000).sendKeys(username);
        $(byName("password")).sendKeys(password);
        $(byClassName("euiButton")).click();
    }

    public static SelenideElement lentaapp() {
        return $(byText("Lenta APP"));
    }

    public static SelenideElement discover() {
        return $(byAttribute("aria-label", "Discover"));
    }

    public static SelenideElement selectEnv(String env) {
        SelenideElement envList = $(byClassName("ui-select-match"));
        envList.click();
        //System.out.println(envList.getText());

        ElementsCollection elements = $$(byClassName("ui-select-choices-row-inner"));
        //System.out.println(elements);
        for (SelenideElement element : elements) {
            //System.out.println(element.getText());
            if (element.getText().contains(env)) {
                return element;
            }
        }
        return null;
    }

    public static void setFilter(String filter) {
        SelenideElement searchpanel = $(byAttribute("placeholder", "Search"));
        searchpanel.sendKeys(filter);
        searchpanel.sendKeys(Keys.ENTER);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void setIntervalMinutes(String interval) throws InterruptedException {
        Thread.sleep(3000);
        $(byClassName("euiSuperDatePicker__prettyFormat")).click();
        $(byClassName("euiDatePopoverButton")).click();

        SelenideElement ele = $(byAttribute("type", "number"));
        for (int i = 0; i <= ele.getAttribute("value").length(); i++) {
            ele.sendKeys(Keys.BACK_SPACE);
        }

        ele.setValue(interval);
        $(byClassName("euiButton")).click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static SelenideElement refreshResults() {
        return $(byText("Refresh"));
    }

    public static WebElement getMessage(WebDriver driver) throws InterruptedException {
        WebElement lastMessage;
        try {
            lastMessage = getLastMessage(driver);
        } catch (NoSuchElementException ex) {
            Thread.sleep(5000);
            Kibana.refreshResults().click();
            lastMessage = getLastMessage(driver);
        }

        return lastMessage;
    }

    public static WebElement getLastMessage(WebDriver driver) {
        return driver.findElement(byClassName("truncate-by-height"));
    }
}
