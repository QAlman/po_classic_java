package web.objs.model;

public class CartSkuItemPostModel {
    /**
     * TODO: Это базовый объект - на него стоит сделать билдер
     */
    private String SkuCode;
    private String SkuName;
    private int Quantity;
    private boolean IsChecked;
    private int Order;
    private boolean Manual;

    public CartSkuItemPostModel (int order, String skuName){
        setOrder(order);
        setSkuName(skuName);
        setQuantity(1);
        setChecked(false);
        setManual(true);
    }
    public CartSkuItemPostModel (int order, String skuName,String skuCode){
        setOrder(order);
        setSkuName(skuName);
        setQuantity(1);
        setChecked(false);
        setManual(true);
        setSkuCode(skuCode);
    }

    public String getSkuCode() {
        return SkuCode;
    }

    public void setSkuCode(String skuCode) {
        SkuCode = skuCode;
    }

    public String getSkuName() {
        return SkuName;
    }

    public void setSkuName(String skuName) {
        SkuName = skuName;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public boolean isChecked() {
        return IsChecked;
    }

    public void setChecked(boolean checked) {
        IsChecked = checked;
    }

    public int getOrder() {
        return Order;
    }

    public void setOrder(int order) {
        Order = order;
    }

    public boolean isManual() {
        return Manual;
    }

    public void setManual(boolean manual) {
        Manual = manual;
    }
}
