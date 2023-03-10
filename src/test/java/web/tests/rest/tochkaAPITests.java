package web.tests.rest;

import common.dto.User;
import io.qameta.allure.Owner;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import web.ops.api.tochkaAPI;
import web.ops.api.UserAPI;


public class tochkaAPITests extends BaseRestClass {
    private final String storeId = "0071";
    private final String skuId = "231213";
    private final String skuIdtochka = "001606";
    private final boolean isPromo = true;
    private final boolean isNotPromo = false;
    private final boolean isDeleted = true;
    private final boolean isNotDeleted = false;
    private int regularPrice;
    private int discountPrice;
    private int regularPriceSecondSku;
    private int discountPriceSecondSku;
    private int regularPriceBeforeChange;
    private int discountPriceBeforeChange;
    private Cookies cookies;
    private final UserAPI userAPI = new UserAPI();
    private final tochkaAPI tochkaAPI = new tochkaAPI();

    @Before
    public void setUp(){
        this.regularPrice = (int) (Math.random() * 20000);
        this.discountPrice = (int) (Math.random() * 2000);
        this.regularPriceSecondSku = (int) (Math.random() * 20000);
        this.discountPriceSecondSku = (int) (Math.random() * 2000);

        User usertochka = userAPI.registerUserWithDeliveryInfo();

        log.info("Random Regular price first SKU " + regularPrice);
        log.info("Random Discount price first SKU " + discountPrice);
        log.info("Random Regular price Second SKU " + regularPriceSecondSku);
        log.info("Random Discount price Second SKU " + discountPriceSecondSku);


        this.cookies = userAPI.loginAndGetCookies(usertochka);
        Response skuBeforeChange = tochkaAPI.getSKUWithLogin(storeId, skuIdtochka, cookies);
        JSONObject responseJSON = new JSONObject(skuBeforeChange.getBody().asString());
        this.regularPriceBeforeChange = (int) (Double.parseDouble(responseJSON.get("regularPrice").toString()));
        this.discountPriceBeforeChange = (int) (Double.parseDouble(responseJSON.get("discountPrice").toString()));
        log.info("Regular Price Before Change: " + regularPriceBeforeChange);
        log.info("Discount Price Before Change: " + discountPriceBeforeChange);
    }

    @Test
    @DisplayName("?????????????????? ?????? ?????????? PUT")
    @Owner(value = "????????????")
    public void put_tochkaSetPromoPrice_Test() {
        tochkaAPI.put_tochkaSetPrice(storeId, skuIdtochka, regularPrice, discountPrice, isNotPromo, isNotDeleted);

        Response skuAfterChange = tochkaAPI.getSKUWithLogin(storeId, skuIdtochka, cookies);
        JSONObject response = new JSONObject(skuAfterChange.getBody().asString());

        int regularPriceAfterChange = (int) (Double.parseDouble(response.get("regularPrice").toString()));
        int discountPriceAfterChange = (int) (Double.parseDouble(response.get("discountPrice").toString()));
        log.info("Regular Price After Change: " + regularPriceAfterChange);
        log.info("Discount Price After Change: " + discountPriceAfterChange);
//==========================================================================================================
        //Asserts
        Assert.assertNotEquals("Regular prices should be different. Actual: "
                + regularPriceAfterChange, regularPriceBeforeChange, regularPriceAfterChange);
        Assert.assertEquals("Discount prices should be equals generated regular price. value: "
                + regularPrice, regularPrice, discountPriceAfterChange);
        Assert.assertEquals("Regular price should be equal to generated regular price. value: "
                + regularPrice, regularPrice, regularPriceAfterChange);
    }


    @Test
    @DisplayName("?????????????????? ?????????? ?????? ?????????? PUT")
    @Owner(value = "????????????")
    public void put_tochkaSetPrice_Test() {

        tochkaAPI.put_tochkaSetPrice(storeId, skuIdtochka, regularPrice, discountPrice, isPromo, isNotDeleted);

        Response skuAfterChange = tochkaAPI.getSKUWithLogin(storeId, skuIdtochka, cookies);
        JSONObject response = new JSONObject(skuAfterChange.getBody().asString());

        int regularPriceAfterChange = (int) (Double.parseDouble(response.get("regularPrice").toString()));
        int discountPriceAfterChange = (int) (Double.parseDouble(response.get("discountPrice").toString()));
        log.info("Regular Price After Change: " + regularPriceAfterChange);
        log.info("Discount Price After Change: " + discountPriceAfterChange);
//==========================================================================================================
        //Asserts
        Assert.assertNotEquals("Regular prices should be different. Actual: "
                + regularPriceAfterChange, regularPriceBeforeChange, regularPriceAfterChange);
        Assert.assertNotEquals("discount prices should be different. Actual: "
                + discountPriceAfterChange, discountPriceBeforeChange, discountPriceAfterChange);
        Assert.assertEquals("Discount prices should be equals generated discount price. value: "
                + discountPrice, discountPrice, discountPriceAfterChange);
        Assert.assertEquals("Regular price should be equal to generated regular price. value: "
                + regularPrice, regularPrice, regularPriceAfterChange);

    }


    @Test
    @DisplayName("??????????????????  ?????? ??????????, ?????????? ???????????????? ?????? ?????????? PUT")
    @Owner(value = "????????????")
    public void put_tochkaSetAndDeletePrice_Test() {
//==========================================================================================================
        //Do first requests
        tochkaAPI.put_tochkaSetPrice(storeId, skuIdtochka, regularPrice, discountPrice, isPromo, isNotDeleted);
//==========================================================================================================
        //Do second requests
        tochkaAPI.put_tochkaSetPrice(storeId, skuIdtochka, regularPrice, discountPrice, isPromo, isDeleted);
//==========================================================================================================
        //Get values after requests
        Response skuAfterChange = tochkaAPI.getSKUWithLogin(storeId, skuIdtochka, cookies);
        JSONObject secondResponse = new JSONObject(skuAfterChange.getBody().asString());

        int regularPriceAfterChange = (int) (Double.parseDouble(secondResponse.get("regularPrice").toString()));
        int discountPriceAfterChange = (int) (Double.parseDouble(secondResponse.get("discountPrice").toString()));
        log.info("Regular Price After Change: " + regularPriceAfterChange);
        log.info("Discount Price After Change: " + discountPriceAfterChange);
//==========================================================================================================
        //Asserts
        Assert.assertEquals("Discount prices should be equal. Actual: " + discountPriceAfterChange
                + " and " + discountPriceBeforeChange, discountPriceBeforeChange, discountPriceAfterChange);
        Assert.assertEquals("Regular prices should be equal. Actual: " + regularPriceAfterChange
                + " and " + regularPriceBeforeChange, regularPriceBeforeChange, regularPriceAfterChange);
        Assert.assertNotEquals("Regular price should be different to generated value: "
                + regularPrice, regularPrice, regularPriceAfterChange);
        Assert.assertNotEquals("Discount price should be different to generated value: "
                + discountPrice, discountPrice, discountPriceAfterChange);
    }

    @Test
    @DisplayName("?????????????????? ?????? ??????????, ?????????? ???????????????? ???????????? ?????? ??????????, ???????? ???? ?????? ??????????????????(PUT)")
    @Owner(value = "????????????")
    public void put_tochkaSetAndDeleteKitPrice_Test() {
//==========================================================================================================
        Response secondSkuBeforeChange = tochkaAPI.getSKUWithLogin(storeId, skuId, cookies);
        JSONObject responseJSON = new JSONObject(secondSkuBeforeChange.getBody().asString());
        int regularPriceSecondSkuBeforeChange = (int) (Double.parseDouble(responseJSON.get("regularPrice").toString()));
        int discountPriceSecondSkuBeforeChange = (int) (Double.parseDouble(responseJSON.get("discountPrice").toString()));
        log.info("Regular Price second SKU Before Change: " + regularPriceSecondSkuBeforeChange);
        log.info("Discount Price second SKU Before Change: " + discountPriceSecondSkuBeforeChange);

//==========================================================================================================
        //Do first requests
        tochkaAPI.put_tochkaSetPrice(storeId, skuIdtochka, regularPrice, discountPrice, isNotPromo, isNotDeleted);
//==========================================================================================================
        //Do second requests
        tochkaAPI.put_tochkaSetPrice(storeId, skuIdtochka, regularPrice, discountPrice, isNotPromo, isDeleted,
                skuId, regularPriceSecondSku, discountPriceSecondSku, isPromo, isNotDeleted);
//==========================================================================================================
        //Get values after requests
        Response skuAfterChange = tochkaAPI.getSKUWithLogin(storeId, skuIdtochka, cookies);
        JSONObject resultResponse = new JSONObject(skuAfterChange.getBody().asString());

        int regularPriceAfterChange = (int) (Double.parseDouble(resultResponse.get("regularPrice").toString()));
        int discountPriceAfterChange = (int) (Double.parseDouble(resultResponse.get("discountPrice").toString()));
        log.info("Regular Price After Change: " + regularPriceAfterChange);
        log.info("Discount Price After Change: " + discountPriceAfterChange);

        Response skuSecondAfterChange = tochkaAPI.getSKUWithLogin(storeId, skuId, cookies);
        JSONObject secondResultResponse = new JSONObject(skuSecondAfterChange.getBody().asString());

        int regularPriceSecondSkuAfterChange = (int) (Double.parseDouble(secondResultResponse.get("regularPrice").toString()));
        int discountPriceSecondSkuAfterChange = (int) (Double.parseDouble(secondResultResponse.get("discountPrice").toString()));
        log.info("Regular Price second SKU After Change: " + regularPriceSecondSkuAfterChange);
        log.info("Discount Price second SKU After Change: " + discountPriceSecondSkuAfterChange);
//==========================================================================================================
        //Asserts first SKU
        Assert.assertEquals("Discount prices should be equal. value: " + discountPriceAfterChange
                + " and " + discountPriceBeforeChange, discountPriceBeforeChange, discountPriceAfterChange);
        Assert.assertEquals("Regular prices should be equal. value:  " + regularPriceAfterChange
                + " and " + regularPriceBeforeChange, regularPriceBeforeChange, regularPriceAfterChange);
        Assert.assertNotEquals("Regular price should be different to generated value: "
                + regularPrice, regularPrice, regularPriceAfterChange);
        Assert.assertNotEquals("Discount price should be different to generated value: "
                + discountPrice, discountPrice, discountPriceAfterChange);
//==========================================================================================================
        //Asserts second SKU
        Assert.assertNotEquals("Regular prices second SKU should be different. value: "
                + regularPriceSecondSkuAfterChange, regularPriceSecondSkuBeforeChange, regularPriceSecondSkuAfterChange);
        Assert.assertNotEquals("discount prices second SKU should be different. value: "
                + discountPriceSecondSkuAfterChange, discountPriceSecondSkuBeforeChange, discountPriceSecondSkuAfterChange);
        Assert.assertEquals("Discount prices second SKU should be equals generated discount price. value: "
                + discountPriceSecondSku, discountPriceSecondSku, discountPriceSecondSkuAfterChange);
        Assert.assertEquals("Regular price second SKU should be equal to generated regular price. value: "
                + regularPriceSecondSku, regularPriceSecondSku, regularPriceSecondSkuAfterChange);

    }

    @Test
    @DisplayName("?????????????????? ?????? ?????????? POST")
    @Owner(value = "????????????")
    public void post_tochkaSetPromoPrice_Test() {
        tochkaAPI.post_tochkaSetPrice(storeId, skuIdtochka, regularPrice, discountPrice, isNotPromo, isNotDeleted);

        Response skuAfterChange = tochkaAPI.getSKUWithLogin(storeId, skuIdtochka, cookies);
        JSONObject response = new JSONObject(skuAfterChange.getBody().asString());

        int regularPriceAfterChange = (int) (Double.parseDouble(response.get("regularPrice").toString()));
        int discountPriceAfterChange = (int) (Double.parseDouble(response.get("discountPrice").toString()));
        log.info("Regular Price After Change: " + regularPriceAfterChange);
        log.info("Discount Price After Change: " + discountPriceAfterChange);
//==========================================================================================================
        //Asserts
        Assert.assertNotEquals("Regular prices should be different. Actual: "
                + regularPriceAfterChange, regularPriceBeforeChange, regularPriceAfterChange);
        Assert.assertEquals("Discount prices should be equals generated regular price. value: "
                + regularPrice, regularPrice, discountPriceAfterChange);
        Assert.assertEquals("Regular price should be equal to generated regular price. value: "
                + regularPrice, regularPrice, regularPriceAfterChange);
    }


    @Test
    @DisplayName("?????????????????? ?????????? ?????? ?????????? POST")
    @Owner(value = "????????????")
    public void post_tochkaSetPrice_Test() {

        tochkaAPI.post_tochkaSetPrice(storeId, skuIdtochka, regularPrice, discountPrice, isPromo, isNotDeleted);

        Response skuAfterChange = tochkaAPI.getSKUWithLogin(storeId, skuIdtochka, cookies);
        JSONObject response = new JSONObject(skuAfterChange.getBody().asString());

        int regularPriceAfterChange = (int) (Double.parseDouble(response.get("regularPrice").toString()));
        int discountPriceAfterChange = (int) (Double.parseDouble(response.get("discountPrice").toString()));
        log.info("Regular Price After Change: " + regularPriceAfterChange);
        log.info("Discount Price After Change: " + discountPriceAfterChange);
//==========================================================================================================
        //Asserts
        Assert.assertNotEquals("Regular prices should be different. Actual: "
                + regularPriceAfterChange, regularPriceBeforeChange, regularPriceAfterChange);
        Assert.assertNotEquals("discount prices should be different. Actual: "
                + discountPriceAfterChange, discountPriceBeforeChange, discountPriceAfterChange);
        Assert.assertEquals("Discount prices should be equals generated discount price. value: "
                + discountPrice, discountPrice, discountPriceAfterChange);
        Assert.assertEquals("Regular price should be equal to generated regular price. value: "
                + regularPrice, regularPrice, regularPriceAfterChange);

    }


    @Test
    @DisplayName("??????????????????  ?????? ??????????, ?????????? ???????????????? ?????? ?????????? POST")
    @Owner(value = "????????????")
    public void post_tochkaSetAndDeletePrice_Test() {
//==========================================================================================================
        //Do first requests
        tochkaAPI.post_tochkaSetPrice(storeId, skuIdtochka, regularPrice, discountPrice, isPromo, isNotDeleted);
//==========================================================================================================
        //Do second requests
        tochkaAPI.post_tochkaSetPrice(storeId, skuIdtochka, regularPrice, discountPrice, isPromo, isDeleted);
//==========================================================================================================
        //Get values after requests
        Response skuAfterChange = tochkaAPI.getSKUWithLogin(storeId, skuIdtochka, cookies);
        JSONObject secondResponse = new JSONObject(skuAfterChange.getBody().asString());

        int regularPriceAfterChange = (int) (Double.parseDouble(secondResponse.get("regularPrice").toString()));
        int discountPriceAfterChange = (int) (Double.parseDouble(secondResponse.get("discountPrice").toString()));
        log.info("Regular Price After Change: " + regularPriceAfterChange);
        log.info("Discount Price After Change: " + discountPriceAfterChange);
//==========================================================================================================
        //Asserts
        Assert.assertEquals("Discount prices should be equal. Actual: " + discountPriceAfterChange
                + " and " + discountPriceBeforeChange, discountPriceBeforeChange, discountPriceAfterChange);
        Assert.assertEquals("Regular prices should be equal. Actual: " + regularPriceAfterChange
                + " and " + regularPriceBeforeChange, regularPriceBeforeChange, regularPriceAfterChange);
        Assert.assertNotEquals("Regular price should be different to generated value: "
                + regularPrice, regularPrice, regularPriceAfterChange);
        Assert.assertNotEquals("Discount price should be different to generated value: "
                + discountPrice, discountPrice, discountPriceAfterChange);
    }

    @Test
    @DisplayName("?????????????????? ?????? ??????????, ?????????? ???????????????? ???????????? ?????? ??????????, ???????? ???? ?????? ?????????????????? POST")
    @Owner(value = "????????????")
    public void post_tochkaSetAndDeleteKitPrice_Test() {
//==========================================================================================================
        //Get info by second SKU
        Response secondSkuBeforeChange = tochkaAPI.getSKUWithLogin(storeId, skuId, cookies);
        JSONObject responseJSON = new JSONObject(secondSkuBeforeChange.getBody().asString());
        int regularPriceSecondSkuBeforeChange = (int) (Double.parseDouble(responseJSON.get("regularPrice").toString()));
        int discountPriceSecondSkuBeforeChange = (int) (Double.parseDouble(responseJSON.get("discountPrice").toString()));
        log.info("Regular Price second SKU Before Change: " + regularPriceSecondSkuBeforeChange);
        log.info("Discount Price second SKU Before Change: " + discountPriceSecondSkuBeforeChange);

//==========================================================================================================
        //Do first requests
        tochkaAPI.post_tochkaSetPrice(storeId, skuIdtochka, regularPrice, discountPrice, isNotPromo, isNotDeleted);
//==========================================================================================================
        //Do second requests
        tochkaAPI.post_tochkaSetPrice(storeId, skuIdtochka, regularPrice, discountPrice, isNotPromo, isDeleted,
                skuId, regularPriceSecondSku, discountPriceSecondSku, isPromo, isNotDeleted);
//==========================================================================================================
        //Get values after requests
        Response skuAfterChange = tochkaAPI.getSKUWithLogin(storeId, skuIdtochka, cookies);
        JSONObject resultResponse = new JSONObject(skuAfterChange.getBody().asString());

        int regularPriceAfterChange = (int) (Double.parseDouble(resultResponse.get("regularPrice").toString()));
        int discountPriceAfterChange = (int) (Double.parseDouble(resultResponse.get("discountPrice").toString()));
        log.info("Regular Price After Change: " + regularPriceAfterChange);
        log.info("Discount Price After Change: " + discountPriceAfterChange);

        Response skuSecondAfterChange = tochkaAPI.getSKUWithLogin(storeId, skuId, cookies);
        JSONObject secondResultResponse = new JSONObject(skuSecondAfterChange.getBody().asString());

        int regularPriceSecondSkuAfterChange = (int) (Double.parseDouble(secondResultResponse.get("regularPrice").toString()));
        int discountPriceSecondSkuAfterChange = (int) (Double.parseDouble(secondResultResponse.get("discountPrice").toString()));
        log.info("Regular Price second SKU After Change: " + regularPriceSecondSkuAfterChange);
        log.info("Discount Price second SKU After Change: " + discountPriceSecondSkuAfterChange);
//==========================================================================================================
        //Asserts first SKU
        Assert.assertEquals("Discount prices should be equal. value: " + discountPriceAfterChange
                + " and " + discountPriceBeforeChange, discountPriceBeforeChange, discountPriceAfterChange);
        Assert.assertEquals("Regular prices should be equal. value:  " + regularPriceAfterChange
                + " and " + regularPriceBeforeChange, regularPriceBeforeChange, regularPriceAfterChange);
        Assert.assertNotEquals("Regular price should be different to generated value: "
                + regularPrice, regularPrice, regularPriceAfterChange);
        Assert.assertNotEquals("Discount price should be different to generated value: "
                + discountPrice, discountPrice, discountPriceAfterChange);
//==========================================================================================================
        //Asserts second SKU
        Assert.assertNotEquals("Regular prices second SKU should be different. value: "
                + regularPriceSecondSkuAfterChange, regularPriceSecondSkuBeforeChange, regularPriceSecondSkuAfterChange);
        Assert.assertNotEquals("discount prices second SKU should be different. value: "
                + discountPriceSecondSkuAfterChange, discountPriceSecondSkuBeforeChange, discountPriceSecondSkuAfterChange);
        Assert.assertEquals("Discount prices second SKU should be equals generated discount price. value: "
                + discountPriceSecondSku, discountPriceSecondSku, discountPriceSecondSkuAfterChange);
        Assert.assertEquals("Regular price second SKU should be equal to generated regular price. value: "
                + regularPriceSecondSku, regularPriceSecondSku, regularPriceSecondSkuAfterChange);

    }
}