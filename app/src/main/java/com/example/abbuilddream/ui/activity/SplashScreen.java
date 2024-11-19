package com.example.abbuilddream.ui.activity;

import static com.example.abbuilddream.network.RetrofitClient.baseUrl;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.abbuilddream.R;
import com.example.abbuilddream.adaptor.DashBoardAdapter;
import com.example.abbuilddream.model.Product;
import com.example.abbuilddream.network.RetroInterface;
import com.example.abbuilddream.network.RetrofitClient;
import com.example.abbuilddream.utility.GeneralMethods;
import com.example.abbuilddream.utility.SessionManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    private List<Product> productList;
    TextView subMottoTextView;
    ImageView logoImageView;
    SessionManager sessionManager;


    @Override
    protected void onStart() {
        super.onStart();
        sessionManager = new SessionManager(this);
        fetchProducts();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        GeneralMethods.hideSystemUI(this);

        logoImageView = findViewById(R.id.logoImageView);
        subMottoTextView = findViewById(R.id.projectLineTxt);

        // Load animations
        Animation zoomInAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        Animation bottomToTopAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);

        // Start animations
        logoImageView.startAnimation(zoomInAnimation);
        subMottoTextView.startAnimation(bottomToTopAnimation);

        // Delay to match total animation duration
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreen.this, WelcomeScreen.class));
            finish();
        }, 3000); // Adjust this if needed based on your total animation duration

    }


    private void fetchProducts() {
        RetroInterface apiService = RetrofitClient.getClient(baseUrl).create(RetroInterface.class);
        Call<List<Product>> call = apiService.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productList = response.body();

                    Log.d("productList", "Product List in splash "+productList.toString());
                    // Create and set the adapter for RecyclerView
                    if(productList != null)
                    {
                        sessionManager.filterDataCategory(productList);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {

            }
        });
    }
}
