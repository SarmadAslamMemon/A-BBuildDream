package com.example.abbuilddream.utility;

public interface CartUpdateListener {
    void increaseItem(int totalItems, double totalPrice);
    void decreaseItem(int totalItems, double totalPrice);
    void refreshTheFragment();
}

