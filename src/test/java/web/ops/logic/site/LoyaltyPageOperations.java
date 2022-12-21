package web.ops.logic.site;


import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import web.objs.pages.site.LoyaltyPage;

import java.util.*;

public class LoyaltyPageOperations {
    LoyaltyPage loyaltyPage;

    private final Logger log = Logger.getLogger(this.getClass().getName());

    public LoyaltyPageOperations(WebDriver driver) {
        this.loyaltyPage = new LoyaltyPage(driver);
    }

    public List<SelenideElement> getListSelectedCategories(Map<String, List <SelenideElement>> map) {
       List<SelenideElement> selectedElements = new LinkedList<>() ;
       for(int i=1; i<6; i++){
           selectedElements.add(getElementByKeyFromMapWithLists(map,i+"element"));
       }
        return selectedElements;

    }

    public SelenideElement getElementByKeyFromMapWithLists(Map<String, List <SelenideElement>> map, String number) {
        SelenideElement element = null;
        try{
            element = map.get(number).get(0);
        }
        catch (NullPointerException e){
            log.info("Element by "+ number+" not found");
        }
        return element;
    }

    public List<SelenideElement> getFiveUniqueRandomUnSelectedElement(Map<String, List <SelenideElement>> map) {
        log.info("Get random unselected element from map");
        List<SelenideElement> randomElements = map.get("element");
        List<SelenideElement> fiveUniqueRandomElements = new ArrayList<>();
        Collections.shuffle(randomElements);
        for (int i=0; i<5; i++){
            fiveUniqueRandomElements.add(randomElements.get(i));
        }
        return fiveUniqueRandomElements;
    }

    public String getTutorialText(int numSlide){
        String slide;
        switch (numSlide){
            case(1):
              slide = loyaltyPage.slideOne().getText();
                if(slide == null){
                    getTutorialText(1);
                }else {
                return slide;
                }
            case(2):
               slide = loyaltyPage.slideTwo().getText();
                if(slide == null){
                    getTutorialText(2);
                }else {
                    return slide;
                }
            case(3):
                slide = loyaltyPage.slideThree().getText();
                if(slide == null){
                    getTutorialText(3);
                }else {
                    return slide;
                }

        }
        return null;
    }

    public void selectFiveCategories(){

        log.info("Get list five unique categories");
        List<SelenideElement> unSelectedFiveUniqueCategoriesBeforeChange = getFiveUniqueRandomUnSelectedElement(loyaltyPage.getCategoriesMap());
        unSelectedFiveUniqueCategoriesBeforeChange.iterator().forEachRemaining(element -> log.info(element));

        log.info("Select five categories");
        for (SelenideElement selenideElement : unSelectedFiveUniqueCategoriesBeforeChange) {
            selenideElement.click();
        }

        log.info("Save categories");
        loyaltyPage.acceptCategories().click();

    }
}


