package web.ops.logic.site;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import common.dto.User;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import web.objs.elements.containers.Header;
import web.objs.pages.site.PersonalCabinetPage;
import web.ops.api.UserAPI;

public class PersonalCabinetOperations {
    private final PersonalCabinetPage personalCabinetPage;
    private final UserAPI userAPI = new UserAPI();
    private final Logger log = Logger.getLogger(this.getClass().getName());

    public PersonalCabinetOperations(WebDriver driver) {
        this.personalCabinetPage = new PersonalCabinetPage(driver);
    }

    public PersonalCabinetOperations openLastMessage() {
        log.info("Go to message");
        personalCabinetPage.getUserName().click();
        log.info("Open Last message");
        personalCabinetPage.messageButton().click();
        personalCabinetPage.lastUnreadMessage().click();
        return this;
    }

    public JsonArray getMessages(Cookies cookies) {
        log.info("Get message");
        Response response =userAPI.get_getComplaints(cookies);
        return JsonParser.parseString(response.getBody().asString()).getAsJsonArray();
    }
    public void deleteMessage(Cookies cookies, String complaintGuid) {
        log.info("delete message");
        userAPI.delete_deleteComplaints(cookies, complaintGuid);

    }
    public PersonalCabinetOperations deleteAllMessage(User user) {
        log.info("deleting all messages");
        Cookies cookies = userAPI.loginAndGetCookies(user);
        JsonArray complaints = getMessages(cookies);
        for(int i = 0; i<complaints.size(); i++){
            deleteMessage(cookies, complaints.get(i).getAsJsonObject().get("Id").getAsString());
        }
        return this;
    }

    public PersonalCabinetOperations goToStampsFromLK() {
        log.info("GO to stamps page from LK");
        personalCabinetPage.stampsLinkFromLK().click();
        return this;
    }

    public PersonalCabinetOperations logout() {
        log.info("Logout");
        Header header = personalCabinetPage.getHeader();
        header.getUserMenu().getListElementByInnerText("Выйти").click();
        return this;
    }
}
