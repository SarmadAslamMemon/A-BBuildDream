package com.example.abbuilddream.adaptor;

import static com.example.abbuilddream.network.RetrofitClient.baseUrl;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide; // For loading images
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.abbuilddream.R;
import com.example.abbuilddream.model.ProductOrder;
import com.example.abbuilddream.network.RetroInterface;
import com.example.abbuilddream.network.RetrofitClient;
import com.example.abbuilddream.model.AddOrderResponse;
import com.example.abbuilddream.model.CartItem;
import com.example.abbuilddream.model.Order;
import com.example.abbuilddream.model.Product;
import com.example.abbuilddream.ui.activity.MainDashBoard;
import com.example.abbuilddream.ui.activity.fragments.product_detail_fragment;
import com.example.abbuilddream.utility.SessionManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardAdapter extends RecyclerView.Adapter<DashBoardAdapter.DashBoardViewHolder> {

    private final Context context;
    private List<Product> productList;
    com.example.abbuilddream.utility.SessionManager sessionManager;

    // Constructor to initialize context and product list
    public DashBoardAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public DashBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(context).inflate(R.layout.product_item_layout, parent, false);
        return new DashBoardViewHolder(view);
    }

    public void updateProductList(List<Product> newProductList) {
        this.productList = newProductList;
        notifyDataSetChanged(); // Notify adapter to refresh the data
    }
    @Override
    public void onBindViewHolder(@NonNull DashBoardViewHolder holder, int position) {
        // Bind data to each item view
        Product product = productList.get(position);
        holder.productCardNameTextView.setText(product.getName());
        holder.productCardPriceTextView.setText("Rs." + product.getPrice());
        Glide.with(context)
                .load(product.getImage())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.productCardImageView);

        // Set click listener on the whole item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToProductDetailFragment(product);
            }
        });



        holder.cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTickLottie(holder);
                new Handler().postDelayed(() -> {
                    holder.tickAnimation.setVisibility(View.GONE);
                    holder.cartIcon.setVisibility(View.VISIBLE);
                },1700);
                getProductDetails(product);
            }


        });



    }


    private void showTickLottie(DashBoardViewHolder holder) {

        holder.cartIcon.setVisibility(View.GONE);
        holder.tickAnimation.setVisibility(View.VISIBLE);
        holder.tickAnimation.playAnimation();

    }
    private void navigateToProductDetailFragment(Product product) {
        // Create an instance of the target fragment
        product_detail_fragment productDetailFragment = new product_detail_fragment();
        // Create a Bundle and put the product object into it
        Bundle bundle = new Bundle();
        bundle.putSerializable("product", product);
        // Set the bundle as arguments to the fragment
        productDetailFragment.setArguments(bundle);
        // Start the fragment transaction
        FragmentManager fragmentManager = ((MainDashBoard) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.homeFrameLayout, productDetailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }


    private void getProductDetails(Product product) {
        sessionManager = new SessionManager(context);
        // Fetch the current cart from session
        List<ProductOrder> addedToCartProducts = sessionManager.getAddedToCartProducts();
        List<Product> cartItems = sessionManager.getCartItems();

        if (addedToCartProducts == null) {
            addedToCartProducts = new ArrayList<>();
        }

        // Flag to check if the product is already in the cart
        boolean productExists = false;

        // Iterate over the cart to find the product
        for (ProductOrder productOrderItem : addedToCartProducts) {
            if (productOrderItem.getProductId() == product.getId()) {
                // If product exists, increment the count and update the price
                productOrderItem.setCount(productOrderItem.getCount() + 1);
                productOrderItem.setTotalPrice(productOrderItem.getTotalPrice() + product.getPrice());
                productExists = true;
                Log.d("ProductDetails", "Product exists in cart. Updated count: " + productOrderItem.getCount());
                break;
            }
        }

        // If the product is new, add it to the cart
        if (!productExists) {
            ProductOrder newProductOrder = new ProductOrder();
            newProductOrder.setProductId(product.getId());
            newProductOrder.setTotalPrice(product.getPrice());
            newProductOrder.setCount(1);
            addedToCartProducts.add(newProductOrder);
            Log.d("ProductDetails", "New product added to cart. Product ID: " + product.getId());
            cartItems.add(product);
            sessionManager.saveCartItems(cartItems);
        }

        // Save the updated cart back to the session
        sessionManager.saveCart(addedToCartProducts);
        Log.d("ProductDetails", "Cart size after update: " + addedToCartProducts.size());




    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    // ViewHolder class to hold references to each item's views
    public static class DashBoardViewHolder extends RecyclerView.ViewHolder {

        TextView productCardNameTextView, productCardPriceTextView ;
        ImageView productCardImageView, cartIcon;
        LottieAnimationView tickAnimation;


        public DashBoardViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views
            productCardImageView = itemView.findViewById(R.id.productImageRecItem);
            productCardNameTextView = itemView.findViewById(R.id.productTitleRecItem);
            productCardPriceTextView = itemView.findViewById(R.id.productSoldCount);
            cartIcon = itemView.findViewById(R.id.addToCartIconRecItem);
            tickAnimation = itemView.findViewById(R.id.lottieTickAnimation);

        }
    }
}
