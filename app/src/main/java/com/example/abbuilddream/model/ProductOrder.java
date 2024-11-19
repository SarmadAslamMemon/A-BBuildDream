package com.example.abbuilddream.model;

import androidx.annotation.NonNull;

public class ProductOrder {

    int productId;
    int count;
    double totalPrice;

    public ProductOrder(int productId, int count, double totalPrice) {
        this.productId = productId;
        this.count = count;
        this.totalPrice = totalPrice;
    }

    public ProductOrder() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @NonNull
    @Override
    public String toString() {
        return "ProductOrder{" +
                "productId=" + productId +
                ", count=" + count +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
