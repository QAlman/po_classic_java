package web.ops.api;

import com.google.gson.JsonParser;
import common.cnst.Headers;
import common.dto.User;
import common.service.UserService;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import web.ops.helpers.CommonHelpers;

import static org.apache.log4j.Logger.getLogger;
import static web.ops.util.datautils.ConfigPropertiesUtil.getProperty;


public class UserAPI {
    private static final Logger log = getLogger(UserAPI.class.getName());
    private static final String host = getProperty("site.host");
    private static final String rest = "/api/v1/registration/";
    private static final String verificationCode = "000000";

    UserService userService = new UserService();

    public User registerUser(User user) {
        log.info("register user");

        log.info("request validation code");
        post_requestValidationCode(user);

        log.info("verifying code");
        post_verifyCode(user);

        log.info("updating user data");
        post_updateUser(user);

        log.info("setting user password");
        Response response = post_setUserPassword(user);

        log.info("Select default city");
        post_setDefaultCity(response.getDetailedCookies());
        log.info("Phone  standard user " + user.getPhone());

        return user;
    }

    public User registerUserWithOutCity() {
        log.info("register user");

        User user = userService.generateDefaultUser();

        log.info("request validation code");
        post_requestValidationCode(user);

        log.info("verifying code");
        post_verifyCode(user);

        log.info("updating user data");
        post_updateUser(user);

        log.info("setting user password");
        post_setUserPassword(user);

        return user;
    }

    public User registerUserWithDeliveryInfo() {

        log.info("register user");
        User user = this.userService.generateDefaultUser();

        log.info("request validation code");
        post_requestValidationCode(user);

        log.info("verifying code");
        post_verifyCode(user);

        log.info("updating user data");
        post_updateUser(user);

        log.info("setting user password");
        Response response = post_setUserPassword(user);

        log.info("Select default city");
        post_setDefaultCity(response.getDetailedCookies());

        log.info("Set delivery info");
        Cookies cookies = loginAndGetCookies(user);
        post_setDeliveryInfo(cookies);

        log.info("Phone user with delivery info " + user.getPhone());
        return user;
    }

    public User registerUserWithDefaultCity() {

        log.info("register user");
        User user = this.userService.generateDefaultUser();

        log.info("request validation code");
        post_requestValidationCode(user);

        log.info("verifying code");
        post_verifyCode(user);

        log.info("updating user data");
        post_updateUser(user);

        log.info("setting user password");
        Response response = post_setUserPassword(user);

        log.info("Select default city");
        post_setDefaultCity(response.getDetailedCookies());

        return user;
    }

    public User registerUserByTablet() {

        log.info("register user by tablet");
        User user = this.userService.generateDefaultUser();

        log.info("request validation code");
        post_requestValidationCodeByTablet(user);

        log.info("verifying code by tablet");
        post_verifyCodeByTablet(user);

        log.info("updating user data by tablet");
        post_updateUserByTablet(user);

        log.info("updating user data by tablet");
//        post_requestOTPByTablet(user);

        return user;
    }

    @Step(value = "Верификация нового пароля")
    public Response post_setUserPassword(User user) {
        JSONObject body = new JSONObject();

        body
                .put("phoneNumber", user.getPhone())
                .put("password", user.getPassWord())
                .put("verificationCode", verificationCode);

        String url = host + rest + "setUserPassword";

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .body(body.toString())
                .when()
                .post(url)
                .andReturn();

    }

    @Step(value = "Получить сообщения пользователя")
    public Response get_getComplaints(Cookies cookies) {

        String url = host + "/api/v1/complaints";

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .cookies(cookies)
                .when()
                .get(url)
                .andReturn();
    }

    @Step(value = "Удалить сообщения пользователя")
    public Response delete_deleteComplaints(Cookies cookies, String complaintGuid) {

        String url = host + "/api-data/complaint?complaintId=" + complaintGuid;
        JSONObject body = new JSONObject()
                .put("complaintGuid", complaintGuid);

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .cookies(cookies)
                .body(body.toString())
                .when()
                .delete(url)
                .andReturn();
    }

    @Step(value = "Получить баланс фишек")
    public Response get_getStampsBalance(Cookies cookies) {

        String url = host + "/api/v1/me/stamps";

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .cookies(cookies)
                .when()
                .get(url)
                .andReturn();
    }

    @Step(value = "Перевод фишек")
    public Response post_transferStamps(Cookies cookies, String phoneNumber, int stamps) {
        JSONObject body = new JSONObject()
                .put("code", "000000")
                .put("phone", phoneNumber.substring(1))
                .put("stamps", stamps);

        String url = host + "/api/v1/me/stamps/give";

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .cookies(cookies)
                .body(body.toString())
                .when()
                .post(url)
                .andReturn();
    }


    @Step(value = "Верификация кода подтверждения")
    public Response post_verifyCode(User user) {
        String rest = "/api/v1/authentication/";
        String url = host + rest + "verifyCode";
        JSONObject body = new JSONObject()
                .put("phone", user.getPhone())
                .put("code", verificationCode);

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .body(body.toString())
                .when()
                .post(url)
                .andReturn();
    }

    @Step(value = "Верификация кода подтверждения с планшета")
    public Response post_verifyCodeByTablet(User user) {
        String url = host + "/api/v1/authentication/verifyCode";
        JSONObject body = new JSONObject()
                .put("code", verificationCode)
                .put("phone", user.getPhone().substring(1))
                .put("source", "Tablet");

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .body(body.toString())
                .when()
                .post(url)
                .andReturn();
    }

    @Step(value = "Верификация кода подтверждения с планшета")
    public Response post_getUserInfo(User user) {
        String url = host + "/api/v1/profile/getUserInfo";
        JSONObject body = new JSONObject()
                .put("phoneNumber", user.getPhone().substring(1))
                .put("verificationCode", verificationCode);

        log.info(body.toString());
        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .body(body.toString())
                .when()
                .post(url)
                .andReturn();
    }

    @Step(value = "Обновление данных пользователя")
    public Response post_updateUser(User user) {

        String url = host + rest + "updateUser";

        log.info(url);

        JSONObject body = new JSONObject()
                .put("dateOfBirth", user.getBirthdate())
                .put("emailAddress", user.getEmail())
                .put("firstName", user.getFirstName())
                .put("gender", user.getSex())
                .put("lastname", user.getSurname())
                .put("patronymic", user.getPatronymic())
                .put("phoneNumber", user.getPhone())
                .put("verificationCode", verificationCode);

        log.trace(body);
        log.trace("updating user status: ");

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .body(body.toString())
                .when()
                .post(url)
                .andReturn();

    }

    @Step(value = "Обновление данных пользователя с планшета")
    public Response post_updateUserByTablet(User user) {

        String url = host + rest + "updateUser";

        JSONObject body = new JSONObject()
                .put("cardNumber", "")
                .put("dateOfBirth", user.getBirthdate())
                .put("emailAddress", "")
                .put("firstName", user.getFirstName())
                .put("gender", user.getSex())
                .put("lastname", user.getSurname())
                .put("patronymic", user.getPatronymic())
                .put("phoneNumber", user.getPhone().substring(1))
                .put("verificationCode", verificationCode)
                .put("infokioskSettings", new JSONObject()
                        .put("employeeName", "")
                        .put("registrationSource", "Tablet")
                        .put("storeId", ""));

        log.info(body.toString());
        log.trace("updating user status: ");

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .body(body.toString())
                .when()
                .post(url)
                .andReturn();

    }

    @Step(value = "Обновление данных пользователя с планшета")
    public Response post_requestOTPByTablet(User user) {

        String url = host + rest + "ppk/requestOTP";

        log.info(url);

        JSONObject body = new JSONObject()
                .put("phoneNumber", user.getPhone().substring(1))
                .put("cardNumber", "");

        log.trace(body);

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .body(body.toString())
                .when()
                .post(url)
                .andReturn();

    }


    @Step(value = "Запрос кода подтверждения")
    public Response post_requestValidationCode(User user) {
        String url = host + rest + "requestValidationCode";
        JSONObject body = new JSONObject()
                .put("phone", user.getPhone());

        log.trace("Receiving Code");

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .body(body.toString())
                .when()
                .post(url)
                .andReturn();

    }

    @Step(value = "Запрос кода подтверждения с планшета")
    public Response post_requestValidationCodeByTablet(User user) {
        String url = host + "/api/v1/authentication/requestValidationCode";
        JSONObject body = new JSONObject()
                .put("phone", user.getPhone().substring(1))
                .put("registrationSource", "Tablet");

        log.trace("Receiving Code");
        log.info(body.toString());

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .body(body.toString())
                .when()
                .post(url)
                .andReturn();

    }

    @Step(value = "Проверка номера телефона, свободел ли он")
    public Response post_requestUserStatus(User user) {
        String url = host + rest + "requestUserStatus";
        JSONObject body = new JSONObject()
                .put("phoneNumber", user.getPhone().substring(1));


        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .body(body.toString())
                .when()
                .post(url)
                .andReturn();

    }

    public Cookies loginAndGetCookies(User user) {
        Response login = this.post_login(user);
        log.trace(login.getBody().asString());
        Assert.assertNotEquals("Check Possibly included 2FA & AutoTestEnabled = False", login.getBody().print(), "{\"message\":\"Неправильный код проверки. Повторите попытку ещё раз.\",\"errorCode\":\"SmsCodeVerificationFailed\"}");
        Assert.assertEquals(200, login.statusCode());
        return login.getDetailedCookies();
    }

    @Step(value = "Выбрать 5 категорий")
    public Response post_setCategories(Cookies cookies) {
        String url = host + "/api/v1/me/loyaltyprogram/categories";

        Response response = RestAssured.get(host + "/api/v1/me/loyaltyprogram/avaliable").andReturn();

        String first = JsonParser.parseString(response.getBody().asString()).getAsJsonArray().get(5).getAsJsonObject().get("id").getAsString();
        String second = JsonParser.parseString(response.getBody().asString()).getAsJsonArray().get(4).getAsJsonObject().get("id").getAsString();
        String third = JsonParser.parseString(response.getBody().asString()).getAsJsonArray().get(3).getAsJsonObject().get("id").getAsString();
        String fourth = JsonParser.parseString(response.getBody().asString()).getAsJsonArray().get(2).getAsJsonObject().get("id").getAsString();
        String fifth = JsonParser.parseString(response.getBody().asString()).getAsJsonArray().get(1).getAsJsonObject().get("id").getAsString();

        JSONObject body = new JSONObject().put("selectedCategoryIds", new JSONArray().put(first).put(second).put(third).put(fourth).put(fifth));

        log.info(body);

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .cookies(cookies)
                .body(body.toString())
                .when()
                .post(url)
                .andReturn();
    }

    @Step(value = "Выбор дефолтного города")
    private Response post_setDefaultCity(Cookies cookies) {
        String url = host + "/api/v1/me/store";

        JSONObject body = new JSONObject()
                .put("storeId", "0071")
                .put("isChangedFromStorePicker", false);

        System.out.println(body.toString());

        return RestAssured.given()
                .header(Headers.CONTENT_TYPE_JSON)
                .header(new Header("accept", "application/json"))
                .header(new Header("accept-encoding", "gzip, deflate, br"))
                .cookies(cookies)
                .body(body.toString())
                .put(url)
                .andReturn();

    }

    @Step(value = "Обновить информацую по доставке")
    public Response post_setDeliveryInfo(Cookies cookies) {
        String url = host + "/api/v1/me/deliveryinfo";
        JSONObject body = new JSONObject()
                .put("address",
                        new JSONObject()
                                .put("addressName", "630124, Новосибирская обл, г Новосибирск, Октябрьский р-н, Гусинобродское шоссе, д 1")
                                .put("block", "")
                                .put("blockType", "")
                                .put("city", "Новосибирск")
                                .put("country", "Россия")
                                .put("county", "")
                                .put("district", "Октябрьский")
                                .put("flat", "")
                                .put("flatType", "")
                                .put("house", "1")
                                .put("municipalFormation", "область")
                                .put("region", "")
                                .put("state", "Новосибирская")
                                .put("streetAddress", "Гусинобродское шоссе")
                                .put("zipCode", "630124")
                )
                .put("deliveryOptions", "Delivery")
//                .put("StoreId", "0071")
                .put("deliveryStoreId", "0071")
                .put("geoLocation",
                        new JSONObject()
                                .put("lat", "55.032644")
                                .put("long", "82.995494")
                );

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .cookies(cookies)
                .body(body.toString())
                .when()
                .post(url)
                .andReturn();

    }

    @Step(value = "Войти с помощью мобильного API")
    public Response post_signInAsMobileUser(User user) {
        String url = host + "/api/v1/signin?q=2";
        String body = "grant_type=password&password=" + user.getPassWord() + "&username=" + user.getPhone();

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_URLENCODED)
                .body(body)
                .when()
                .post(url)
                .andReturn();
    }

    @Step(value = "Изменить адресс электронной почты")
    public Response put_changeEmail(User user, String access_token) {
        String url = host + "/api/v1/me/email";

        JSONObject body = new JSONObject()
                .put("newEmail", user.getEmail())
                .put("verificationCode", verificationCode)
                .put("receiveEmails", true);

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .header(Headers.AUTHORIZATION_BEARER.getName(), "Bearer " + access_token)
                .body(body.toString())
                .when()
                .put(url)
                .andReturn();
    }

    @Step(value = "Запрос кода валидации при смене забытого пароля")
    public Response post_requestForgottenPasswordCode(User user) {
        String url = host + "/api/v1/authentication/requestForgotPasswordCode";
        JSONObject body = new JSONObject().put("login", user.getPhone());

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .body(body.toString())
                .when()
                .post(url)
                .andReturn();
    }

    @Step(value = "Запрос статуса карты")
    public Response post_requestCardStatus(User user) {
        String url = host + "/api/v1/authentication/GetCardStatus";
        JSONObject body = new JSONObject().put("phone", user.getPhone()).put("card", user.getCardNumber());

        return RestAssured.given()
                .header(Headers.CONTENT_TYPE_JSON)
                .body(body.toString())
                .when()
                .post(url)
                .andReturn();
    }


    @Step(value = "Запросить текст пользовательского соглашения")
    public Response get_requestPersonalAgreement() {
        String url = host + "/api/v1/authentication/PersonalDataAgreement";

        return RestAssured.get(url).andReturn();
    }

    @Step(value = "Валидация забытого пароля")
    public Response post_validateForgotPassword(User user, String code) {
        String url = host + "/api/v1/authentication/validateForgotPasswordCode";
        JSONObject body = new JSONObject().put("login", user.getPhone()).put("verificationCode", code);

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .body(body.toString())
                .when()
                .post(url)
                .andReturn();

    }

    @Step(value = "Сбросить пароль")
    public Response post_resetPassword(User user, String verificationCode) {
        String url = host + "/api/v1/authentication/resetPassword";
        JSONObject body = new JSONObject()
                .put("login", user.getPhone())
                .put("verificationCode", verificationCode)
                .put("newPassword", "!Q" + CommonHelpers.generateRandomLatinString(8));

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .body(body.toString())
                .when()
                .post(url)
                .andReturn();

    }

    @Step(value = "Залогиниться")
    public Response post_login(User user) {
        JSONObject jsonObject = new JSONObject()
                .put("login", user.getPhone())
                .put("password", user.getPassWord())
                .put("smsCode", "");

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .body(jsonObject.toString())
                .when()
                .post(host + "/api/v1/authentication/login")
                .andReturn();
    }

    public Response post_requestValidationCodeForStamps(Cookies cookies, String recipientNumber, int stamps) {
        String url = host + "/api/v2/me/stamps/requestValidationCode";
        JSONObject body = new JSONObject()
                .put("recipientPhone", recipientNumber.substring(1))
                .put("stamps", stamps);

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .cookies(cookies)
                .body(body.toString())
                .when()
                .post(url)
                .andReturn();

    }
}
