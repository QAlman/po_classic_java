package web.tests.rest;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import common.cnst.Tags;
import io.qameta.allure.Owner;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import web.objs.model.PriceValidityModel;
import web.objs.model.SkuModel;
import web.objs.model.SkuPricesModel;
import web.ops.api.SKUAPI;
import web.ops.helpers.CommonHelpers;

import java.util.ArrayList;
import java.util.List;


public class SkuAPITests extends BaseRestClass {
    private final String storeId = "0009";
    private static final int regularPrice = (int) (Math.random() * 2000);
    private static final int discountPrice = (int) (Math.random() * 100);
    private final SKUAPI skuApi = new SKUAPI();
    static List<PriceValidityModel> prices = new ArrayList<>();
    static SkuModel skuModel = new SkuModel();

    @BeforeClass
    public static void setUpPrices() {
        PriceValidityModel price2 = new PriceValidityModel(
                regularPrice,
                discountPrice,
                "2021-05-01",
                "2090-11-29",
                false);

        prices.add(price2);

        skuModel.setName("Колбаса");
        skuModel.setBrand("PIERRE CARDIN");
        skuModel.setDescription("Lille 20den visone 3");
        skuModel.setFullDescription(CommonHelpers.generateRandomCyrilicString(10));
        skuModel.setPackage("1L");
        skuModel.setGoodsType("1HAW");
        skuModel.setGTIN(null);
        skuModel.setPrice(String.valueOf(regularPrice));
        skuModel.setSubCategoryName("Женские чулочно-носочные изделия");
        skuModel.setSubCategoryId("530201");
        skuModel.setCategory(null);
        skuModel.setGoodsGroup(null);
        skuModel.setProducer("PIERRE CARDIN");
        skuModel.setCountryProducer("CountryProducer");
        skuModel.setGOST("");
        skuModel.setShelfLife("360 День");
        skuModel.setStorageConditions(null);
        skuModel.setPriceLevelType(null);
        skuModel.setPriceGroupId(null);
        skuModel.setNovelty(false);
        skuModel.setSeasonalGoods(false);
        skuModel.setNetWeight(1);
        skuModel.setGrossWeight(1);
        skuModel.setLength(71);
        skuModel.setWidth(72);
        skuModel.setDimensionUOM("MM");
        skuModel.setHeight(215);
        skuModel.setVolume(0);
        skuModel.setVolumeUOM(null);
        skuModel.setProductLine("");
        skuModel.setAmountInPackage("");
        skuModel.setColor("");
        skuModel.setMaterial("");
        skuModel.setIngridients("");
        skuModel.setFoodEnergyValue("");
        skuModel.setAlcoholStrength("0.00");
        skuModel.setVendorCode("");
    }

    @Test
    @DisplayName("Создание товара API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void createSkuTest() {
//=======================Prepare=Values==========================================================
        String skuId = String.format("%.0f", Math.random() * 1000000);
        log.info(" new SkuID " + skuId);
        skuModel.setSapCode(skuId);

        List<SkuModel> skus = new ArrayList<>();
        skus.add(skuModel);

        SkuPricesModel sku = new SkuPricesModel(skuId, prices);

        List<SkuPricesModel> skuPrices = new ArrayList<>();
        skuPrices.add(sku);

//=======================Do=Requests==========================================================
        skuApi.importSkus(skus);
        skuApi.importPrices(storeId, skuPrices);
        skuApi.importStocks(400, storeId, skuId);

        Response skuBeforeChange = skuApi.getSKU(storeId, skuId);


//=======================Handle=Results==========================================================
        JsonElement skuAsJson = JsonParser.parseString(skuBeforeChange.getBody().asString());
        JsonElement stockAfterChange = skuAsJson.getAsJsonObject().get("stock");
        JsonElement regularPriceAfterChange = skuAsJson.getAsJsonObject().get("regularPrice");
        JsonElement discountPriceAfterChange = skuAsJson.getAsJsonObject().get("discountPrice");
        log.info("stock After Change " + stockAfterChange);
        log.info("regular Price After Change " + regularPriceAfterChange);
        log.info("discount Price After Change " + discountPriceAfterChange);

        Assert.assertNotEquals("Stocks should be different. Actual: " + stockAfterChange, "None", stockAfterChange);

        Assert.assertEquals("Regular price should be equal to generated value: " + regularPrice + " ; " + regularPriceAfterChange, regularPrice, regularPriceAfterChange.getAsInt());
        Assert.assertEquals("Discount price should be equal to generated value: " + discountPrice + " ; " + discountPriceAfterChange, discountPrice, discountPriceAfterChange.getAsInt());
    }

    @Test
    @DisplayName("Изменение товара API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void changeSKUTest() {
//==========================================================================================================
        //Prepare values
        String skuId = "231213";

        List<SkuModel> skus = new ArrayList<>();
        skus.add(skuModel);

        List<SkuPricesModel> skuPrices = new ArrayList<>();
        skuPrices.add(new SkuPricesModel(skuId, prices));


        Response skuBeforeChange = skuApi.getSKU(storeId, skuId);
        JsonElement skuAsJson = JsonParser.parseString(skuBeforeChange.getBody().asString());

        JsonElement stockBeforeChange = skuAsJson.getAsJsonObject().get("stock");
        JsonElement regularPriceBeforeChange = skuAsJson.getAsJsonObject().get("regularPrice");
        JsonElement discountPriceBeforeChange = skuAsJson.getAsJsonObject().get("discountPrice");

//==========================================================================================================
        //Do requests
        skuApi.importSkus(skus);

        skuApi.importPrices(storeId, skuPrices);

        skuApi.importStocks(0, storeId, skuId);

        log.info("stock Before Change: " + stockBeforeChange);
        log.info("regular Price Before Change: " + regularPriceBeforeChange);
        log.info("discount Price Before Change: " + discountPriceBeforeChange);
//==========================================================================================================
        //Get values after requests
        Response skuAfterChange = skuApi.getSKU(storeId, skuId);
        skuAsJson = JsonParser.parseString(skuAfterChange.getBody().asString());

        JsonElement stockAfterChange = skuAsJson.getAsJsonObject().get("stock");
        JsonElement regularPriceAfterChange = skuAsJson.getAsJsonObject().get("regularPrice");
        JsonElement discountPriceAfterChange = skuAsJson.getAsJsonObject().get("discountPrice");
        log.info("stock After Change: " + stockAfterChange);
        log.info("regular Price After Change: " + regularPriceAfterChange);
        log.info("discount Price After Change: " + discountPriceAfterChange);

//==========================================================================================================
        //Asserts
        if (stockBeforeChange.equals(stockAfterChange)) {
            skuApi.importStocks(100, storeId, skuId);
            Response sku = skuApi.getSKU(storeId, skuId);
            skuAsJson =JsonParser.parseString(sku.getBody().asString());
            stockAfterChange = skuAsJson.getAsJsonObject().get("stock");
        }

        Assert.assertNotEquals("Stocks should be different. Actual: " + stockAfterChange, stockBeforeChange, stockAfterChange);
        Assert.assertNotEquals("Regular prices should be different. Actual: " + discountPriceAfterChange, discountPriceBeforeChange, discountPriceAfterChange);
        Assert.assertNotEquals("Regular prices should be different. Actual: " + regularPriceAfterChange, regularPriceBeforeChange, regularPriceAfterChange);

        Assert.assertEquals("Regular price should be equal to generated value: " + regularPrice + " ; " + regularPriceAfterChange, regularPrice, regularPriceAfterChange.getAsInt());
        Assert.assertEquals("Discount price should be equal to generated value: " + discountPrice + " ; " + discountPriceAfterChange, discountPrice, discountPriceAfterChange.getAsInt());
    }

    @Test
    @DisplayName("Получить поля карточки товара API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void getFieldsOfSKUCardTest() {
        Response response = skuApi.get_fieldsOfSKUCard();

        log.info(response.statusCode());
        log.info(response.getBody());

        Assert.assertEquals(200, response.statusCode());
        Assert.assertNotNull(response.getBody());
        Assert.assertTrue(ìsRequiredFieldsInResponse(testData.getVariable("fields"), response.getBody().asString()));
    }

    @Test
    @DisplayName("Получить информацию о каталоге API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void getCatalogInformationTest() {
        Response response = skuApi.get_CatalogInformation("0002");

        log.info(response.statusCode());
        log.info(response.getBody());

        Assert.assertEquals(200, response.statusCode());
        Assert.assertNotNull(response.getBody());
        Assert.assertTrue(ìsRequiredFieldsInResponse(testData.getVariable("fields"), response.getBody().asString()));
    }

    @Test
    @DisplayName("Поиск API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void SearchTest() {
        Response response = skuApi.get_searchMobileApi("0002", "морковь");

        log.info(response.statusCode());
        log.info(response.getBody().asString());

        Assert.assertEquals(200, response.statusCode());
        Assert.assertNotNull(response.getBody());
        Assert.assertTrue(ìsRequiredFieldsInResponse(testData.getVariable("fields"), response.getBody().asString()));
    }

    @Test
    @DisplayName("Получить информацию о продуктовых группах API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void getProductGroupFiltersInfoTest() {
        Response response = skuApi.get_ProductGroupFiltersInfo();

        log.info(response.statusCode());
        log.info(response.getBody().asString());

        Assert.assertEquals(200, response.statusCode());
        Assert.assertNotNull(response.getBody());
        Assert.assertTrue(ìsRequiredFieldsInResponse(testData.getVariable("fields"), response.getBody().asString()));
    }

    @Test
    @DisplayName("Добавление коментария API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void addCommentTest() {
        String skucode = "471713";

        Response response = skuApi.addCommentToSKU(skucode, defaultUser);

        log.info(response.statusCode());
        log.info(response.getBody().asString());

        Assert.assertEquals(201, response.statusCode());
        Assert.assertNotNull(response.getBody());
    }


    @Test
    @DisplayName("Получить коментарий товара API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void getSkuCommentsTest() {
        String skuId = "379098";

        Response response = skuApi.getSKUComment(skuId);

        log.info(response.statusCode());
        log.info(response.getBody().asString());

        Assert.assertEquals(200, response.statusCode());
        Assert.assertNotNull(response.getBody());
    }


    @Test
    @DisplayName("Получить имя товара API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void getSkuNameTest() {
        String skuCode = "471713";

        Response response = skuApi.getSKUName(skuCode);

        String name = JsonParser.parseString(response.getBody().asString()).getAsJsonObject().get("name").getAsString();

        log.info(response.statusCode());
        log.info(response.getBody().asString());

        Assert.assertEquals(200, response.statusCode());
        Assert.assertNotNull(response.getBody());
        Assert.assertTrue(ìsRequiredFieldsInResponse("code, name", response.getBody().asString()));
        Assert.assertEquals("Маска тканевая для лица LANIX M La Miso Essence Sheet, снятие отеков с экстрактом картофеля, 21г", name);
    }


    @Test
    @DisplayName("Получить все теги API")
    @Owner(value = "Антон")
    @Tag(value = Tags.API)
    public void getAllTagsTest() {
        Response response = skuApi.getTags();

        log.info(response.statusCode());
        log.info(response.getBody());

        Assert.assertEquals(200, response.statusCode());
        Assert.assertNotNull(response.getBody());
    }


}
