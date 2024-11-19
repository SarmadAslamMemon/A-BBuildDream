package com.example.abbuilddream.model;

import androidx.annotation.NonNull;

import java.util.List;

public class Order {
    private List<ProductOrder> cart;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String mobileNumber;
    private String password;
    private String email;

    // Getters and Setters
    public List<ProductOrder> getCart() {
        return cart;
    }

    public void setCart(List<ProductOrder> cart) {
        this.cart = cart;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @NonNull
    @Override
    public String toString() {
        return "Order{" +
                "cart=" + cart.toString() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
