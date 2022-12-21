package web.objs.model;

public class SkuModel {
    /**
     * TODO: сделать билдер
     */
    /// <summary>
    /// Gets or sets MATNR.
    /// </summary>
    private String SapCode;

    /// <summary>
    /// Gets or sets ZEXARTNM.
    /// </summary>
    private String Name;

    /// <summary>
    /// Gets or sets EXTWG.
    /// </summary>
    private String Brand;

    /// <summary>
    /// Gets or sets ZEXARTDS.
    /// </summary>
    private String Description;

    /// <summary>
    /// Gets or sets ZEXLTEXT.
    /// </summary>
    private String FullDescription;

    /// <summary>
    /// Gets or sets ZEXPACKG.
    /// </summary>
    private String Package;

    /// <summary>
    /// Gets or sets MTART.
    /// </summary>
    private String GoodsType;

    /// <summary>
    /// Gets or sets EAN11.
    /// </summary>
    private String GTIN;

    /// <summary>
    /// Gets or sets list of GTINs.
    /// </summary>
    //private IEnumerable<GTINModel> GTINs;

    /// <summary>
    /// Gets or sets PRICE.
    /// </summary>
    private String Price;

    /// <summary>
    /// Gets or sets WGBEZ60.
    /// </summary>
    private String SubCategoryName;

    /// <summary>
    /// Gets or sets MATKL.
    /// </summary>
    private String SubCategoryId;

    /// <summary>
    /// Gets or sets MATKL.
    /// </summary>
    private String Category;

    /// <summary>
    /// Gets or sets MTART.
    /// </summary>
    private String GoodsGroup;

    /// <summary>
    /// Gets or sets MFRNR.
    /// </summary>
    private String Producer;

    /// <summary>
    /// Gets or sets WHERL.
    /// </summary>
    private String CountryProducer;

    /// <summary>
    /// Gets or sets GOST.
    /// </summary>
    private String GOST;

    /// <summary>
    /// Gets or sets MHDHB.
    /// </summary>
    private String ShelfLife;

    /// <summary>
    /// Gets or sets WHSTC.
    /// </summary>
    private String StorageConditions;

    /// <summary>
    /// Gets or sets PLGTP.
    /// </summary>
    private String PriceLevelType;

    /// <summary>
    /// Gets or sets ZPRFAM.
    /// </summary>
    private String PriceGroupId;

    private boolean IsNovelty;

    private boolean IsSeasonalGoods;

    /// <summary>
    /// Gets or sets INHAL.
    /// </summary>
    private int NetWeight;

    /// <summary>
    /// Gets or sets BRGEW.
    /// </summary>
    private int GrossWeight;

    /// <summary>
    /// Gets or sets LAENG.
    /// </summary>
    private int Length;

    /// <summary>
    /// Gets or sets BREIT.
    /// </summary>
    private int Width;

    /// <summary>
    /// Gets or sets HOEHE.
    /// </summary>
    private int Height;

    /// <summary>
    /// Gets or sets DimensionUOM.
    /// </summary>
    private String DimensionUOM;

    /// <summary>
    /// Gets or sets VOLUM.
    /// </summary>
    private int Volume;

    /// <summary>
    /// Gets or sets VOLEH.
    /// </summary>
    private String VolumeUOM;

    /// <summary>
    /// Gets or sets Z_COLLECT.
    /// </summary>
    private String ProductLine;

    /// <summary>
    /// Gets or sets Z_CONTENTS.
    /// </summary>
    private String AmountInPackage;

    /// <summary>
    /// Gets or sets Z_COLOR.
    /// </summary>
    private String Color;

    /// <summary>
    /// Gets or sets Z_MATERIAL.
    /// </summary>
    private String Material;

    /// <summary>
    /// Gets or sets INGRIDIENTS.
    /// </summary>
    private String Ingridients;

    /// <summary>
    /// Gets or sets NUTVAL.
    /// </summary>
    private String FoodEnergyValue;

    /// <summary>
    /// Gets or sets Z_ALCOHOL_STR.
    /// </summary>
    private String AlcoholStrength;

    /// <summary>
    /// Gets or sets ZEXARTCD.
    /// </summary>
    private String VendorCode;

    private boolean IsWeightProduct;

    public String getSapCode() {
        return SapCode;
    }

    public void setSapCode(String sapCode) {
        SapCode = sapCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getFullDescription() {
        return FullDescription;
    }

    public void setFullDescription(String fullDescription) {
        FullDescription = fullDescription;
    }

    public String getPackage() {
        return Package;
    }

    public void setPackage(String aPackage) {
        Package = aPackage;
    }

    public String getGoodsType() {
        return GoodsType;
    }

    public void setGoodsType(String goodsType) {
        GoodsType = goodsType;
    }

    public String getGTIN() {
        return GTIN;
    }

    public void setGTIN(String GTIN) {
        this.GTIN = GTIN;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getSubCategoryName() {
        return SubCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        SubCategoryName = subCategoryName;
    }

    public String getSubCategoryId() {
        return SubCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        SubCategoryId = subCategoryId;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getGoodsGroup() {
        return GoodsGroup;
    }

    public void setGoodsGroup(String goodsGroup) {
        GoodsGroup = goodsGroup;
    }

    public String getProducer() {
        return Producer;
    }

    public void setProducer(String producer) {
        Producer = producer;
    }

    public String getCountryProducer() {
        return CountryProducer;
    }

    public void setCountryProducer(String countryProducer) {
        CountryProducer = countryProducer;
    }

    public String getGOST() {
        return GOST;
    }

    public void setGOST(String GOST) {
        this.GOST = GOST;
    }

    public String getShelfLife() {
        return ShelfLife;
    }

    public void setShelfLife(String shelfLife) {
        ShelfLife = shelfLife;
    }

    public String getStorageConditions() {
        return StorageConditions;
    }

    public void setStorageConditions(String storageConditions) {
        StorageConditions = storageConditions;
    }

    public String getPriceLevelType() {
        return PriceLevelType;
    }

    public void setPriceLevelType(String priceLevelType) {
        PriceLevelType = priceLevelType;
    }

    public String getPriceGroupId() {
        return PriceGroupId;
    }

    public void setPriceGroupId(String priceGroupId) {
        PriceGroupId = priceGroupId;
    }

    public boolean isNovelty() {
        return IsNovelty;
    }

    public void setNovelty(boolean novelty) {
        IsNovelty = novelty;
    }

    public boolean isSeasonalGoods() {
        return IsSeasonalGoods;
    }

    public void setSeasonalGoods(boolean seasonalGoods) {
        IsSeasonalGoods = seasonalGoods;
    }

    public int getNetWeight() {
        return NetWeight;
    }

    public void setNetWeight(int netWeight) {
        NetWeight = netWeight;
    }

    public int getGrossWeight() {
        return GrossWeight;
    }

    public void setGrossWeight(int grossWeight) {
        GrossWeight = grossWeight;
    }

    public int getLength() {
        return Length;
    }

    public void setLength(int length) {
        Length = length;
    }

    public int getWidth() {
        return Width;
    }

    public void setWidth(int width) {
        Width = width;
    }

    public int getHeight() {
        return Height;
    }

    public void setHeight(int height) {
        Height = height;
    }

    public String getDimensionUOM() {
        return DimensionUOM;
    }

    public void setDimensionUOM(String dimensionUOM) {
        DimensionUOM = dimensionUOM;
    }

    public int getVolume() {
        return Volume;
    }

    public void setVolume(int volume) {
        Volume = volume;
    }

    public String getVolumeUOM() {
        return VolumeUOM;
    }

    public void setVolumeUOM(String volumeUOM) {
        VolumeUOM = volumeUOM;
    }

    public String getProductLine() {
        return ProductLine;
    }

    public void setProductLine(String productLine) {
        ProductLine = productLine;
    }

    public String getAmountInPackage() {
        return AmountInPackage;
    }

    public void setAmountInPackage(String amountInPackage) {
        AmountInPackage = amountInPackage;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }

    public String getIngridients() {
        return Ingridients;
    }

    public void setIngridients(String ingridients) {
        Ingridients = ingridients;
    }

    public String getFoodEnergyValue() {
        return FoodEnergyValue;
    }

    public void setFoodEnergyValue(String foodEnergyValue) {
        FoodEnergyValue = foodEnergyValue;
    }

    public String getAlcoholStrength() {
        return AlcoholStrength;
    }

    public void setAlcoholStrength(String alcoholStrength) {
        AlcoholStrength = alcoholStrength;
    }

    public String getVendorCode() {
        return VendorCode;
    }

    public void setVendorCode(String vendorCode) {
        VendorCode = vendorCode;
    }

    public boolean isWeightProduct() {
        return IsWeightProduct;
    }

    public void setWeightProduct(boolean weightProduct) {
        IsWeightProduct = weightProduct;
    }
}
