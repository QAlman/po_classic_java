package web.ops.api;

import com.google.gson.Gson;
import common.cnst.Headers;
import common.dto.User;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import static web.ops.util.datautils.ConfigPropertiesUtil.getProperty;

public class WalletAPI {

    String host = getProperty("site.host");
    private static final Logger log = Logger.getLogger(WalletAPI.class.getName());
    String rest = "/walletapi/v1/card";

    public Response get_UserCard(User user) {
        String url = host + rest;

        return RestAssured.given()
                .auth().preemptive().basic(getProperty("wallet.username"), getProperty("wallet.password"))
                .param("msisdn", user.getPhone())
                .param("birthdate", user.getBirthdate())
                //.header(Headers.BASIC_AUTH.getName(),"Basic dGVzdGxvZ2luZXh0OnRlc3RwYXNzd29yZGV4dA==")
                .get(url)
                .andReturn();
    }


    public Response channelUpdate(String cardNumber) {
        String url = host + rest + "/{cardNumber}/communication";

        JSONObject body = new JSONObject()
                .put("allow", new JSONArray().put("email").put("call").put("push"))
                .put("deny", new JSONArray().put("sms"));

        return RestAssured.given()
                .auth().preemptive().basic(getProperty("wallet.username"), getProperty("wallet.password"))
                .header(Headers.CONTENT_TYPE_JSON)
                .body(body.toString())
                .pathParam("cardNumber", cardNumber)
                .put(url)
                .andReturn();

    }

    public Response findUserByPhoneAndBirthdate(User user) {
        String url = host + "/walletapi/v2/card";

        return RestAssured.given()
                .auth().preemptive().basic("test", "testpasswordv2")
                .param("msisdn", user.getPhone())
                .param("birthDate", user.getBirthdate())
                .header(Headers.CONTENT_TYPE_JSON)
                .get(url)
                .andReturn();
    }

    public Response getUserByCard_v2_api(String cardNumber) {
        String url = host + "/walletapi/v2/card/{cardNumber}";

        return RestAssured.given()
                .header(Headers.CONTENT_TYPE_JSON)
                .pathParam("cardNumber", cardNumber)
                .auth().preemptive().basic("test", "testpasswordv2")
                .get(url)
                .andReturn();
    }

    public Response post_updateUser(User user) {
        String url = host + "/walletapi/v2/card/{cardNumber}";

        return RestAssured.given()
                .pathParam("cardNumber", user.getCardNumber())
                .header(Headers.CONTENT_TYPE_JSON)
                .auth().preemptive().basic("test", "testpasswordv2")
                .body(generateJsonFromUserData(user))
                .post(url)
                .andReturn();
    }


    public Response post_publishNewCard(String card, User user) {
        String url = host + "/walletapi/v2/card/anonymous/{cardNumber}";

        return RestAssured.given()
                .header(Headers.CONTENT_TYPE_JSON)
                .auth().preemptive().basic("test", "testpasswordv2")
                .pathParam("cardNumber", card)
                .body(generateJsonFromUserData(user))
                .post(url)
                .andReturn();
    }


    public String generateJsonFromUserData(User user) {
        Gson gson = new Gson();
        System.out.println(gson.toJson(user));
        return gson.toJson(user);
    }
}
