package com.example.abbuilddream.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.abbuilddream.model.Product; // Ensure you have the Product model defined
import com.example.abbuilddream.model.ProductOrder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SessionManager {
    private static final String PREFS_NAME = "AppPrefs";
    private static final String KEY_CART_ITEMS = "cart_items";
    private static  final String KEY_CAT_STRUCTURE="structure";
    private static  final String KEY_CAT_FINISHING="finishing";
    private static  final String KEY_CAT_SOLAR="solar";
    private static  final String KEY_CAT_EXTERIOR="exterior";
    private static  final String KEY_CAT_INTERIOR="interior";
    private static final String KEY_ALL_PRODUCTS="allProducts";
    public static final String KEY_ADDED_TO_CART_PRODUCTS="addedToCartProducts";



    // Change the key to reflect cart items
    public static SharedPreferences sharedPreferences;
    public static Gson gson;

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
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
    public void removeCartItemById(int id) {
        List<Product> cartItems = getCartItems();
        Log.d("jarvis", "Before cartItems: " + cartItems.size());
        cartItems.removeIf(product -> id == product.getId());
        sharedPreferences.edit()
                .putString(KEY_CART_ITEMS, gson.toJson(cartItems))
                .apply();
        Log.d("jarvis", "After cartItems: " + cartItems.size());
    }

    public void totalAmountCount(int count,double amount)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("count",count);
        editor.putFloat("amount", (float) amount);
        editor.apply();
    }
    public void reduceTotalCount(int count , double amount)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int existingCount = sharedPreferences.getInt("count", 0);
        editor.putInt("count", existingCount - count);
        float existingAmount = sharedPreferences.getFloat("amount", 0f);
        editor.putFloat("amount", existingAmount - (float) amount);
        editor.apply();
    }

//    public int getTotalCount() {
//        return sharedPreferences.getInt("count", 0);
//    }
    public double getTotalAmount() {
        return sharedPreferences.getFloat("amount", 0f);
    }

    // Method to add a cart item to the session
    public void addCartItem(Product product) {
        List<Product> cartItems = getCartItems(); // Retrieve existing cart items

        if (cartItems.isEmpty()) {
            // If the cart is empty, add the product and save
            cartItems.add(product);
            saveCartItems(cartItems);
            Log.w("jarvis", "addCartItem: " + cartItems.size());
            return;
        } else {
            boolean productExists = false;

            // Iterate over the cart items to check if the product already exists
            for (Product item : new ArrayList<>(cartItems)) { // Use a copy to avoid modification issues
                Log.w("jarvis", "CartItem: " + item.getId() + " product id " + product.getId());
                if (Objects.equals(item.getId(), product.getId())) {
                    productExists = true;
                    break; // Exit loop if product is found
                }
            }

            if (!productExists) {
                // Add the product if it doesn't already exist in the cart
                cartItems.add(product);
                saveCartItems(cartItems);
                Log.w("jarvis", "addCartItem: " + cartItems.size());
            }
        }
    }



    public void saveCart(List<ProductOrder> cart) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(cart);
        editor.putString(KEY_ADDED_TO_CART_PRODUCTS, json);
        editor.apply();
        Log.d("ProductDetails", "Cart saved. Size: " + cart.size());
    }

    public void removeCartItem(int id)
    {
        List<ProductOrder> addedToCartProducts = getAddedToCartProducts();
        Log.d("ProductDetails", "Before addedToCartProducts: " + addedToCartProducts.size());
        addedToCartProducts.removeIf(productOrder -> productOrder.getProductId() == id);
        sharedPreferences.edit()
                .putString(KEY_ADDED_TO_CART_PRODUCTS, gson.toJson(addedToCartProducts))
                .apply();

        Log.d("ProductDetails", "After addedToCartProducts: " + addedToCartProducts.size());

    }


    public List<ProductOrder> getAddedToCartProducts()
    {
        String json = sharedPreferences.getString(KEY_ADDED_TO_CART_PRODUCTS, null);
        Type type = new TypeToken<ArrayList<ProductOrder>>() {}.getType();
        return json != null ? gson.fromJson(json, type) : new ArrayList<>();
    }



    // Method to clear all cart items
    public void clearCart() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_CART_ITEMS);
        editor.remove(KEY_ADDED_TO_CART_PRODUCTS);
        editor.apply();
    }

    public int getCountOfAddedProduct(int id) {
        List<ProductOrder> addedToCartProducts = getAddedToCartProducts();

        if (addedToCartProducts != null) {
            for (ProductOrder productOrder : addedToCartProducts) {
                if (productOrder.getProductId() == id) {
                    // Product found, return its count
                    return productOrder.getCount();
                }
            }
        }
        // Product not found or cart is empty, return 0
        return 0;
    }


    public static void saveStructureItems(List<Product> structureItems)
    {

        Log.d("jarvis", "Saving structure items: " + structureItems.size());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(structureItems);
        editor.putString(KEY_CAT_STRUCTURE, json);
        editor.apply();
    }

    public static List<Product> getStructureItems()
    {
        String json = sharedPreferences.getString(KEY_CAT_STRUCTURE, null);
        Type type = new TypeToken<ArrayList<Product>>() {}.getType();
        return json != null ? gson.fromJson(json, type) : new ArrayList<>();

    }

    public static void saveFinishingItems(List<Product> finishingItems)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(finishingItems);
        editor.putString(KEY_CAT_FINISHING, json);
        editor.apply();

    }

    public static List<Product> getFinishingItems(){
        String json = sharedPreferences.getString(KEY_CAT_FINISHING, null);
        Type type = new TypeToken<ArrayList<Product>>() {}.getType();
        return json != null ? gson.fromJson(json, type) : new ArrayList<>();

    }



    public static void saveSolarItems(List<Product> solarItems)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(solarItems);
        editor.putString(KEY_CAT_SOLAR, json);
        editor.apply();

    }

    public static List<Product> getSolarItems()
    {
        String json = sharedPreferences.getString(KEY_CAT_SOLAR, null);
        Type type = new TypeToken<ArrayList<Product>>() {}.getType();
        return json != null ? gson.fromJson(json, type) : new ArrayList<>();
    }

    public static void saveExteriorItems(List<Product> exteriorItems) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(exteriorItems);
        editor.putString(KEY_CAT_EXTERIOR, json);
        editor.apply();

    }

    public static List<Product> getExteriorItems() {
        String json = sharedPreferences.getString(KEY_CAT_EXTERIOR, null);
        Type type = new TypeToken<ArrayList<Product>>() {}.getType();
        return json != null ? gson.fromJson(json, type) : new ArrayList<>();

    }


    public static void saveInteriorItems(List<Product> interiorItems) {
        Log.d("jarvis", "Saving interior items: " + interiorItems.size());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(interiorItems);
        editor.putString(KEY_CAT_INTERIOR, json);
        editor.apply();
    }

    public static List<Product> getInteriorItems() {
        String json = sharedPreferences.getString(KEY_CAT_INTERIOR, null);
        Type type = new TypeToken<ArrayList<Product>>() {}.getType();
        return json != null ? gson.fromJson(json, type) : new ArrayList<>();
    }


    public static void saveAllProducts(List<Product> productList)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(productList);
        editor.putString(KEY_ALL_PRODUCTS, json);
        editor.apply();

    }

    public static List<Product> getAllProducts()
        {

            String json = sharedPreferences.getString(KEY_ALL_PRODUCTS, null);
            Type type = new TypeToken<ArrayList<Product>>() {}.getType();
            return json != null ? gson.fromJson(json, type) : new ArrayList<>();

        }

    public void filterDataCategory(List<Product> productList)
    {
        saveAllProducts(productList);
        List<Product> structureProdcutList = new ArrayList<>();
        List<Product> finishingProdcutList = new ArrayList<>();
        List<Product> solarProdcutList = new ArrayList<>();
        List<Product> exteriorProdcutList = new ArrayList<>();
        List<Product> interiorProdcutList = new ArrayList<>();

        for(Product product:productList)
        {

            switch (product.getCategory()) {

                case "Structure Items":
                    structureProdcutList.add(product);
                    break;
                case "Finishing Items":
                    finishingProdcutList.add(product);
                    break;
                case "Solar Items":
                    solarProdcutList.add(product);
                    break;
                case "Exterior Items":
                    exteriorProdcutList.add(product);
                    break;
                case "Interior Items":
                    interiorProdcutList.add(product);
                    break;
            }

        }

        saveStructureItems(structureProdcutList);
        saveFinishingItems(finishingProdcutList);
        saveSolarItems(solarProdcutList);
        saveExteriorItems(exteriorProdcutList);
        saveInteriorItems(interiorProdcutList);


    }


    public int getTotalCount()
    {
        List<ProductOrder> addedToCartProducts = getAddedToCartProducts();
        int totalCount = 0;
        for (ProductOrder productOrder : addedToCartProducts) {
            totalCount += productOrder.getCount();
        }
        return totalCount;
    }


    public void saveOrderName(String orderName)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("orderName", orderName);
        editor.apply();
    }

    public String getOrderName()
    {
        return sharedPreferences.getString("orderName", "");
    }

    public boolean getIsOrderDone()
    {
        return sharedPreferences.getBoolean("isOrderDone", true);
    }

    public void setIsOrderDone(boolean isOrderDone) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isOrderDone", isOrderDone);
        editor.apply();
    }

    public void saveIncrementer(int incrementer) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("incrementer", 1);
        editor.apply();
    }
    public int getIncrementer() {
        return sharedPreferences.getInt("incrementer", 0)+1;
    }

    public void savePaymentId(int paymentId)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("paymentId", paymentId);
        editor.apply();

    }

    public int getPaymentId()
    {
        return sharedPreferences.getInt("paymentId", 0);
    }


    public void clearAfterOrder()
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("orderName");
        editor.remove("isOrderDone");
        editor.remove("incrementer");
        editor.remove("paymentId");
        editor.remove("count");
        editor.remove("amount");
        editor.remove(KEY_ADDED_TO_CART_PRODUCTS);
        editor.remove(KEY_CART_ITEMS);
        editor.apply();
    }




//    public void increaseQuatity() {
//        int currentQuantity = Integer.parseInt(productQuantity.getText().toString());
//        productQuantity.setText(String.valueOf(currentQuantity + 1));
//    }
}
