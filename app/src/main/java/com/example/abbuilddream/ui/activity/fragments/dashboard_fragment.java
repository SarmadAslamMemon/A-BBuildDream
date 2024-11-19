package com.example.abbuilddream.ui.activity.fragments;

import static com.example.abbuilddream.network.RetrofitClient.baseUrl;
import static com.example.abbuilddream.utility.GeneralMethods.showSpinner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.abbuilddream.R;
import com.example.abbuilddream.adaptor.DashBoardAdapter;
import com.example.abbuilddream.network.RetroInterface;
import com.example.abbuilddream.network.RetrofitClient;
import com.example.abbuilddream.model.Product;
import com.example.abbuilddream.utility.GeneralMethods;
import com.example.abbuilddream.utility.SessionManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class dashboard_fragment extends Fragment {

    RecyclerView recyclerView;
    ViewPager viewPager_dashboard;
    ProgressBar progressBar;
    CardView cardBridge, cardFinishing, cardSolar, cardExterior, cardInterior;
    AutoCompleteTextView searchAutoCompleteTextView;
    List<Product> productList;
    List<Product> filteredProductList;
    DashBoardAdapter dashBoardAdapter;
    List<String> productNameList = new ArrayList<>();
    View view;
    SessionManager sessionManager;
    TextView greetingTextMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_dashboard_fragment, container, false);

        View decorView = requireActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);


        findViewByIds(view);
        fetchProducts();

        setSearchAutoCompleteText();
        return view;
    }

    private void findViewByIds(View view) {
        recyclerView = view.findViewById(R.id.productRecyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        greetingTextMessage = view.findViewById(R.id.greetingTextMessage);
        greetingTextMessage.setText(GeneralMethods.getGreetingMessage());

        sessionManager=new SessionManager(requireContext());

        setCardViews(view);
    }

    private void setCardViews(View view) {
        // Initialize card references
        cardBridge = view.findViewById(R.id.structureCardView);
        cardFinishing = view.findViewById(R.id.finishingCardView);
        cardSolar = view.findViewById(R.id.solarCardView);
        cardExterior = view.findViewById(R.id.exterriorCardView);
        cardInterior = view.findViewById(R.id.interiorCardView);

        // Set unique tags for each card to identify categories
        cardBridge.setTag("Structure");
        cardFinishing.setTag("Finishing");
        cardSolar.setTag("Solar");
        cardExterior.setTag("Exterior");
        cardInterior.setTag("Interior");

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

            // Retrieve the category from the tag
            String category = (String) v.getTag();

            // Pass the category to the next fragment
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            category_product_fragment categoryProductFragment = new category_product_fragment();
            Bundle bundle = new Bundle();
            bundle.putString("category", category); // Send category to next fragment
            categoryProductFragment.setArguments(bundle);

            // Log the category for debugging
            Log.d("Category", category);

            fragmentManager.beginTransaction()
                    .replace(R.id.homeFrameLayout, categoryProductFragment)
                    .addToBackStack(null)
                    .commit();
        };

        // Set the click listener on each card
        for (CardView card : cards) {
            card.setOnClickListener(cardClickListener);
        }
    }


    private void fetchProducts() {


                    productList = SessionManager.getAllProducts();
                    productNameList = getProductNameList(productList);
                    Log.d("productList", productList.toString());
                    // Create and set the adapter for RecyclerView
                    dashBoardAdapter = new DashBoardAdapter(getContext(), productList);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    recyclerView.setAdapter(dashBoardAdapter);
    }

    private List<String> getProductNameList(List<Product> products) {
        List<String> names = new ArrayList<>();
        for (Product product : products) {
            names.add(product.getName());
        }
        return names;
    }

    private void setSearchAutoCompleteText() {
        searchAutoCompleteTextView = view.findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(requireContext(),android.R.layout.simple_dropdown_item_1line,productNameList);
        AutoCompleteTextView textView=(AutoCompleteTextView)view.findViewById(R.id.searchAutoCompleteTextView);
        textView.setThreshold(3);
        textView.setAdapter(adapter);
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterData(String query) {
        filteredProductList = new ArrayList<>();

        // Check if the query is empty and show all products, otherwise filter by product name
        if (query.isEmpty()) {
            filteredProductList.addAll(productList);
        } else {
            for (Product item : productList) {
                if (item.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredProductList.add(item);
                }
            }
        }

        dashBoardAdapter.updateProductList(filteredProductList);
    }
}
