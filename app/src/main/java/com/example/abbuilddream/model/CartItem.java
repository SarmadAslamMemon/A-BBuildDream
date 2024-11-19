package com.example.abbuilddream.model;


    public class CartItem {
        private int productDetailId;
        private int count;
        private double totalPrice;



        public CartItem(int productDetailId, int count, double totalPrice) {
            this.productDetailId = productDetailId;
            this.count = count;
            this.totalPrice = totalPrice;
        }

        // Getters and Setters
        public int getProductDetailId() {
            return productDetailId;
        }

        public void setProductDetailId(int productDetailId) {
            this.productDetailId = productDetailId;
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

        @Override
        public String toString() {
            return "CartItem{" +
                    "productDetailId=" + productDetailId +
                    ", count=" + count +
                    ", totalPrice=" + totalPrice +
                    '}';
        }
    }
