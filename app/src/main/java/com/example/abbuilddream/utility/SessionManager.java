package com.example.abbuilddream.utility;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.abbuilddream.model.Product; // Ensure you have the Product model defined
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SessionManager {
    private static final String PREFS_NAME = "AppPrefs";
    private static final String KEY_CART_ITEMS = "cart_items"; // Change the key to reflect cart items
    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    public SessionManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    // Method to save cart items in session storage
    public void saveCartItems(List<Product> cartItems) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(cartItems);
        editor.putString(KEY_CART_ITEMS, json);
        editor.apply();
    }

    // Method to retrieve the list of cart items from session storage
    public List<Product> getCartItems() {
        String json = sharedPreferences.getString(KEY_CART_ITEMS, null);
        Type type = new TypeToken<ArrayList<Product>>() {}.getType();
        return json != null ? gson.fromJson(json, type) : new ArrayList<>();
    }

    // Method to add a cart item to the session
    public void addCartItem(Product product) {
        List<Product> cartItems = getCartItems(); // Retrieve existing cart items
        cartItems.add(product); // Add the new product
        saveCartItems(cartItems); // Save the updated list back to session storage
    }

    // Method to clear all cart items
    public void clearCart() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_CART_ITEMS);
        editor.apply();
    }
}
