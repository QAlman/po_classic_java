package web.tests.rest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import common.builder.UserBuilder;
import common.cnst.Tags;
import common.dto.User;
import io.qameta.allure.Owner;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import web.objs.model.CartSkuItemPostModel;
import web.ops.api.ProjectAPI;
import web.ops.api.UserAPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static web.ops.util.datautils.ConfigPropertiesUtil.getProperty;

public class ProjectAPITests extends BaseRestClass {
    private static String fieldsList;

    private static User user = new User();
    private final UserBuilder userBuilder = new UserBuilder();
    private static final UserAPI userAPI = new UserAPI();
    private final String defaultStore = getProperty("Project.defaultStore");
    private final String defaultSKU = getProperty("Project.defaultSKU");
    private final ProjectAPI ProjectAPI = new ProjectAPI();

    @Before
    public void setUp() {
            user = userAPI.registerUserWithDefaultCity();
            log.info(user.getPhone());
            log.info(user.getPassWord());
    }

    @Test
    @DisplayName("Проверить статус  в ТК (Из магазина)")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void get_ProjectmStatusInTC_fromStores_Test() {
        fieldsList = "id, name, address, cityKey, cityName, type, lat, long, isDefaultStore, isProjectAvailable, is24hStore, hasPetShop, storeTimeZoneOffset";

        Response response = ProjectAPI.get_ProjectStatsInTradeCenter("/api/v1/stores?storeId=0071");
        String body = response.getBody().asString();

        JsonElement parse = JsonParser.parseString(body);
        log.trace(body);

        JsonArray responseBody = parse.getAsJsonArray();

        String ProjectStatus = null;

        for (int i = 0; i < responseBody.size(); i++) {
            JsonObject storeobject = responseBody.get(i).getAsJsonObject();
            String id = storeobject.get("id").getAsString();
            if (id.equals("0002")) {
                ProjectStatus = storeobject.get("isProjectAvailable").getAsString();
            }
        }

        log.info("0002 Project status: " + ProjectStatus);

        Assert.assertEquals("0002 TK: Project should be enabled, current value: " + ProjectStatus
                , ProjectStatus, "true");

        Assert.assertTrue(ìsRequiredFieldsInResponse(fieldsList, body));
    }


    @Test
    @DisplayName("Проверить статус Projectm в ТК (с главной)")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void get_ProjectmStatusInTC_fromHome_Test() {
        Response response = ProjectAPI.get_ProjectStatsInTradeCenter("/api/v1/stores/0012/home");

        JsonElement body = JsonParser.parseString(response.getBody().asString());
        log.trace(body);

        String isProjectAvailable = body.getAsJsonObject().get("isProjectAvailable").getAsString();

        log.info("0012 Project status: " + isProjectAvailable);

        Assert.assertEquals("0012 TK: Project should be enabled, current value: " + isProjectAvailable
                , isProjectAvailable, "true");

    }

    /**
     * А актуален ли данный тест? В свагере есть sync V2
     **/
    @Test
    @DisplayName("Синхронизация корзины API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void get_CartSync_Test() {
        List<CartSkuItemPostModel> skus = new ArrayList<>();
        skus.add(new CartSkuItemPostModel(0, "тесто"));
        skus.add(new CartSkuItemPostModel(1, "Бумажные полотенца FAMILIA 2-сл.", "352153"));

        List<CartSkuItemPostModel> cart = new ArrayList<>();
        cart.add(new CartSkuItemPostModel(0, "КПолное красивое имя для конфет", "098354"));


        User user = userBuilder
                .withPhone("79629947084")
                .withDefaultPassword()
                .execute();

        fieldsList = "title, skus, promotions, cartStoreId, cart, promotions";

        Cookies cookies = userAPI.loginAndGetCookies(user);
        addGoodsToCart(cookies, defaultSKU);

        Response res = ProjectAPI.post_syncCart(cookies, skus, cart);

        String rawBody = res.getBody().asString();
        log.info(rawBody);
        JsonElement parse = JsonParser.parseString(rawBody);
        JsonElement listofPurchases = parse.getAsJsonObject().get("skus").getAsJsonArray().get(0);

        Assert.assertNotNull("list of purchases is empty", listofPurchases);
        Assert.assertTrue(ìsRequiredFieldsInResponse(fieldsList, rawBody));
    }


    @Test
    @DisplayName("Обновление статуса корзины API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void post_CartStatus_Test() {
        List<CartSkuItemPostModel> skus = new ArrayList<>();
        skus.add(new CartSkuItemPostModel(0, "тесто"));
        skus.add(new CartSkuItemPostModel(1, "Бумажные полотенца FAMILIA 2-сл.", "352153"));

        List<CartSkuItemPostModel> cart = new ArrayList<>();
        cart.add(new CartSkuItemPostModel(0, "КПолное красивое имя для конфет", "098354"));

        Cookies cookies = userAPI.loginAndGetCookies(user);

        Response response = ProjectAPI.post_syncCart(cookies, skus, cart);

        log.trace(response.getBody());
        Assert.assertEquals(200, response.statusCode());
    }


    @Test
    @DisplayName("Проверка статуса корзины API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void get_CartStatus_Test() {
        fieldsList = "skus, promotions, cartStoreId, cart, promotions";
        Cookies cookies = userAPI.loginAndGetCookies(user);

        Response response = ProjectAPI.get_CartStatus(cookies, defaultStore);

        log.trace(response.getBody().asString());
        log.trace(response.statusCode());
        log.trace(response.statusLine());

        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertTrue(ìsRequiredFieldsInResponse(fieldsList, response.getBody().asString()));
    }

    @Test
    @DisplayName("Получение актуальной информации по товарам API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void post_ActualSKUInfo_unAuthorizedUser_Test() {
        fieldsList = "stampsPromotion, specification, groupName, groupDescription, properties, vendorCode, brandName, kindOfGoods, formatOfIssue, material, taste, fatContent, aroma, sound, customerSex, customerAge, package, color, amountInPackage, typeOfFeed, sportType, goodsType, sortOfGoods, productLine, numberOfPortions, cookingMethod, heatTreatmentMethod, cookingTime, cookingTemperature, goesWith, cookingAdvices, servingAdvices, consumptionAdvices, gasLevel, alcoholStrength, foodEnergyValue, ingridients, model, power, capacity, producerInfo, groupName, groupDescription, properties, producer, producersCountry, producerInformation, importantLimitations, groupName, groupDescription, properties, alergicInformation, diabeticInformation, pregnantInformation, parentsInformation, customerInformation, precautionaryMeasures, relatedSkus, similarSkus, rates, totalCount, averageRate, scores, rate1, rate2, rate3, rate4, rate5, code, title, brand, subTitle, description, regularPrice, discountPrice, offerDescription, promoType, validityStartDate, validityEndDate, image, thumbnail, medium, fullSize, mediumLossy, images, thumbnail, medium, fullSize, mediumLossy, stampsPrice, webUrl, orderLimit, orderSteps, skuWeight, isAvailableForOrder, isWeightProduct, stock, categories, group, code, name, category, code, name, subcategory, code, name";
        List<String> skus = Arrays.asList("354342, 354338, 498054".split(","));
        Response response = ProjectAPI.post_ActualSKUInfo(skus, "0002");

        JsonArray asJsonArray = JsonParser.parseString(response.getBody().asString()).getAsJsonArray();
        ArrayList<String> skuNames = new ArrayList<>();

        asJsonArray.forEach(element -> skuNames.add(element.getAsJsonObject().get("title").getAsString()));

        log.trace("Status: " + response.statusCode());
        log.trace("Status: " + response.getBody().asString());

        Assert.assertTrue(ìsRequiredFieldsInResponse(fieldsList, response.getBody().asString()));
        Assert.assertTrue("Returned skus array is empty", skuNames.size() > 0);
        Assert.assertTrue("Skus were not returned correctly", skuNames.contains("498054_1"));
        Assert.assertTrue("Skus were not returned correctly", skuNames.contains("Смесь овощная СВОЙ УРОЖАЙ Президентский суп зам"));
        Assert.assertTrue("Skus were not returned correctly", skuNames.contains("Капуста СВОЙ УРОЖАЙ цветная зам"));
    }

    @Test
    @DisplayName("Получение актуальной информации по товарам (Пустой список товаров) API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void post_ActualSKUInfo_emptySku_unAuthorizedUser_Test() {
        Response response = ProjectAPI.post_ActualSKUInfo(Collections.emptyList(), "0002");
        log.info(response.getBody().asString());

        Assert.assertEquals(400, response.statusCode());
    }

    @Test
    @DisplayName("Разместить заказ API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void createOrder_Test() {
        fieldsList = "redirectUrl";
        Response response = createOrder(defaultStore, defaultSKU);

        log.info("Status: " + response.statusCode());
        log.info(response.getBody().asString());

        String redirectUrl = JsonParser.parseString(response.getBody().asString()).getAsJsonObject().get("redirectUrl").getAsString();

        Assert.assertEquals(200, response.statusCode());
        Assert.assertTrue(ìsRequiredFieldsInResponse(fieldsList, response.getBody().asString()));
        Assert.assertNotNull(redirectUrl);
    }

    @Test
    @DisplayName("Разместить заказ (Пустая корзина) API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void createOrderEmptyCartTest() {

        Response response = createEmptyOrder();

        log.info("Status: " + response.statusCode());
        log.info(response.getBody().asString());

        String errorMessage = JsonParser.parseString(response.getBody().asString()).getAsJsonObject().get("errorMessage").getAsString();

        Assert.assertEquals(409, response.statusCode());
        Assert.assertEquals("Корзина пуста, обновите страницу", errorMessage);

    }

    @Test
    @DisplayName("Разместить закак (Невалидный магазин) API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void createInvalidStoreOrderTest() {
        Response response = createOrder("0435", defaultSKU);

        log.info("Status: " + response.statusCode());
        log.info(response.getBody());

        String errorMessage = JsonParser.parseString(response.getBody().asString()).getAsJsonObject().get("message").getAsString();

        Assert.assertEquals(400, response.statusCode());
        Assert.assertEquals("К сожалению, доставка по данному адресу невозможна", errorMessage);
    }

    @Test
    @DisplayName("Оформить и подтвердить заказ API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void createAndConfirmOrderTest() {
        fieldsList = "availableTimeSlots, dayLabel, timeSlots, timeSlotId, timeLabel, dayLabel";
        String defaultTimeslot = "1618326000000-1618333140000-1618326000000-1618333140000-4600184003";

        log.info("SKU " + defaultSKU);
        log.info("StoreId " + defaultStore);
        log.info("User " + user.getPhone());
        log.info("Pass " + user.getPassWord());

        Cookies cookies = userAPI.loginAndGetCookies(user);
        createOrder(defaultStore, defaultSKU);

        Response response = ProjectAPI.post_ConfirmOrder(cookies, defaultTimeslot, user);

        log.info("Response body confirm order " + response.getBody().asString());

        Assert.assertTrue(ìsRequiredFieldsInResponse(fieldsList, response.getBody().asString()));

        if (response.statusCode() != 200) {
            String timeslot = JsonParser.parseString(response.getBody().asString()).getAsJsonObject()
                    .get("availableTimeSlots").getAsJsonArray().get(0)
                    .getAsJsonObject().get("timeSlots").getAsJsonArray().get(0)
                    .getAsJsonObject().get("timeSlotId").getAsString();

            response = ProjectAPI.post_ConfirmOrder(cookies, timeslot, user);
            log.info("Response body confirm order " + response.getBody().asString());
            log.info("TimeSlot " + timeslot);
        }

        Assert.assertEquals("Order was not created", 200, response.statusCode());

    }

    @Test
    @DisplayName("Получить информацию об остатках со всех магазинов API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void get_StocksInfoFromAllStores_Test() {
        fieldsList = "stocks, stockBalance, id, name, address, cityKey, cityName, type, lat, long, opensAt, closesAt, isDefaultStore, isProjectAvailable, is24hStore, hasPetShop, minOrderSumm, maxOrderSumm, maxWeight, maxQuantityPerItem, orderLimitOverall, storeTimeZoneOffset";
        Response response = ProjectAPI.get_StockInfoFromAllStores("spb", "257495");
        log.trace(response.getBody().asString());
        JsonArray stoks = JsonParser.parseString(response.getBody().asString()).getAsJsonObject().get("stocks").getAsJsonArray();

        Assert.assertEquals(200, response.statusCode());
        Assert.assertTrue(ìsRequiredFieldsInResponse(fieldsList, response.getBody().asString()));
        Assert.assertNotNull(stoks.get(0));
    }


    @Test
    @DisplayName("Получить информацию об остатках со всех магазинов (невалидный товар) API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void get_StocksInfoFromAllStores_InvalidSKUId_Test() {
        Response response = ProjectAPI.get_StockInfoFromAllStores("spb", "999999");
        log.trace(response.getBody());

        Assert.assertEquals(404, response.statusCode());
    }

    @Test
    @DisplayName("Получить список всех магазинов в городе API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void get_Cities_Test() {
        fieldsList = "id, name, address, cityKey, cityName, type, lat, long, opensAt, closesAt, isDefaultStore, isProjectAvailable, is24hStore, hasPetShop, minOrderSumm, maxOrderSumm, maxWeight, maxQuantityPerItem, orderLimitOverall, storeTimeZoneOffset";
        Response response = ProjectAPI.get_StoresOfCity("spb");

        log.trace(response.getBody());
        JsonArray stores = JsonParser.parseString(response.getBody().asString()).getAsJsonArray();

        Assert.assertEquals(200, response.statusCode());
        Assert.assertTrue(ìsRequiredFieldsInResponse(fieldsList, response.getBody().asString()));
        Assert.assertNotNull(stores.get(0));
    }


    @Test
    @DisplayName("Получить список всех магазинов в городе (Невалидный город) API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void get_Cities_InvalidCityCode_Test() {
        Response response = ProjectAPI.get_StoresOfCity("loblin");

        log.trace(response.getBody().asString());

        Assert.assertEquals(404, response.statusCode());
    }

    private Response createOrder(String store, String sku) {
        Cookies strings = userAPI.loginAndGetCookies(user);
        addGoodsToCart(strings, sku);
        log.info("Create Order " + host + "/api/v2/Project/order/create?storeId=" + store);
        Response response = ProjectAPI.get_createOrder(strings, store);
        log.info("Response body create order " + response.getBody().asString());
        return response;
    }

    private Response createEmptyOrder() {
        Cookies cookies = userAPI.loginAndGetCookies(user);

        return ProjectAPI.get_createOrder(cookies, "0012");
    }


    public void addGoodsToCart(Cookies cookies, String sku) {
        log.info("add Goods To Cart " + host + "/api/v2/Project/cart");
        Response stringHttpResponse = ProjectAPI.post_addGoodsToCart(cookies, sku);

        log.info("Response status add Goods To Cart " + stringHttpResponse.statusCode());
        log.info("Response add Goods To Cart " + stringHttpResponse.getBody().asString());
    }


}
