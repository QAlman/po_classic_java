package web.ops.service;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class ScreenshotService {
    Logger log = Logger.getLogger(this.getClass().getName());
    WebDriver driver;

    public ScreenshotService(WebDriver driver) {
        this.driver = driver;
    }

    public void captureScreenshot(String name) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = "report/screenshots/" + name + ".png";
        try {
            FileUtils.copyFile(screenshot, new File(path));
        } catch (IOException e) {
            log.info("Screenshot has not been taken");
        }
    }
}
