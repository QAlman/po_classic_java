package web.ops.api;

import common.cnst.Headers;
import common.dto.User;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import web.objs.model.SkuModel;
import web.objs.model.SkuPricesModel;
import web.objs.model.SkuStocks;

import java.util.List;

public class SKUAPI extends BaseAPIClass {
    private static final String series = String.format("%.0f", Math.random() * 100000000000000L);

    @Step(value = "Импорт остатков")
    public Response importStocks(int stock, String storeId, String skuId) {
        String url = host + "/api/v1/stocks";

        SkuStocks skuStocks = new SkuStocks();
                skuStocks.setStock(stock);
                skuStocks.setSkuCode(skuId);

        JSONObject body = new JSONObject()
                .put("storeId", storeId)
                .put("skuStocks", new JSONArray().put(new JSONObject(skuStocks)));

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .header(Headers.BASIC_AUTH)
                .header(new Header("Series", "PIM" + series))
                .header(new Header("Message-Number", "1"))
                .header(new Header("Total-Messages", "2"))
                .body(body.toString())
                .post(url)
                .andReturn();
    }

    @Step(value = "Импорт цен")
    public Response importPrices(String storeId, List<SkuPricesModel> prices) {
        String url = host + "/api/v1/prices";

        JSONObject body = new JSONObject()
                .put("StoreId", storeId)
                .put("SkuPrices", new JSONArray(prices));

        System.out.println(body.toString());

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .header(Headers.BASIC_AUTH)
                .header(new Header("Series", "PIM" + series))
                .header(new Header("Message-Number", "1"))
                .header(new Header("Total-Messages", "1"))
                .header(new Header("Message-Id", "1"))
                .body(body.toString())
                .put(url)
                .andReturn();
    }

    @Step(value = "Импорт товаров")
    public Response importSkus(List<SkuModel> skus) {
        String url = host + "/api/v1/skus";

        JSONObject body = new JSONObject().put("Skus", new JSONArray(skus));

        System.out.println(body.toString());

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .header(Headers.BASIC_AUTH)
                .header(new Header("Series", "1"))
                .header(new Header("Message-Number", "1"))
                .header(new Header("Message-Id", "1"))
                .header(new Header("Total-Messages", "1"))
                .body(body.toString())
                .post(url)
                .andReturn();
    }

    @Step(value = "Получить товар")
    public Response getSKU(String storeId, String skuId) {
        String url = host + "/api/v1/stores/" + storeId + "/skus/" + skuId;

        return RestAssured
                .given()
                .get(url)
                .andReturn();
    }

    @Step(value = "Получить поля карточки SKU")
    public Response get_fieldsOfSKUCard() {
        String url = host + "/api/v1/labels/sku/";

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .get(url)
                .andReturn();

    }

    @Step(value = "Получить информацию о каталоге")
    public Response get_CatalogInformation(String storeid) {
        String url = host + "/api/v1/stores/{storeId}/catalog";

        return RestAssured
                .given()
                .pathParam("storeId", storeid)
                .header(Headers.CONTENT_TYPE_JSON)
                .get(url)
                .andReturn();
    }

    @Step(value = "Поиск (Мобильное АПИ)")
    public Response get_searchMobileApi(String storeId, String searchBody) {
        String url = host + "/api/v1/stores/{storeId}/catalog/search";

        return RestAssured
                .given()
                .pathParam("storeId", storeId)
                .param("value", searchBody)
                .header(Headers.CONTENT_TYPE_JSON)
                .get(url)
                .andReturn();
    }

    @Step(value = "Получить информацию о группе продукта")
    public Response get_ProductGroupFiltersInfo() {
        String url = host + "/api/v1/nodes/nodeinfo";

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .get(url)
                .andReturn();
    }

    @Step(value = "Добавить коментарий к товару")
    public Response addCommentToSKU(String skucode, User user) {
        String url = host + "/api/v1/addcomments";

        JSONObject body = new JSONObject()
                .put("skuCode",skucode)
                .put("userName",user.getPhone())
                .put("text","Maximum shit")
                .put("rating","2");

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .body(body.toString())
                .post(url)
                .andReturn();
    }

    @Step(value = "Получить коментарии SKU")
    public Response getSKUComment(String skuId) {
        String url = host + "/api/v1/comments/{skuId}";

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .pathParam("skuId", skuId)
                .param("offset", "0")
                .param("count", "1")
                .get(url)
                .andReturn();
    }

    @Step(value = "Получить название SKU")
    public Response getSKUName(String skucode) {
        String url = host + "/api/v1/skus/{skuCode}/name";

        return RestAssured
                .given()
                .pathParam("skuCode", skucode)
                .header(Headers.CONTENT_TYPE_JSON)
                .get(url)
                .andReturn();
    }

    @Step(value = "Получить список тегов")
    public Response getTags() {
        String url = host + "/api/v1/tags/list";

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .get(url)
                .andReturn();
    }
}
