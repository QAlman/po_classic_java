package web.objs.elements.fields;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.util.stream.IntStream;

public class InputField {
    SelenideElement element;

    public InputField(SelenideElement element){
        this.element=element;
    }

    public void setValue(String value){
        element.sendKeys(value);
    }

    public void clearField(){
        IntStream.range(0, element.getText().length()).forEach(i -> element.sendKeys(Keys.BACK_SPACE));
    }

    public String getFieldValue(){
       return element.getText();
    }

    public SelenideElement getElement(){
        return element;
    }

}
