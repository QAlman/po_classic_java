package web.ops.api;

import common.cnst.Headers;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import web.objs.model.PriceValidityModel;
import web.objs.model.SkuPricesModel;

import java.util.ArrayList;
import java.util.List;

import static common.cnst.Headers.CONTENT_TYPE_JSON;

public class LentochkaAPI extends BaseAPIClass {


    @Step(value = "Устновка цен точки PUT")
    public Response put_lentochkaSetPrice(String storeID, String skuId, int regularPrice,
                                          int discountPrice, boolean isPromo, boolean deleted) {

        String url = host + "/api/v1/lentochka/prices";
        PriceValidityModel priceValidityModel = new PriceValidityModel(
                regularPrice,
                discountPrice,
                "2020-05-07T00:00:00",
                "2023-03-31T23:59:59",
                isPromo);
        priceValidityModel.setIsdeleted(deleted);

        List<PriceValidityModel> priceValidityModelList = new ArrayList<>();
        priceValidityModelList.add(priceValidityModel);

        SkuPricesModel sku = new SkuPricesModel(skuId, priceValidityModelList);

        List<SkuPricesModel> skuPrices = new ArrayList<>();
        skuPrices.add(sku);

        JSONObject body = new JSONObject()
                .put("StoreId", storeID)
                .put("SkuPrices", skuPrices);


        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .header("Message-Id", "1")
                .header("Message-Number", "1")
                .header("Total-Messages", "1")
                .header(Headers.AUTHORIZATION_LENTOCHKA)
                .body(body.toString())
                .when()
                .put(url)
                .andReturn();

    }

    @Step(value = "Устновка цен точки PUT, несколько товаров")
    public Response put_lentochkaSetPrice(String storeID,
                                          String skuId, int regularPrice, int discountPrice,
                                          boolean isPromoSku, boolean deleted,
                                          String secondSkuId, int regularPriceSecondSku, int discountPriceSecondSku,
                                          boolean isPromoSecondSku, boolean deletedSecondSKU) {
//==========================================================================================================
        //First SKU
        String url = host + "/api/v1/lentochka/prices";
        PriceValidityModel priceValidityModel = new PriceValidityModel(
                regularPrice,
                discountPrice,
                "2021-05-07T00:00:00",
                "2022-03-31T23:59:59",
                isPromoSku);
        priceValidityModel.setIsdeleted(deleted);
        List<PriceValidityModel> priceValidityModelList = new ArrayList<>();
        priceValidityModelList.add(priceValidityModel);
//==========================================================================================================
        //Second SKU
        PriceValidityModel secondPriceValidityModel = new PriceValidityModel(
                regularPriceSecondSku,
                discountPriceSecondSku,
                "2021-05-07T00:00:00",
                "2022-03-31T23:59:59",
                isPromoSecondSku);
        secondPriceValidityModel.setIsdeleted(deletedSecondSKU);
        List<PriceValidityModel> secondPriceValidityModelList = new ArrayList<>();
        secondPriceValidityModelList.add(secondPriceValidityModel);
//==========================================================================================================

        List<SkuPricesModel> skuPricesModels = new ArrayList<>();
        skuPricesModels.add(new SkuPricesModel(skuId, priceValidityModelList));
        skuPricesModels.add(new SkuPricesModel(secondSkuId, secondPriceValidityModelList));


        JSONObject body = new JSONObject()
                .put("StoreId", storeID)
                .put("SkuPrices", new JSONArray(skuPricesModels));

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .header(Headers.AUTHORIZATION_LENTOCHKA)
                .header("Message-Id", "1")
                .header("Message-Number", "1")
                .header("Total-Messages", "1")
                .body(body.toString())
                .when()
                .put(url)
                .andReturn();
    }

    @Step(value = "Устновка цен точки POST")
    public Response post_lentochkaSetPrice(String storeID, String skuId, int regularPrice,
                                          int discountPrice, boolean isPromo, boolean deleted) {

        String url = host + "/api/v1/lentochka/prices";
        PriceValidityModel priceValidityModel = new PriceValidityModel(
                regularPrice,
                discountPrice,
                "2020-05-07T00:00:00",
                "2023-03-31T23:59:59",
                isPromo);
        priceValidityModel.setIsdeleted(deleted);

        List<PriceValidityModel> priceValidityModelList = new ArrayList<>();
        priceValidityModelList.add(priceValidityModel);

        SkuPricesModel sku = new SkuPricesModel(skuId, priceValidityModelList);

        List<SkuPricesModel> skuPrices = new ArrayList<>();
        skuPrices.add(sku);

        JSONObject body = new JSONObject()
                .put("StoreId", storeID)
                .put("SkuPrices", skuPrices);


        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .header("Message-Id", "1")
                .header("Message-Number", "1")
                .header("Total-Messages", "1")
                .header(Headers.AUTHORIZATION_LENTOCHKA)
                .body(body.toString())
                .when()
                .put(url)
                .andReturn();

    }

    @Step(value = "Устновка цен точки POST, несколько товаров")
    public Response post_lentochkaSetPrice(String storeID,
                                          String skuId, int regularPrice, int discountPrice,
                                          boolean isPromoSku, boolean deleted,
                                          String secondSkuId, int regularPriceSecondSku, int discountPriceSecondSku,
                                          boolean isPromoSecondSku, boolean deletedSecondSKU) {
//==========================================================================================================
        //First SKU
        String url = host + "/api/v1/lentochka/prices";
        PriceValidityModel priceValidityModel = new PriceValidityModel(
                regularPrice,
                discountPrice,
                "2021-05-07T00:00:00",
                "2022-03-31T23:59:59",
                isPromoSku);
        priceValidityModel.setIsdeleted(deleted);
        List<PriceValidityModel> priceValidityModelList = new ArrayList<>();
        priceValidityModelList.add(priceValidityModel);
//==========================================================================================================
        //Second SKU
        PriceValidityModel secondPriceValidityModel = new PriceValidityModel(
                regularPriceSecondSku,
                discountPriceSecondSku,
                "2021-05-07T00:00:00",
                "2022-03-31T23:59:59",
                isPromoSecondSku);
        secondPriceValidityModel.setIsdeleted(deletedSecondSKU);
        List<PriceValidityModel> secondPriceValidityModelList = new ArrayList<>();
        secondPriceValidityModelList.add(secondPriceValidityModel);
//==========================================================================================================

        List<SkuPricesModel> skuPricesModels = new ArrayList<>();
        skuPricesModels.add(new SkuPricesModel(skuId, priceValidityModelList));
        skuPricesModels.add(new SkuPricesModel(secondSkuId, secondPriceValidityModelList));


        JSONObject body = new JSONObject()
                .put("StoreId", storeID)
                .put("SkuPrices", new JSONArray(skuPricesModels));

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .header(Headers.AUTHORIZATION_LENTOCHKA)
                .header("Message-Id", "1")
                .header("Message-Number", "1")
                .header("Total-Messages", "1")
                .body(body.toString())
                .when()
                .put(url)
                .andReturn();
    }

    @Step(value = "Получение информации о товаре")
    public Response getSKUWithLogin(String storeId, String skuId, Cookies cookies) {
        return RestAssured
                .given()
                .header(CONTENT_TYPE_JSON)
                .cookies(cookies)
                .when()
                .get(host + "/api/v2/stores/" + storeId + "/skus/" + skuId)
                .andReturn();

    }
}
