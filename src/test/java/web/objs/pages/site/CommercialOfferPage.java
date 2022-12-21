package web.objs.pages.site;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import web.objs.elements.buttons.RadioButton;
import web.objs.elements.fields.WebList;
import web.objs.pages.BasePage;

import java.util.Arrays;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CommercialOfferPage extends BasePage {

    public CommercialOfferPage(WebDriver driver) {
        super(driver);
    }

    public SelenideElement raiseCommercialOfferButton() {
        return $(byLinkText("Оставить коммерческое предложение"));
    }

    public SelenideElement rulesToggleCheckbox() {
        return $(byClassName("checkbox-field__toggle"));
    }

    public SelenideElement setOfferBefore(boolean b) {
        return new RadioButton(getSupplyField("Направлялось ли Вами ранее коммерческое предложение")).getRadioButtonElementByName(isValid(b));
    }

    public SelenideElement setResidentCheckBox(boolean b) {
        return new RadioButton(getSupplyField("Ваша компания является резидентом РФ")).getRadioButtonElementByName(isValid(b));
    }

    public SelenideElement setTradeMarkOffer(boolean b) {
        return new RadioButton(getSupplyField("обращение содержит коммерческое предложение")).getRadioButtonElementByName(isValid(b));
    }

    public SelenideElement nextButton() {
        return $(byText("Далее"));
    }

    public SelenideElement companyName() {
        return $(byName("generalinfo_company"));
    }

    public SelenideElement companyInn() {
        return getField("ИНН").$(byClassName("form-constructor__input-field"));
    }

    public SelenideElement companyLegalAdress() {
        return $(byName("generalinfo_legalAddress"));
    }

    public SelenideElement companyPhysicalAdress() {
        return $(byName("generalinfo_physicalAddress"));
    }

    public SelenideElement companySite() {
        return $(byName("generalinfo_link"));
    }

    public WebList dropDownList() {
        return new WebList(byClassName("dropdown__select"));
    }

    public void setLoyaltySystem(String value) {
        getField("Система налогооблажения").click();
        dropDownList().getListElementByInnerText(value).click();
    }

    public void setRetailRegion(String value) {
        getField("Регионы, в которых предполагается реализация продукции").click();
        dropDownList().getListElementByInnerText(value).click();
    }


    public SelenideElement setDistributionCheckBox(boolean value) {
        String distributor;
        if (value) distributor = "Производитель";
        else distributor = "Дистрибьютор";

        return new RadioButton(getField("Вы являетесь")).getRadioButtonElementByName(distributor);
    }

    public SelenideElement contactName() {
        return $(byName("contactinfo_contact"));
    }

    public SelenideElement contactPosition() {
        return $(byName("contactinfo_position"));
    }


    public SelenideElement contactEmail() {
        return $(byName("contactinfo_email"));
    }

    public SelenideElement contactWorkPhone() {
        return $(byName("contactinfo_workNumber"));
    }

    public SelenideElement contactMobilePhone() {
        return $(byName("contactinfo_mobileNumber"));
    }

    public SelenideElement companyTurnOver() {
        return $(byName("companyinfo_turnover"));
    }

    public SelenideElement companyYearOfAppear() {
        return $(byName("companyinfo_establishDate"));
    }

    public void setTradeWebs(String value) {

        String[] webs = value.split(",");

        SelenideElement element = getField("Представленность");

        ElementsCollection checkbox = element.$$(byClassName("checkbox-field__toggle"));
        for (SelenideElement selenideElement : checkbox) {

            for (int i = 0; i < webs.length; i++) {
                if (selenideElement.getText().contains(webs[i])) {
                    selenideElement.click();
                    webs[i] = "null";
                }
            }

        }

        String asd = new String(Arrays.toString(webs))
                .replace("null,", "")
                .replace("[", "")
                .replace("]", "");

        $(byName("companyinfo_representationOther")).sendKeys(asd);
    }

    public void setTradeGeography(String value) {
        String[] webs = value.split(",");

        SelenideElement element = getField("География существующих поставок");
        ElementsCollection checkbox = element.$$(byClassName("checkbox-field__toggle"));

        for (SelenideElement selenideElement : checkbox) {
            for (String web : webs) {
                if (selenideElement.getText().contains(web)) {
                    selenideElement.click();
                }
            }

        }

    }

    public SelenideElement tradeComplex(String value) {
        return new RadioButton(getField("Способ поставки")).getRadioButtonElementByName(value);
        //return getRadioButtons(value, getField("Способ поставки"));
    }

    public SelenideElement addGood() {
        ElementsCollection elements = $$(byClassName("form-constructor__field"));
        //-------THIS-IS-KOSTYL-------------
        /**DO NOT REMOVE THAT SYSTEM OUT. TEST WILL NOT WORK
         * I dont know why
         * If you will know - write here**/
        System.out.println(elements);
        //-------END-OF-KOSTYL--------------
        for (SelenideElement element : elements) {
            if (element.getText().contains("Полное наименование товара")) {
                return element.$(byAttribute("type", "button"));
            }
        }
        return null;
    }

    public SelenideElement goodName() {
        return $(byName("commercialofferinfo_name"));
    }

    public SelenideElement goodBrand() {
        return $(byName("commercialofferinfo_brand"));
    }

    public SelenideElement goodPrice() {
        return $(byName("commercialofferinfo_price"));
    }

    public SelenideElement goodPriceTU() {
        return $(byName("commercialofferinfo_priceTU"));
    }


    public SelenideElement goodCountry() {
        return $(byName("commercialofferinfo_prodCountry"));
    }


    public SelenideElement goodWeight() {
        return $(byName("commercialofferinfo_weight"));
    }

    public SelenideElement goodTemperatureReglament() {
        return $(byName("commercialofferinfo_stocktemp"));
    }

    public SelenideElement goodNDS() {
        return $(byName("commercialofferinfo_vat"));
    }

    public SelenideElement goodExpirationDate() {
        return $(byName("commercialofferinfo_shelflife"));
    }

    public void setGoodPath(String path) {
        ElementsCollection elements = $$(byClassName("table-field__element"));

        for (SelenideElement element : elements) {

            if (element.getText().contains("Направление")) {
                element.click();

                dropDownList().getListElementByInnerText(path).click();
            }
        }
    }


    public void setGoodCategory(String category) {
        ElementsCollection elements = $$(byClassName("table-field__element"));

        for (SelenideElement element : elements) {

            if (element.getText().contains("Категория")) {
                element.click();

                dropDownList().getListElementByInnerText(category).click();
            }
        }
    }


    public SelenideElement sendOffer() {
        return $(byText("Отправить"));
    }

    public SelenideElement reCaptchaCheckBox() {
        return $(byClassName("recaptcha-checkbox"));
    }

    public SelenideElement submitGood() {
        return $(byText("Готово"));
    }

    public SelenideElement registrationCountry() {
        return $(byName("generalinfo_country"));
    }

    public void setTypeOfOffer(String value) {
        ElementsCollection elements = $$(byClassName("table-field__element"));

        for (SelenideElement element : elements) {

            if (element.getText().contains("Вид предложения")) {
                element.click();
                dropDownList().getListElementByInnerText(value).click();
            }
        }
    }

    public SelenideElement goodImportPart() {
        return $(byName("commercialofferinfo_import"));
    }

    public SelenideElement goodTradeUnit() {
        return $(byName("commercialofferinfo_tradeUnit"));
    }

    public SelenideElement incoterms() {
        return $(byName("commercialofferinfo_incoterms"));
    }

    //======================================================================================================================
    private SelenideElement getField(String name) {
        ElementsCollection elements = $$(byClassName("form-constructor__field"));
        return elements.stream()
                .filter(element -> element.getText().contains(name))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(name));
    }

    private SelenideElement getSupplyField(String name) {
        ElementsCollection elements = $$(byClassName("supplier-questionary__field"));
        return elements.stream()
                .filter(element -> element.getText().contains(name))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(name));
    }

    private String isValid(boolean b) {
        if (b) return "Да";
        else return "Нет";
    }
//======================================================================================================================

}
