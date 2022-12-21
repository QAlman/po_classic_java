package web.ops.logic.site;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import common.dto.Good;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import web.objs.pages.site.CommercialOfferPage;

import static web.ops.util.datautils.ConfigPropertiesUtil.getProperty;

public class CommercialPageOps {

    private final GeneralInformation generalInformation;
    private final CompanyInformation companyInformation;
    private final ContactsInformation contactsInformation;
    private final RepresenceInformation represenceInformation;
    private final Logger log = Logger.getLogger(this.getClass().getName());

    private final GoodInformation goodInformation;

    CommercialOfferPage commercialOfferPage;

    public GeneralInformation getGeneralInformation() {
        return generalInformation;
    }

    public CompanyInformation getCompanyInformation() {
        return companyInformation;
    }

    public ContactsInformation getContactsInformation() {
        return contactsInformation;
    }

    public CommercialOfferPage getCommercialOfferPage() {
        return commercialOfferPage;
    }

    public RepresenceInformation getRepresenceInformation() {
        return represenceInformation;
    }

    public GoodInformation getGoodInformation() {
        return goodInformation;
    }


    public CommercialPageOps(WebDriver driver) {
        commercialOfferPage = new CommercialOfferPage(driver);
        generalInformation = new GeneralInformation();
        companyInformation = new CompanyInformation();
        contactsInformation = new ContactsInformation();
        represenceInformation = new RepresenceInformation();
        goodInformation = new GoodInformation();
    }

    public class GeneralInformation {
        public void submit() {
            log.info("submit changes");
            commercialOfferPage.nextButton().click();

        }

        public GeneralInformation acceptRules() {
            log.info("accept rules");
            commercialOfferPage.rulesToggleCheckbox().waitUntil(Condition.visible, Long.parseLong(getProperty("driver.wait.timeout"))).click();
            return this;
        }

        public GeneralInformation raiseRequest() {
            log.info("Create commercial request");
            commercialOfferPage.raisProjectmercialOfferButton().click();
            return this;
        }

        public GeneralInformation wasOfferBeafore(boolean b) {
            log.info("set field \"Offer before\" with balue " + b);
            commercialOfferPage.setOfferBefore(b).click();
            return this;
        }

        public GeneralInformation isYourCompanyResident(boolean b) {
            log.info("set field \"Residence\" with balue " + b);
            commercialOfferPage.setResidentCheckBox(b).click();
            return this;
        }

        public GeneralInformation isYourOfferNeedOwnTradeMark(boolean b) {
            log.info("set field \"Is your own trademark\" with balue " + b);
            commercialOfferPage.setTradeMarkOffer(b).click();
            return this;
        }


    }

    public class CompanyInformation {

        public void submit() {
            log.info("submit changes");
            commercialOfferPage.nextButton().click();
        }

        public CompanyInformation setName(String name) {
            log.info("set name " + name);
            commercialOfferPage.companyName().sendKeys(name);
            return this;
        }

        public CompanyInformation setINN(String INN) {
            log.info("set INN " + INN);
            commercialOfferPage.companyInn().sendKeys(INN);
            return this;
        }

        public CompanyInformation setLegalAdress(String adress) {
            log.info("Set legal adress " + adress);
            commercialOfferPage.companyLegalAdress().sendKeys(adress);
            return this;
        }

        public CompanyInformation setPhysicalAdress(String adress) {
            log.info("Set physical adress " + adress);
            commercialOfferPage.companyPhysicalAdress().sendKeys(adress);
            return this;
        }

        public CompanyInformation setSite(String site) {
            log.info("set site " + site);
            commercialOfferPage.companySite().sendKeys(site);
            return this;
        }

        public CompanyInformation setLoyaltySystem(String value) {
            log.info("set loyalty system " + value);
            commercialOfferPage.setLoyaltySystem(value);
            return this;
        }

        public CompanyInformation setRetailRegions(String value) {
            log.info("set retail regions " + value);
            commercialOfferPage.setRetailRegion(value);
            return this;
        }

        public CompanyInformation isYourCompanyDistributor(boolean value) {
            log.info("set field \"is your compani distributor\" with " + value);
            commercialOfferPage.setDistributionCheckBox(value).click();
            return this;
        }

        public CompanyInformation setRegistrationCountry(String country) {
            log.info("set reistration country " + country);
            commercialOfferPage.registrationCountry().sendKeys(country);
            return this;

        }
    }

    public class ContactsInformation {

        public void submit() {
            log.info("submit changes");
            commercialOfferPage.nextButton().click();
        }

        public ContactsInformation setContactName(String name) {
            log.info("set contact name " + name);
            commercialOfferPage.contactName().sendKeys(name);
            return this;
        }

        public ContactsInformation setPosition(String position) {
            log.info("set position " + position);
            commercialOfferPage.contactPosition().sendKeys(position);
            return this;
        }

        public ContactsInformation setMail(String mail) {
            log.info("set mail " + mail);
            commercialOfferPage.contactEmail().sendKeys(mail);
            return this;
        }

        public ContactsInformation setWorkPhone(String phone) {
            log.info("set work phone " + phone);
            commercialOfferPage.contactWorkPhone().sendKeys(phone);
            return this;
        }

        public ContactsInformation setMobilePhone(String phone) {
            log.info("set mobile phone " + phone);
            commercialOfferPage.contactMobilePhone().sendKeys(phone);
            return this;
        }
    }

    public class RepresenceInformation {

        public void submit() {
            log.info("submit changes");
            commercialOfferPage.nextButton().click();
        }

        public RepresenceInformation companyTurnoverAYear(String value) {
            log.info("set turnover " + value);
            commercialOfferPage.companyTurnOver().sendKeys(value);
            return this;
        }

        public RepresenceInformation companyYearOfAppear(String value) {
            log.info("set appearing year " + value);
            commercialOfferPage.companyYearOfAppear().sendKeys(value);
            return this;
        }

        public RepresenceInformation companyRepresentsInTradeWeb(String value) {
            log.info("set represent " + value);
            commercialOfferPage.setTradeWebs(value);
            return this;
        }

        public RepresenceInformation companyOfferGeography(String value) {
            log.info("set geography " + value);
            commercialOfferPage.setTradeGeography(value);
            return this;
        }

        public RepresenceInformation companyOffer(String value) {
            log.info("set company offer " + value);
            commercialOfferPage.tradProjectplex(value).click();
            return this;
        }

    }

    public class GoodInformation {
        Good good;

        public GoodInformation() {
            good = new Good();

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
        }

        public GoodInformation addGoodAsResident(WebDriver driver, Good good) {
            SelenideElement element = commercialOfferPage.addGood();
            element.scrollIntoView(false);
            element.click();
            log.info("set good " + good);

            commercialOfferPage.goodName().sendKeys(good.getName());
            commercialOfferPage.goodBrand().sendKeys(good.getBrand());
            commercialOfferPage.goodPrice().sendKeys(good.getPrice());
            commercialOfferPage.setGoodPath(good.getPath());
            commercialOfferPage.setGoodCategory(good.getCategory());
            commercialOfferPage.goodCountry().sendKeys(good.getProducerCountry());
            commercialOfferPage.goodWeight().sendKeys(good.getWeight());
            commercialOfferPage.goodTemperatureReglament().sendKeys(good.getTemperatureReglament());
            commercialOfferPage.goodExpirationDate().sendKeys(good.getExpirationDate());
            commercialOfferPage.goodNDS().sendKeys(good.getNDS());
            commercialOfferPage.submitGood().click();
            return this;
        }

        public GoodInformation addGoodAsNotResident(Good good) {
            SelenideElement element = commercialOfferPage.addGood();
            element.scrollIntoView(false);
            element.click();

            commercialOfferPage.goodName().sendKeys(good.getName());
            commercialOfferPage.goodBrand().sendKeys(good.getBrand());
            commercialOfferPage.goodPriceTU().sendKeys(good.getPrice());
            commercialOfferPage.setGoodPath(good.getPath());
            commercialOfferPage.setGoodCategory(good.getCategory());
            commercialOfferPage.goodCountry().sendKeys(good.getProducerCountry());
            commercialOfferPage.goodWeight().sendKeys(good.getWeight());
            commercialOfferPage.goodTemperatureReglament().sendKeys(good.getTemperatureReglament());
            commercialOfferPage.setTypeOfOffer("Брендированный ассортимент");
            commercialOfferPage.goodImportPart().sendKeys("10");
            commercialOfferPage.goodTradeUnit().sendKeys("1");
            commercialOfferPage.incoterms().sendKeys("asd");

            commercialOfferPage.submitGood().click();
            return this;
        }

        public GoodInformation submitReCaptcha() {
            commercialOfferPage.reCaptchaCheckBox().click();
            return this;
        }

        public void submitOffer() {
            commercialOfferPage.sendOffer().click();

        }
    }
}
