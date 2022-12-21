package web.ops.api;

import common.cnst.Headers;
import common.dto.User;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import web.objs.model.CartSkuItemPostModel;
import web.objs.model.CartSkusPostModel;

import java.util.List;

public class EcomAPI extends BaseAPIClass {

    @Step(value = "Получить статус активности Ecom в торговом центре")
    public Response get_EcomStatsInTradeCenter(String uri) {
        return RestAssured.get(host + uri).andReturn();
    }

    @Step(value = "Синхронизация корзины")
    public Response post_syncCart(Cookies cokies, List<CartSkuItemPostModel> skus, List<CartSkuItemPostModel> cart) {
        CartSkusPostModel cartSkusPostModel = new CartSkusPostModel();
        cartSkusPostModel.setCart(cart);
        cartSkusPostModel.setSkus(skus);

        JSONObject body = new JSONObject(cartSkusPostModel);

        String url = host + "/api/v1/cart/sync";
        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .cookies(cokies)
                .body(body.toString())
                .post(url)
                .andReturn();
    }

    @Step(value = "Получить статус корзины")
    public Response get_CartStatus(Cookies cookies, String store) {
        String url = host + "/api/v1/cart/sync";

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .cookies(cookies)
                .param("storeID", store)
                .when()
                .get(url)
                .andReturn();
    }

    @Step(value = "Получить актуальную информацтю по товарам в ТК")
    public Response post_ActualSKUInfo(List<String> skucodes, String storeId) {
        String url = host + "/api/v1/stores/{storeId}/skuslist";

        JSONObject body = new JSONObject()
                .put("skuCodes", new JSONArray(skucodes));

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .body(body.toString())
                .pathParam("storeId", storeId)
                .when()
                .post(url)
                .andReturn();
    }

    @Step(value = "Разместить заказ")
    public Response get_createOrder(Cookies strings, String storeId) {
        String url = host + "/api/v2/ecom/order/create";

        return RestAssured
                .given()
                .cookies(strings)
                .param("storeId", storeId)
                .when()
                .get(url)
                .andReturn();
    }

    @Step(value = "Получить информацию по остаткам в тк")
    public Response get_StockInfoFromAllStores(String city, String skuCode) {
        String url = host + "/api/v1/stores/{cityId}/skus/{skuCode}";

        return RestAssured
                .given()
                .pathParam("cityId", city)
                .pathParam("skuCode", skuCode)
                .when()
                .get(url)
                .andReturn();

    }

    @Step(value = "Получить список магазинов в определенном городе")
    public Response get_StoresOfCity(String cityID) {
        String url = host + "/api/v1/cities/{cityId}/stores";

        return RestAssured
                .given()
                .pathParam("cityId", cityID)
                .when()
                .get(url)
                .andReturn();
    }

    @Step(value = "Подтвердить заках")
    public Response post_ConfirmOrder(Cookies cookies, String timeslot, User user) {
        String url = host + "/api/v2/ecom/order/confirm";

        JSONObject body = new JSONObject()
                .put("name", user.getFirstName())
                .put("phone", user.getPhone())
                .put("timeSlotId", timeslot)
                .put("comment", "")
                .put("replacementStrategy", "Remove");

        return RestAssured
                .given()
                .header(Headers.CONTENT_TYPE_JSON)
                .cookies(cookies)
                .body(body.toString())
                .post(url)
                .andReturn();
    }

    @Step(value = "Добавить товары в корзину")
    public Response post_addGoodsToCart(Cookies cookies, String sku) {
        String url = host + "/api/v2/ecom/cart";

        JSONObject body = new JSONObject()
                .put("skuCode", sku)
                .put("quantity", 3)
                .put("isPostedFromCartPage", false);

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
