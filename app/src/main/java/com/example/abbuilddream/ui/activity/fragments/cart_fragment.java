package com.example.abbuilddream.ui.activity.fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.abbuilddream.R;
import com.example.abbuilddream.adaptor.AddToCartAdapter;
import com.example.abbuilddream.utility.SessionManager;

public class cart_fragment extends Fragment {


    RecyclerView orderRecyclerView;
    SessionManager sessionManager;
    Button button_proceed_to_checkout;
    TextView noItem;
    CardView cardView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.fragment_cart_fragment, container, false);
        sessionManager=new SessionManager(requireContext());
        getViews(view);
        if(sessionManager.getCartItems().isEmpty())
        {
            noItem.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.GONE);
        }



        button_proceed_to_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.clearCart();
                noItem.setVisibility(View.VISIBLE);
                cardView.setVisibility(View.GONE);
                refreshFragment();
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
        AddToCartAdapter adapter=new AddToCartAdapter(requireContext(),sessionManager.getCartItems());
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        orderRecyclerView.setAdapter(adapter);


    }


    public void refreshFragment()
    {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.homeFrameLayout, new cart_fragment());
        fragmentTransaction.commit();


    }





}