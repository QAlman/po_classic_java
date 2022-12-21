package web.tests.rest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import common.builder.UserBuilder;
import common.cnst.Tags;
import common.dto.User;
import common.service.UserService;
import io.qameta.allure.Owner;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import web.ops.api.WalletAPI;

@RunWith(JUnit4.class)
public class WalletAPITests extends BaseRestClass {

    User user = new User();
    String fixedCardNumber = "470000072783";
    String fixedPhoneNumber = "79620480100";
    WalletAPI walletAPI =new WalletAPI();

    @Before
    public void setUp() {
        UserBuilder userBuilder = new UserBuilder();
        user = userBuilder
                .withFirstName("Утер")
                .withLastName("Светоносный")
                .withPatronimic("Васильевич")
                .withBirthDate("1993-08-28")
                .withPhone(fixedPhoneNumber)
                .withEmail(fixedPhoneNumber + "@cat.com")
                .withSex("м")
                .withDefaultPassword()
                .execute();

        walletAPI.generateJsonFromUserData(user);
    }

    @Test
    @DisplayName("Запос карты пользователя API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void getUserCardTest() {
        Response response = walletAPI.get_UserCard(user);

        JsonObject jsonObject = JsonParser.parseString(response.getBody().asString()).getAsJsonObject();
        fixedCardNumber = jsonObject.getAsJsonObject("card").get("cardNumber").getAsString();

        Assert.assertEquals(200, response.statusCode());
        Assert.assertNotNull(fixedCardNumber);
    }

    @Test
    @DisplayName("Обновление канала API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void channelUpdateTest() {
        Response response = walletAPI.channelUpdate(fixedCardNumber);

        System.out.println(response.getBody().asString());

        Assert.assertEquals(200, response.statusCode());
    }

    @Test
    @DisplayName("Найти пользователя по номеру телефона и дате рождения API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void findUserByPhone_v2_api_Test() {
        new UserBuilder().withPhone("79620488011").withBirthDate("1993-28-08");

        Response response = walletAPI.findUserByPhoneAndBirthdate(user);

        log.info(response.statusCode());
        log.info(response.getBody());

        Assert.assertEquals(200, response.statusCode());
        Assert.assertNotNull(response.getBody().asString());
        Assert.assertTrue(ìsRequiredFieldsInResponse(testData.getVariable("fields"), response.getBody().asString()));
    }

    @Test
    @DisplayName("Найти юзера по номеру карты API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void findUserbyCard_v2_api_Test() {
        Response response = walletAPI.getUserByCard_v2_api("490000000031");

        log.info(response.statusCode());
        log.info(response.getBody().asString());

        Assert.assertEquals(200, response.statusCode());
        Assert.assertNotNull(response.getBody());
        Assert.assertTrue(ìsRequiredFieldsInResponse(testData.getVariable("fields"), response.getBody().asString()));
    }

    @Test
    @DisplayName("Выпустить новую карту API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void publishNewCard_v2_api_Test() {
        String phone = new UserService().generatePhoneNumber();
        String card = new UserService().generateCardNumber(4900);

        User user = new UserBuilder()
                .withFirstName("Алоисий")
                .withLastName("Баранов")
                .withPatronimic("Макарыч")
                .withBirthDate("2000-12-07")
                .withEmail(phone + "@cat.com")
                .withPhone(phone)
                .withSex("м")
                .execute();

        Response response = walletAPI.post_publishNewCard(card, user);

        log.info(response.getBody().asString());
        log.info(response.statusCode());

        Assert.assertEquals(200, response.statusCode());
        Assert.assertNotNull(response.getBody().asString());
    }

    @Test
    @DisplayName("Обновить данные пользователя API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void updateUserData_v2_api_Test() {
        User user = new UserBuilder()
                .withFirstName("Алоисий")
                .withLastName("Баранов")
                .withCard("470000022864")
                .withPatronimic("Макарыч")
                .withBirthDate("2000-12-07")
                .withEmail("79620480011" + "@cat.com")
                .withPhone("79620480011")
                .withSex("ж")
                .execute();

        Response userByPhoneAndBirthdate = walletAPI.findUserByPhoneAndBirthdate(user);

        Response response = walletAPI.post_updateUser(user);

        log.info(response.statusCode());
        log.info(response.getBody().asString());

        Assert.assertEquals(200, response.statusCode());
        Assert.assertNotNull(response.getBody().asString());
        Assert.assertNotEquals(userByPhoneAndBirthdate, response.getBody().asString());

    }

}
