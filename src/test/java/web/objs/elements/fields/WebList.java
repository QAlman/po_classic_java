package web.objs.elements.fields;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import java.util.List;
import java.util.Objects;


import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WebList {
    private static   SelenideElement webList;
    private static List<SelenideElement> childElements;

    public WebList(){}

    public WebList(By by) {
        SelenideElement firstList = $(by);

        ElementsCollection getAllListFromTheDom = $$(by);

        webList = getAllListFromTheDom.stream()
                .filter(SelenideElement::isDisplayed)
                .findFirst()
                .orElse(firstList);
    }

    public static WebList fromElement(SelenideElement element){
        webList = element;
        return new WebList();
    }

    private List<SelenideElement> getChildElements() {
        return webList.$$(byXpath(".//*"));
    }

    public SelenideElement getListElementByInnerText(String name) {
        childElements = getChildElements();

        return childElements.stream()
                .filter(SelenideElement::isDisplayed)
                .filter(selenideElement -> Objects.requireNonNull(selenideElement.getText()).equals(name))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Element not found: "+name));
    }


    public SelenideElement getListElementByCondition(Condition condition) {
        childElements = getChildElements();
        return childElements.stream()
                .filter(element -> element.has(condition))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Element not found"));
    }


    public SelenideElement getElement(int number) {
        childElements = getChildElements();
        return childElements.get(number);
    }

    public List<SelenideElement> getChildElements(By by) {
        return webList.$$(by);
    }
    public SelenideElement returnAsElement(){
        return webList;
    }

}
