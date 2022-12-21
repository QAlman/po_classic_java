package web.objs.elements.buttons;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static web.ops.util.datautils.ConfigPropertiesUtil.getProperty;

public class Button {
    SelenideElement element;

    public Button(SelenideElement element){
        this.element=element;
    }

    public void clickButton(){
        element.waitUntil(Condition.visible,Long.parseLong(getProperty("driver.wait.timeout"))).click();
    }

    public void hoverButton(){
        element.waitUntil(Condition.visible,Long.parseLong(getProperty("driver.wait.timeout"))).hover();
    }

    public String getText(){
       return element.waitUntil(Condition.visible,Long.parseLong(getProperty("driver.wait.timeout"))).getText();
    }

    public SelenideElement returnAsElement(){
        return element.waitUntil(Condition.visible,Long.parseLong(getProperty("driver.wait.timeout")));
    }
}
