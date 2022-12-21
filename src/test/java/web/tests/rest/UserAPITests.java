package web.tests.rest;

import com.google.gson.JsonParser;
import common.builder.UserBuilder;
import common.cnst.Tags;
import common.dto.User;
import common.service.UserService;
import io.qameta.allure.Owner;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import web.ops.api.UserAPI;
import web.ops.helpers.CommonHelpers;
import web.ops.util.datautils.SQLUtil;

import java.sql.SQLException;
import java.util.Map;

import static web.ops.util.datautils.ConfigPropertiesUtil.getProperty;

public class UserAPITests extends BaseRestClass {
    private User user = new User();
    private final UserAPI userAPI = new UserAPI();
    private final UserService userService = new UserService();

    @Before
    public void setUp() {
        user = userService.generateDefaultUser();
    }

    @Test
    @DisplayName("Регистрация пользователя через API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void regUZ_Test() {
        userAPI.registerUser(user);
    }

    @Test
    @DisplayName("Регистрация пользователя через API c delivery info")
    @Owner(value = "Максим")
    @Tag(value = Tags.API)
    public void regUZWithDeliveryInfo_Test() {
        userAPI.registerUserWithDeliveryInfo();
    }


    @Test
    @DisplayName("Регистрация пользователя через API v2")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void registerUserRestTest() {
        user.setSex("male");
        log.info(user.getPhone());

        Response validationCodeResponse = userAPI.post_requestValidationCode(user);
        Response verificationCodeResponse = userAPI.post_verifyCode(user);
        Response updateUserResponse = userAPI.post_updateUser(user);
        Response userPassResponse = userAPI.post_setUserPassword(user);

        Assert.assertNotNull(validationCodeResponse);
        Assert.assertNotNull(verificationCodeResponse);
        Assert.assertNotNull(updateUserResponse);
        Assert.assertNotNull(userPassResponse);
    }

    @Test
    @DisplayName("Регистрация пользователя с планшета")
    @Owner(value = "Максим")
    @Tag(value = Tags.API)
    public void registerUserByTabletRestTest() throws SQLException {
        user.setSex("male");

        Response validationCodeResponse = userAPI.post_requestValidationCodeByTablet(user);
        Response verificationCodeResponse = userAPI.post_verifyCodeByTablet(user);
        Response updateUserResponse = userAPI.post_updateUserByTablet(user);

        Assert.assertEquals(200, validationCodeResponse.getStatusCode());
        Assert.assertEquals(200, verificationCodeResponse.getStatusCode());
        Assert.assertEquals(200, updateUserResponse.getStatusCode());
        Assert.assertEquals("1", getIsNewLoyaltyActiveStatus(updateUserResponse));
        Assert.assertNull(getPasswordHash(user));


    }


    @Test
    @DisplayName("Логин через мобильное APi - валидный пользователь")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void validUser_signInAsMobileUserTest() {
        User user = new UserBuilder()
                .withPhone("79" + getProperty("site.username"))
                .withPassword(getProperty("site.password")).execute();

        Response response = userAPI.post_signInAsMobileUser(user);

        log.info(response.statusCode());
        log.info(response.body().asString());

        Assert.assertEquals(200, response.statusCode());
        Assert.assertNotNull(response.getBody());
        Assert.assertTrue(ìsRequiredFieldsInResponse(testData.getVariable("fields"), response.getBody().asString()));
    }

    @Test
    @DisplayName("Логин через мобильное APi - невалидный пользователь")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void invalidUser_signInAsMobileUserTest() {
        User user = new UserBuilder()
                .withPhone(getProperty("site.username"))
                .withPassword(getProperty("site.password")).execute();

        Response response = userAPI.post_signInAsMobileUser(user);

        log.info(response.statusCode());
        log.info(response.body().asString());

        Assert.assertEquals(400, response.statusCode());
        Assert.assertNotNull(response.getBody());
        Assert.assertTrue(ìsRequiredFieldsInResponse(testData.getVariable("fields"), response.getBody().asString()));
    }

    @Test
    @DisplayName("Логин через мобильное APi - пустой пользователь")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void emptyUser_signInAsMobileUserTest() {
        Response response = userAPI.post_signInAsMobileUser(user);

        log.info(response.statusCode());
        log.info(response.body().asString());

        Assert.assertEquals(400, response.statusCode());
        Assert.assertNotNull(response.getBody());
        Assert.assertTrue(ìsRequiredFieldsInResponse(testData.getVariable("fields"), response.getBody().asString()));
    }

    @Test
    @DisplayName("Изменить электронную почту пользователя - валидный пользователь")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void validUser_changeUserEmailTest() {
        User user = new UserBuilder()
                .withPhone("79" + getProperty("site.username"))
                .withPassword(getProperty("site.password"))
                .withEmail(CommonHelpers.generateRandomLatinString(5) + "@cat.com")
                .execute();

        Response response = userAPI.put_changeEmail(user, getSessionAccessToken(userAPI.post_signInAsMobileUser(user)));

        log.info(response.statusCode());
        log.info(response.body().asString());

        Assert.assertEquals(200, response.statusCode());
        Assert.assertTrue(response.getBody().asString().contains("\"success\":true"));
    }

    @Test
    @DisplayName("Проверить статус карты - карта валидна")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void validCard_getCardStatusTest() {
        User user = new UserBuilder()
                .withPhone("79620480011")
                .withPassword(getProperty("site.password"))
                .withCard("470000022864")
                .withEmail(CommonHelpers.generateRandomLatinString(5) + "@cat.com")
                .execute();

        Response response = userAPI.post_requestCardStatus(user);

        log.info(response.statusCode());
        log.info(response.getBody());

        Assert.assertEquals(200, response.statusCode());
        Assert.assertTrue(ìsRequiredFieldsInResponse(testData.getVariable("fields"), response.getBody().asString()));
    }


    @Test
    @DisplayName("Проверить статус карты - карта невалидна")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void invalidCard_getCardStatusTest() {

        Response response = userAPI.post_requestCardStatus(user);

        log.info(response.statusCode());
        log.info(response.getBody());

        Assert.assertEquals(200, response.statusCode());
        Assert.assertTrue(ìsRequiredFieldsInResponse(testData.getVariable("fields"), response.getBody().asString()));
    }

    @Test
    @DisplayName("Звпрос кода валидации для смены пароля")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void requestForgottenPasswordCode() {
        User user = new UserBuilder()
                .withPhone("79620480011")
                .withPassword(getProperty("site.password"))
                .withCard("470000022864")
                .withEmail(CommonHelpers.generateRandomLatinString(5) + "@cat.com")
                .execute();

        Response response = userAPI.post_requestForgottenPasswordCode(user);

        log.info(response.statusCode());
        log.info(response.getBody().asString());

        Assert.assertEquals(200, response.statusCode());
    }

    @Test
    @DisplayName("Пользовательское соглашение")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void personalDataAgreementTest() {
        Response personalAgreement = userAPI.get_requestPersonalAgreement();

        log.info(personalAgreement.statusCode());
        log.info(personalAgreement.getBody().asString());

        Assert.assertEquals(200, personalAgreement.statusCode());
    }

    @Test
    @DisplayName("Валидаця кода подтверждения - Валидный код")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void validCode_validateForgottenPasswordCode() {
        User user = new UserBuilder()
                .withPhone("79620480010")
                .execute();

        Response response = userAPI.post_validateForgotPassword(user, "000000");

        log.info(response.statusCode());
        log.info(response.body().asString());

        Assert.assertEquals(200, response.statusCode());
    }

    @Test
    @DisplayName("Валидаця кода подтверждения - НЕ Валидный код")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void invalidCode_validateForgottenPasswordCode() {
        User user = new UserBuilder()
                .withPhone("79620480010")
                .execute();

        Response response = userAPI.post_validateForgotPassword(user, "111111");

        log.info(response.statusCode());
        log.info(response.body().asString());

        Assert.assertEquals(403, response.statusCode());
        Assert.assertTrue(response.getBody().asString().contains("Неправильный код проверки. Повторите попытку ещё раз."));
    }

    @Test
    @DisplayName("Сменя пароля")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void changePassword() {
        User user = new UserBuilder()
                .withPhone("79620480000")
                .execute();

        Response response = userAPI.post_resetPassword(user, "000000");

        log.info(response.statusCode());
        log.info(response.getBody());

        Assert.assertEquals(200, response.statusCode());
    }

    private String getSessionAccessToken(Response response) {
        return JsonParser.parseString(response.getBody().asString()).getAsJsonObject().get("access_token").getAsString();
    }

    private String getIsNewLoyaltyActiveStatus(Response response) throws SQLException {
        JSONObject responseJSON = new JSONObject(response.getBody().asString());
        String userId = responseJSON.get("clientId").toString();
        Map<String, String> resultSet = SQLUtil.queryWithResult("SELECT [ClientId]\n" +
                "      ,[IsNewLoyaltyActive]\n" +
                "  FROM [stagesite-crm].[dbo].[tblClients]\n" +
                "  WHERE ClientId = " + userId);

        return resultSet.get("IsNewLoyaltyActive");

    }

    private String getPasswordHash(User user) throws SQLException {
        Map<String, String> resultSet = SQLUtil.queryWithResult("SELECT[PasswordHash]\n" +
                "      ,[PhoneNumber]\n" +
                "  FROM [stagesite-episerver].[dbo].[AspNetUsers]\n" +
                "    Where  PhoneNumber=7" + user.getPhone());
        return resultSet.get("PasswordHash");

    }
}
