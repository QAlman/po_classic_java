package web.objs.model;

import java.util.List;

public class CartSkusPostModel {
    private List<CartSkuItemPostModel> skus;
    private List<CartSkuItemPostModel> cart;

    public List<CartSkuItemPostModel> getSkus() {
        return skus;
    }

    public void setSkus(List<CartSkuItemPostModel> skus) {
        this.skus = skus;
    }

    public List<CartSkuItemPostModel> getCart() {
        return cart;
    }

    public void setCart(List<CartSkuItemPostModel> cart) {
        this.cart = cart;
    }
}
