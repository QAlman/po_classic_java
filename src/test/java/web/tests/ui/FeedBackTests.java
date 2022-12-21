package web.tests.ui;

import common.cnst.Tags;
import io.qameta.allure.Owner;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import web.objs.pages.site.EpiServerPage;
import web.objs.pages.site.GoodPage;
import web.objs.pages.site.PersonalCabinetPage;
import web.ops.logic.site.EpiServerPageOps;
import web.ops.logic.site.GoodPageOperations;
import web.ops.logic.site.LoginPageOperations;
import web.ops.logic.site.MainPageOperations;

import static web.ops.helpers.UIHelpers.openPage;
import static web.ops.util.datautils.ConfigPropertiesUtil.getProperty;

public class FeedBackTests extends BaseUIClass {
    private static final Logger log = Logger.getLogger(OptionsWithoutLoginTests.class.getName());


    @Before
    public void setUp() {
        goodPage = new GoodPage(driver);
        epiServerPage = new EpiServerPage(driver);

        mainPageOperations = new MainPageOperations(driver);
        goodPageOperations = new GoodPageOperations(driver);
        loginPageOperations = new LoginPageOperations(driver);
        epiServerPageOps = new EpiServerPageOps(driver);

    }

    @Test
    @DisplayName("Добавить отзыв к товару")
    @Owner(value = "Антон")
    @Tag(Tags.CLEAN_CACHE)
    public void addFeedbackToProductTest() {

        mainPageOperations
                .goToLoginPage()
                .acceptCookies();

        loginPageOperations.logInWithPass(getProperty("site.username"), getProperty("site.password"));

        log.info("Open product`s page");
        openPage(driver, getProperty("site.host") + testData.getVariable("productLink"));


        goodPageOperations.setFeedback();

        loginPageOperations.logOut(new PersonalCabinetPage(driver));

        log.info("Open episerver");
        openPage(driver, getProperty("site.host") + "/util/login.aspx");

        epiServerPageOps.logInEpi(
                "epiadmin",
                "Password1234!");
        openPage(driver, getProperty("site.host") + testData.getVariable("episerverLink"));

        epiServerPageOps.applyComments();

        log.info("Open product`s page");
        openPage(driver, getProperty("site.host") + testData.getVariable("productLink"));


        goodPage.feedBackTab().click();
        String text = goodPage.feedBackComment().getText();

        Assert.assertTrue(text.contains("Александрос"));
        Assert.assertTrue(text.contains("Не оч"));
    }

}
