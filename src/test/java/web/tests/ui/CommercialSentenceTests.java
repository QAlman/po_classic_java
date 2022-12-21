package web.tests.ui;

import common.cnst.Tags;
import common.dto.Good;
import io.qameta.allure.Owner;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import web.objs.pages.site.MainPage;
import web.ops.logic.site.CommercialPageOps;
import web.ops.logic.site.MainPageOperations;

public class CommercialSentenceTests extends BaseUIClass {
    /*TODO:
    надо переделать метод, акцептящий куки, он слишком зависим от обстоятельств
     */
    @Before
    public void setUp() {

        commercialPageOps = new CommercialPageOps(driver);
        mainPageOperations = new MainPageOperations(driver);

        new MainPage(driver).toCommercialOfferPage().click();

    }

    @Test
    @DisplayName("Отправка коммерческого предложения РЕЗИДЕНТ")
    @Owner(value = "Антон")
    @Tag(Tags.CLEAN_CACHE)
    public void residentCommercialOfferTest() {
        Good good = new Good();

        good.setProducerCountry("Гондурас");
        good.setWeight("100");
        good.setNDS("10");
        good.setExpirationDate("30д");
        good.setTemperatureReglament("20");
        good.setPath("Бакалея");
        good.setCategory("Масло");
        good.setName("Болт");
        good.setBrand("FFF");
        good.setPrice("100");

        mainPageOperations
                .acceptCookies();

        commercialPageOps.getGeneralInformation()
                .raiseRequest();

        mainPageOperations.acceptCookies();

        commercialPageOps.getGeneralInformation()
                .acceptRules()
                .wasOfferBeafore(false)
                .isYourCompanyResident(true)
                .isYourOfferNeedOwnTradeMark(false)
                .submit();

        commercialPageOps.getCompanyInformation()
                .setName(testData.getVariable("companyName"))
                .setINN(testData.getVariable("companyPhone"))
                .setLegalAdress(testData.getVariable("legalAdress"))
                .setPhysicalAdress(testData.getVariable("physicalAdress"))
                .isYourCompanyDistributor(false)
                .setSite(testData.getVariable("companySite"))
                .setLoyaltySystem(testData.getVariable("companyLoyaltySystem"))
                .setRetailRegions(testData.getVariable("retailRegions"))
                //.isYourCompanyDistributor(false)
                .submit();

        commercialPageOps.getContactsInformation()
                .setContactName(testData.getVariable("contactName"))
                .setPosition(testData.getVariable("contactPosition"))
                .setMail(testData.getVariable("contactMail"))
                .setWorkPhone(testData.getVariable("contactWorkPhone"))
                .setMobilePhone(testData.getVariable("contactMobilePhone"))
                .submit();

        commercialPageOps.getRepresenceInformation()
                .companyTurnoverAYear(testData.getVariable("companyTurnoverAYear"))
                .companyYearOfAppear(testData.getVariable("companyYearOfAppear"))
                .companyRepresentsInTradeWeb(testData.getVariable("companyRepresentativeInTradeWeb"))
                .companyOfferGeography(testData.getVariable("companyOfferGeography"))
                .companyOffer(testData.getVariable("companyOffer"))
                .submit();

        commercialPageOps.getGoodInformation()
                .addGoodAsResident(driver, good)
                //.submitReCaptcha()
                .submitOffer();

        Assert.assertTrue(true);
    }

    @Test
    @DisplayName("Отправка коммерческого предложения НЕ РЕЗИДЕНТ")
    @Owner(value = "Антон")
    @Tag(Tags.CLEAN_CACHE)
    public void notResidentCommercialOfferTest() {
        Good good = new Good();

        good.setProducerCountry("Гондурас");
        good.setWeight("100");
        good.setNDS("10");
        good.setExpirationDate("30д");
        good.setTemperatureReglament("20");
        good.setPath("Бакалея");
        good.setCategory("Масло");
        good.setName("Болт");
        good.setBrand("FFF");
        good.setPrice("100");

        mainPageOperations
                .acceptCookies();

        commercialPageOps.getGeneralInformation()
                .raiseRequest();

        mainPageOperations
                .acceptCookies();

        commercialPageOps.getGeneralInformation()
                .acceptRules()
                .wasOfferBeafore(false)
                .isYourCompanyResident(false)
                .isYourOfferNeedOwnTradeMark(false)
                .submit();

        commercialPageOps.getCompanyInformation()
                .setName(testData.getVariable("companyName"))
                .setINN(testData.getVariable("companyPhone"))
                .isYourCompanyDistributor(false)
                .setRegistrationCountry(testData.getVariable("companyRegistrationCountry"))
                .setLegalAdress(testData.getVariable("physicalAdress"))
                .setSite(testData.getVariable("companySite"))
                .submit();

        commercialPageOps.getContactsInformation()
                .setContactName(testData.getVariable("contactName"))
                .setPosition(testData.getVariable("contactPosition"))
                .setMail(testData.getVariable("contactMail"))
                .setWorkPhone(testData.getVariable("contactWorkPhone"))
                .setMobilePhone(testData.getVariable("contactMobilePhone"))
                .submit();

        commercialPageOps.getRepresenceInformation()
                .companyYearOfAppear(testData.getVariable("companyYearOfAppear"))
                .companyRepresentsInTradeWeb(testData.getVariable("companyRepresentativeInTradeWeb"))
                .companyOfferGeography(testData.getVariable("companyOfferGeography"))
                .companyOffer(testData.getVariable("companyOffer"))
                .submit();

        commercialPageOps.getGoodInformation()
                .addGoodAsNotResident(good)
//                .submitReCaptcha()
                .submitOffer();

        Assert.assertTrue(true);

    }


}
