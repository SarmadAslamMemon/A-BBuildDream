package com.example.abbuilddream.model;


import androidx.annotation.NonNull;

public class Payment {
        private int id;
        private int userID;
        private String orderDate;
        private int totalItem;
        private double totalCost;
        private String paymentImage;
        private int status;


        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public int getUserID() {
                return userID;
        }

        public void setUserID(int userID) {
                this.userID = userID;
        }

        public String getOrderDate() {
                return orderDate;
        }

        public void setOrderDate(String orderDate) {
                this.orderDate = orderDate;
        }

        public int getTotalItem() {
                return totalItem;
        }

        public void setTotalItem(int totalItem) {
                this.totalItem = totalItem;
        }

        public double getTotalCost() {
                return totalCost;
        }

        public void setTotalCost(double totalCost) {
                this.totalCost = totalCost;
        }

        public String getPaymentImage() {
                return paymentImage;
        }

        public void setPaymentImage(String paymentImage) {
                this.paymentImage = paymentImage;
        }

        public int getStatus() {
                return status;
        }

        public void setStatus(int status) {
                this.status = status;
        }


        @NonNull
        @Override
        public String toString() {
                return "Payment{" +
                        "id=" + id +
                        ", userID=" + userID +
                        ", orderDate='" + orderDate + '\'' +
                        ", totalItem=" + totalItem +
                        ", totalCost=" + totalCost +
                        ", paymentImage='" + paymentImage + '\'' +
                        ", status=" + status +
                        '}';
        }
}
