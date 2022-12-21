package web.objs.model;

import java.util.List;

public class SkuPricesModel {
    private String SkuCode;

    private List<PriceValidityModel> PriceValidityItems;

    public SkuPricesModel (String skuCode, List<PriceValidityModel> priceValidityItems){
        this.PriceValidityItems = priceValidityItems;
        this.SkuCode = skuCode;
    }

    public String getSkuCode() {
        return SkuCode;
    }

    public void setSkuCode(String skuCode) {
        SkuCode = skuCode;
    }

    public List<PriceValidityModel> getPriceValidityItems() {
        return PriceValidityItems;
    }

    public void setPriceValidityItems(List<PriceValidityModel> priceValidityItems) {
        PriceValidityItems = priceValidityItems;
    }
}
