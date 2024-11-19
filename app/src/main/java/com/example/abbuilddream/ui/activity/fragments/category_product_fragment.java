package com.example.abbuilddream.ui.activity.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.SystemBarStyle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abbuilddream.R;
import com.example.abbuilddream.adaptor.DashBoardAdapter;
import com.example.abbuilddream.model.Product;
import com.example.abbuilddream.utility.GeneralMethods;
import com.example.abbuilddream.utility.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class category_product_fragment extends Fragment {

    TextView titleTextView;
    RecyclerView recyclerViewProductCategory;
    SessionManager sessionManager;
    private String categoryTitle;
    ImageView backButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_product_fragment, container, false);
        sessionManager = new SessionManager(requireContext());
        view.setFitsSystemWindows(true);
        GeneralMethods.showSystemUI(requireActivity());

        getViews(view);
        getBundleData();
        setProductsToRecyclerView();



        return view;
    }




    private void setProductsToRecyclerView() {
        List<Product> productList = new ArrayList<>();
        productList = getProductFilteredList();
        DashBoardAdapter dashBoardAdapter = new DashBoardAdapter(getContext(), productList);
        recyclerViewProductCategory.setLayoutManager(new GridLayoutManager(getContext(), 2));;
        recyclerViewProductCategory.setAdapter(dashBoardAdapter);

    }

    private List<Product> getProductFilteredList() {
        if (categoryTitle != null) {
            switch (categoryTitle) {
                case "Structure":
                    Log.d("jarvis","size" + SessionManager.getStructureItems().size());
                    return SessionManager.getStructureItems();

                case "Finishing":
                    Log.d("jarvis","size" + SessionManager.getFinishingItems().size());
                    return SessionManager.getFinishingItems();

                case "Solar":
                    return SessionManager.getSolarItems();

                case "Exterior":
                    return SessionManager.getExteriorItems();

                case "Interior":
                    Log.d("jarvis","size" + SessionManager.getInteriorItems().size());
                    return SessionManager.getInteriorItems();
                default:
                    return null;
            }
        }else
        {
            return null;
        }

    }

    private void getBundleData()
    {
        Bundle bundle = getArguments();
        if (bundle != null) {
            categoryTitle = bundle.getString("category");
            titleTextView.setText(categoryTitle);
        }
    }

    private void getViews(View view) {
        titleTextView = view.findViewById(R.id.titleTextViewPC);
        recyclerViewProductCategory = view.findViewById(R.id.recyclerViewPC);
        backButton = view.findViewById(R.id.backButtonPC);
        backButton.setOnClickListener(v -> requireActivity().onBackPressed());

    }
}