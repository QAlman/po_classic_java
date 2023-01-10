package web.ops.service;

import org.json.JSONObject;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.*;

import static web.ops.util.datautils.ConfigPropertiesUtil.getProperty;

public class WebDriverService {
    public WebDriver launchBrowserSwitch(String driversName) {
        try {
            Thread.sleep((long) (Math.random() * 10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (driversName == null) {
            driversName = "CHROME";
        }

        switch (driversName) {

            case "FIREFOX":
                return setFireFoxDriver();
            case "EDGE":
                return setEdgeDriver();
            default:
                return setChromeDriver();
        }
    }

    public void stopDriver(WebDriver driver) {
        driver.quit();
    }

    public void openNewTab(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
    }

    public void switchTab(WebDriver driver, int tab) {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tab));
    }

    public WebDriver setChromeDriver() {

        System.setProperty("webdriver.chrome.driver", Objects.requireNonNull(getProperty("site.chromedriver.path")));
        System.setProperty("webdriver.chrome.sitOutput", "true");

        ChromeOptions options = new ChromeOptions();

        options.addArguments(Arrays.asList(getProperty("chrome.prefs").split(", ")));
//        options.addArguments("--headless");


        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_settings.geolocation", 2);

        options.setExperimentalOption("prefs", new JSONObject().put("profile.default_content_settings.geolocation", 0));
        return new ChromeDriver(options);
    }

    private WebDriver setFireFoxDriver() {
        System.setProperty("webdriver.gecko.driver", Objects.requireNonNull(getProperty("site.geckodriver.path")));
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().setSize(new Dimension(1920, 1280));
        return driver;
    }

    private WebDriver setEdgeDriver() {
        System.setProperty("webdriver.edge.driver", Objects.requireNonNull(getProperty("site.edgedriver.path")));
        WebDriver driver = new EdgeDriver();

        driver.manage().window().setSize(new Dimension(1920, 1280));
        return driver;
    }


}
