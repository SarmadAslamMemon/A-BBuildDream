package com.example.abbuilddream.ui.activity.fragments;

import static com.example.abbuilddream.api.RetrofitClient.baseUrl;
import static com.example.abbuilddream.utility.GeneralMethods.showSpinner;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.abbuilddream.R;
import com.example.abbuilddream.adaptor.DashBoardAdapter;
import com.example.abbuilddream.api.RetroInterface;
import com.example.abbuilddream.api.RetrofitClient;
import com.example.abbuilddream.model.Product;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class dashboard_fragment extends Fragment {

    RecyclerView recyclerView;
    ViewPager viewPager_dashboard;
    ProgressBar progressBar;
    CardView cardBridge,cardFinishing,cardSolar,cardExterior,cardInterior ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard_fragment, container, false);

        findViewByIds(view);
        fetchProducts();
        showSpinner(progressBar);
        return view;
    }




    private void findViewByIds(View view) {

        recyclerView = view.findViewById(R.id.productRecyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        setCardViews(view);






}

    private void setCardViews(View view) {
        // Initialize card references
        CardView cardBridge = view.findViewById(R.id.structureCardView);
        CardView cardFinishing = view.findViewById(R.id.finishingCardView);
        CardView cardSolar = view.findViewById(R.id.solarCardView);
        CardView cardExterior = view.findViewById(R.id.exterriorCardView);
        CardView cardInterior = view.findViewById(R.id.interiorCardView);

        // Store all cards in an array for easy management
        CardView[] cards = {cardBridge, cardFinishing, cardSolar, cardExterior, cardInterior};

        // Define the click listener with animations
        View.OnClickListener cardClickListener = v -> {
            // Reset the background and scale for all cards
            for (CardView card : cards) {
                card.setBackgroundResource(R.drawable.defult_cat_card_bg); // Default background
                card.animate().scaleX(1f).scaleY(1f).setDuration(200).start(); // Reset scale
            }

            // Set the selected background and apply scale animation
            v.setBackgroundResource(R.drawable.circle_selected_bg); // Highlight background
            v.animate().scaleX(1.1f).scaleY(1.1f).setDuration(200).start(); // Scale up animation
        };

        // Set the click listener on each card
        for (CardView card : cards) {
            card.setOnClickListener(cardClickListener);
        }
    }


    private void fetchProducts() {
        RetroInterface apiService = RetrofitClient.getClient(baseUrl).create(RetroInterface.class);
        Call<List<Product>> call = apiService.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    progressBar.setVisibility(View.GONE);
                    List<Product> products = response.body();

                    DashBoardAdapter dashBoardAdapter=new DashBoardAdapter(getContext(),products);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    recyclerView.setAdapter(dashBoardAdapter);
                    Log.d("jarvis", "Products received: ");
                } else {
                    Snackbar.make(requireView(), "Server not responding", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                // Handle failure
                progressBar.setVisibility(View.GONE);
                Snackbar.make(requireView(), "Server not responding", Snackbar.LENGTH_SHORT).show();
            }
        });
    }



}