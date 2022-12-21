package web.objs.model;

public class PriceValidityModel {
    private int Price;
    private int PriceWithCard;
    private String ValidFrom;
    private String  ValidTo;
    // не менять написание ispromo  и isdeleted иначе не будет работать!!
    private boolean ispromo;
    private String PromoId;
    private String PromoType;
    private String PromoTopic;
    // не менять написание ispromo  и isdeleted иначе не будет работать!!
    private boolean isdeleted;

    public PriceValidityModel (int regularPrice, int discountPrice, String from, String to, boolean IsPromo){
        setPrice(regularPrice);
        setPriceWithCard(discountPrice);
        setValidTo(to);
        setValidFrom(from);
        setIspromo(IsPromo);
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getPriceWithCard() {
        return PriceWithCard;
    }

    public void setPriceWithCard(int priceWithCard) {
        PriceWithCard = priceWithCard;
    }

    public String getValidFrom() {
        return ValidFrom;
    }

    public void setValidFrom(String validFrom) {
        ValidFrom = validFrom;
    }

    public String getValidTo() {
        return ValidTo;
    }

    public void setValidTo(String validTo) {
        ValidTo = validTo;
    }

    public boolean isIspromo() {
        return ispromo;
    }

    public void setIspromo(boolean IsPromo) {
        this.ispromo = IsPromo;
    }

    public String getPromoId() {
        return PromoId;
    }

    public void setPromoId(String promoId) {
        PromoId = promoId;
    }


    public String getPromoType() {
        return PromoType;
    }

    public void setPromoType(String promoType) {
        PromoType = promoType;
    }

    public String getPromoTopic() {
        return PromoTopic;
    }

    public void setPromoTopic(String promoTopic) {
        PromoTopic = promoTopic;
    }

    public boolean isIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(boolean isdeleted) {
        this.isdeleted = isdeleted;
    }
}
