package web.objs.pages.remotesystems;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import web.ops.helpers.UIHelpers;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class Siebel {
    WebDriver driver;

    public Siebel(WebDriver driver) {
        this.driver = driver;
    }

    public SelenideElement userName() {
        return $(byName("SWEUserName"));
    }

    public SelenideElement password() {
        return $(byName("SWEPassword"));
    }

    public SelenideElement submitLogin() {
        return $(byClassName("siebui-login-btn"));
    }

    public SelenideElement selectTab(String tabName) {
        SelenideElement element;
        try {
            element = $(byText(tabName));
        } catch (Throwable ex) {
            element = $(byId("j_s_sctrl_tabScreen")).$(byText(tabName));
        }
        return element;
    }

    public SelenideElement tasksButton() {
        return $(byXpath("//li[@id='tb_9']/span"));
    }

    public SelenideElement selectTask(String name) {
        SelenideElement element = $(byId("taskList"));
        return element.$(byText(name));
    }

    public SelenideElement field(String fieldName) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SelenideElement element = $(byAttribute("aria-label", fieldName));
        UIHelpers.scrollToElement((JavascriptExecutor) driver, element);

        return element;
    }

    public SelenideElement createCardButton() {
        return $(byText("Создать карту"));
    }

    public SelenideElement saveClientButton() {
        SelenideElement element = $(byId("s_1_1_2_0_Ctrl"));
        UIHelpers.scrollToElement((JavascriptExecutor) driver, element);
        return element;
    }

    public SelenideElement clientList() {
        return $(byText("Список клиентов"));
    }

    public void searchBy(String value) {
        SelenideElement element = $(byAttribute("aria-label", "Поиск"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        element.sendKeys(value);
    }

    public SelenideElement searchField() {
        return $(byAttribute("aria-label", "Начиная с"));
    }

    public SelenideElement searchButton() {
        return $(byAttribute("data-display", "Перейти"));
    }

    public SelenideElement foundUser() {
        return $(byLinkText("Могрейн"));
    }

    public SelenideElement ops() {
        return $(byAttribute("aria-label", "Панель представления третьего уровня"));
    }

    public SelenideElement operation(String operation) {
        return $(byText(operation));
    }

    public SelenideElement selectAllSystems() {
        return $(byAttribute("aria-label", "Выгрузить во все системы"));
    }

    public SelenideElement uploadButton() {
        return $(byAttribute("data-display", "Выгрузить"));
    }
}
