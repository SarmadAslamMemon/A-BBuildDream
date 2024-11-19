package com.example.abbuilddream.ui.activity.fragments;

import static com.example.abbuilddream.adaptor.AddToCartAdapter.totalSum;
import static com.example.abbuilddream.utility.SessionManager.KEY_ADDED_TO_CART_PRODUCTS;
import static com.example.abbuilddream.utility.SessionManager.sharedPreferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.abbuilddream.R;
import com.example.abbuilddream.adaptor.AddToCartAdapter;
import com.example.abbuilddream.model.ProductOrder;
import com.example.abbuilddream.ui.activity.CheckOutActivity;
import com.example.abbuilddream.utility.CartUpdateListener;
import com.example.abbuilddream.utility.SessionManager;
import com.example.abbuilddream.utility.ShareViewModel;

import java.util.List;
import java.util.Objects;

public class cart_fragment extends Fragment implements  CartUpdateListener {


    RecyclerView orderRecyclerView;
    SessionManager sessionManager;
    Button button_proceed_to_checkout;
    TextView noItem,totalAmountTextView;
    CardView cardView;
    ShareViewModel viewModel;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.fragment_cart_fragment, container, false);
        sessionManager=new SessionManager(requireContext());
        viewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);
        sharedPreferenceClickListener();

        getViews(view);
        if(sessionManager.getCartItems().isEmpty())
        {
            noItem.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.GONE);
        }




        List<ProductOrder> productOrders = sessionManager.getAddedToCartProducts();
        double totalPrice = 0.0;
        for (ProductOrder productOrder : productOrders) {
            totalPrice += productOrder.getTotalPrice();
        }
        totalAmountTextView.setText(String.valueOf(totalPrice));


        button_proceed_to_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.homeFrameLayout, new BillingAddressFragment())
                        .addToBackStack(null)
                        .commit();

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void getViews(View view) {
        button_proceed_to_checkout=view.findViewById(R.id.button_proceed_to_checkout);
        orderRecyclerView=view.findViewById(R.id.orderedProductRecycler);
        cardView=view.findViewById(R.id.card_view_total);
        noItem=view.findViewById(R.id.text_no_items);
        totalAmountTextView=view.findViewById(R.id.totalPriceTextViewCH);
        totalAmountTextView.setText(String.valueOf(sessionManager.getTotalAmount()));
        AddToCartAdapter adapter=new AddToCartAdapter(requireContext(),sessionManager.getCartItems(),viewModel,this);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        orderRecyclerView.setAdapter(adapter);


    }


    public void refreshFragment()
    {
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.homeFrameLayout, new cart_fragment());
        fragmentTransaction.commit();
    }

    private void sharedPreferenceClickListener() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        preferenceChangeListener = (sharedPreferences, key) -> {
            if (KEY_ADDED_TO_CART_PRODUCTS.equals(key)) {
                Log.w("jarvis", "sharedPreferenceClickListener: " + key+ " value "+ sharedPreferences);
                int updatedValue = sharedPreferences.getInt(key, 0);
                totalAmountTextView.setText(String.valueOf(updatedValue)); // Update the UI
            }
        };
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener);
    }


    @Override
    public void increaseItem(int totalItems, double totalPrice)
    {
        double existingAmount= Double.parseDouble(totalAmountTextView.getText().toString());
        Log.w("jarvis", "onCartUpdated: " + totalItems + " " + totalPrice);
        totalAmountTextView.setText(String.valueOf(existingAmount+totalPrice));


    }

    @Override
    public void decreaseItem(int totalItems, double totalPrice) {
        double existingAmount= Double.parseDouble(totalAmountTextView.getText().toString());
        Log.w("jarvis", "onCartUpdated: " + totalItems + " " + totalPrice);
        totalAmountTextView.setText(String.valueOf(existingAmount-totalPrice));
    }

    @Override
    public void refreshTheFragment() {
        Fragment newFragment = new cart_fragment(); // Replace with your fragment class

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.homeFrameLayout, newFragment)
                .commitNow(); // Instant replacement
    }
}