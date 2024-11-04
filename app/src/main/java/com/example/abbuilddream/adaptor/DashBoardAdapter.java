package com.example.abbuilddream.adaptor;

import static com.example.abbuilddream.api.RetrofitClient.baseUrl;

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
import com.example.abbuilddream.api.RetroInterface;
import com.example.abbuilddream.api.RetrofitClient;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardAdapter extends RecyclerView.Adapter<DashBoardAdapter.DashBoardViewHolder> {

    private final Context context;
    private final List<Product> productList;
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
                Snackbar.make(v, "Product added to Cart", Snackbar.LENGTH_SHORT).show();
                getProductDetails(product);
                Log.w("jarvis", "onClick: " + product.getName());
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

        Order order = new Order();
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem((product.getId()), 1, product.getPrice()));
        order.setCart(cartItems);
        order.setFirstName("Test");
        order.setLastName("Test");
        order.setAddress("Test");
        order.setCity("Test");
        order.setMobileNumber("Test");
        order.setPassword("Test");
        makeOrder(order);
        sessionManager=new SessionManager(context);
        sessionManager.addCartItem(product);






    }

    private void makeOrder(Order order) {

        RetroInterface apiService = RetrofitClient.getClient(baseUrl).create(RetroInterface.class);
        Call<AddOrderResponse> createData = apiService.createData(order);
        createData.enqueue(new Callback<AddOrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<AddOrderResponse> call, @NonNull Response<AddOrderResponse> response) {
                if (response.isSuccessful()) {

                    AddOrderResponse addOrderResponse = response.body();
                    Log.w("jarvis", "onResponse: " + addOrderResponse.getResult().getMessage());

                }
            }

            @Override
            public void onFailure(@NonNull Call<AddOrderResponse> call, @NonNull Throwable t) {

                Log.w("jarvis", "onFailure: " + t.getMessage());
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // ViewHolder class to hold references to each item's views
    public static class DashBoardViewHolder extends RecyclerView.ViewHolder {

        TextView productCardNameTextView, productCardPriceTextView;
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
