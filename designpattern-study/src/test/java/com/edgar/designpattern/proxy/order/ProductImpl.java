package com.edgar.designpattern.proxy.order;

public class ProductImpl implements Product {
	private String name;
	private int price;
	private String sku;

    public ProductImpl(String name, int price, String sku) {
        this.name = name;
        this.price = price;
        this.sku = sku;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
