package web.tests.ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import common.cnst.Tags;
import io.qameta.allure.Attachment;
import io.qameta.allure.junit4.Tag;
import io.restassured.response.Response;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import web.objs.elements.containers.CitySelectionPopup;
import web.objs.elements.containers.DeliveryPopUp;
import web.objs.elements.containers.StoreSelectionPopup;
import web.objs.pages.site.*;
import web.ops.api.UserAPI;
import web.ops.helpers.CommonHelpers;
import web.ops.logic.site.*;
import web.ops.service.CookieHandler;
import web.ops.service.ScreenshotService;
import web.ops.service.WebDriverService;
import web.ops.util.listeners.WebDriverActionWaitingListener;
import web.tests.BaseTestClass;

import java.io.FileOutputStream;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.open;
import static web.ops.util.datautils.ConfigPropertiesUtil.getProperty;


public class BaseUIClass extends BaseTestClass {
    protected WebDriver driver;
    private final WebDriverService webDriverService = new WebDriverService();
    static CookieHandler cookieHandler = CookieHandler.getInstance();


    protected MainPage mainPage;
    protected GoodPage goodPage;
    protected EpiServerPage epiServerPage;
    protected LoyaltyPage loyaltyPage;
    protected PersonalCabinetPage personalCabinetPage;
    protected ProfilePage profilePage;
    protected LoginPage loginPage;
    protected StampsPage stampsPage;

    protected MainPageOperations mainPageOperations;
    protected ListOfPurchasesOperations listOfPurchasesOperations;
    protected CommercialPageOps commercialPageOps;
    protected GoodPageOperations goodPageOperations;
    protected LoginPageOperations loginPageOperations;
    protected ProfilePageOperations profilePageOperations;
    protected StampsPageOperations stampsPageOperations;
    protected PersonalCabinetOperations personalCabinetOperations;
    protected EpiServerPageOps epiServerPageOps;
    protected LoyaltyPageOperations loyaltyPageOperations;
    protected ReceiptsOps receiptsOps;


    protected String firstStore = getProperty("site.firstStore");
    protected String secondStore = getProperty("site.secondStore");
    protected String city = "Санкт-Петербург и ЛО";


    @Rule
    public TestWatcher testWatcher = new TestWatcher() {
        ScreenshotService screenshotService;

        @Override
        public Statement apply(Statement base, Description description) {
            log.info("Starting driver");

            driver = webDriverService.launchBrowserSwitch(System.getProperty("BROWSER"));
            EventFiringWebDriver newDriver = new EventFiringWebDriver(driver);

            prop.setProperty("Browser." + System.getenv("BROWSER") + ".Version", (String) newDriver.getCapabilities().getCapability("browserVersion"));
            prop.setProperty("Browser", System.getenv("BROWSER"));
            try {
                prop.store(new FileOutputStream("target/allure-results/environment.properties"), null);
            } catch (IOException e) {
                e.printStackTrace();
            }

            newDriver.register(new WebDriverActionWaitingListener());
            driver = newDriver;

            WebDriverRunner.setWebDriver(driver);
            Configuration.timeout = Long.parseLong(getProperty("driver.wait.timeout"));

            open(host);
            mainPage = new MainPage(driver);
//            mainPage.cityInStorePicker().waitUntil(Condition.visible, Long.parseLong(getProperty("driver.wait.timeout"))).isDisplayed();


            if (description.getAnnotation(Tag.class).value().equals(Tags.CLEAN_CACHE)) {
                mainPage.getHeader().getChooseDeliveryOrPickUpButton().clickButton();
                setCity(city);
                setPickupFromStore();
                setStore(firstStore);

                mainPage.getHeader().getActiveAddress().shouldHave(Condition.text(firstStore));

            } else if (description.getAnnotation(Tag.class).value().equals(Tags.EXISTING_COOCKIES)) {
//                if(new File("Cookies.data").exists()){
//                    cookieHandler.handleCookies(driver);
//                    open(host);
//                }
//                else {
                Response login = new UserAPI().post_login(defaultUser);
                log.info(login.body().print());
                cookieHandler.writeCookiesToFile(login.getDetailedCookies().asList());
                cookieHandler.handleCookies(driver);
                open(host);
            }

            screenshotService = new ScreenshotService(driver);
            return super.apply(base, description);
        }


        @Override
        protected void succeeded(Description description) {
            log.info("Test " + description.getMethodName() + " complete successfully");
            super.succeeded(description);
        }

        @Override
        protected void failed(Throwable e, Description description) {
            makeScreenshotOnFailure();
            screenshotService.captureScreenshot(description.getMethodName());
            CommonHelpers.saveHTMLToFile(driver, "report/html/" + description.getMethodName() + ".html");
            log.info("Test " + description.getMethodName() + " failed. The screenshot have been taken");
            super.failed(e, description);
        }

        @Attachment("Screenshot on failure")
        public byte[] makeScreenshotOnFailure() {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        }

        @Override
        protected void starting(Description description) {
            log.info("Test " + description.getMethodName() + " started");
            super.starting(description);
        }

        @Override
        protected void finished(Description description) {
            webDriverService.stopDriver(driver);
            super.finished(description);
        }
    };

    private void setCity(String city) {
        CitySelectionPopup citySelectionPopup = new MainPage(driver).getHeader().getCitySelectionPopup();

        log.info("change city button click");
        citySelectionPopup
                .changeCityButton()
                .clickButton();

        log.info("select city");
        citySelectionPopup
                .selectCity(city);
    }

    private void setStore(String store) {
        StoreSelectionPopup storeSelectionPopup = new MainPage(driver).getHeader().getStoreSelectionPopup();
        log.info("select store");
        storeSelectionPopup
                .changeCityButton()
                .clickButton();

        storeSelectionPopup
                .selectElement(store)
                .saveChanges()
                .clickButton();
    }

    private void setPickupFromStore() {
        DeliveryPopUp deliveryPopup = new MainPage(driver).getHeader().getDeliveryPopup();

        log.info("select Pickup / shop ");
        deliveryPopup
                .getPickUp()
                .clickButton();
    }
}
