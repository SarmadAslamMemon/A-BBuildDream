package com.example.abbuilddream.ui.activity;

import static android.content.ContentValues.TAG;

import static com.example.abbuilddream.api.RetrofitClient.baseUrl;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.abbuilddream.R;
import com.example.abbuilddream.adaptor.DashBoardAdapter;
import com.example.abbuilddream.api.RetroInterface;
import com.example.abbuilddream.api.RetrofitClient;
import com.example.abbuilddream.model.Product;
import com.example.abbuilddream.ui.activity.fragments.cart_fragment;
import com.example.abbuilddream.ui.activity.fragments.dashboard_fragment;
import com.example.abbuilddream.ui.activity.fragments.profile_fragment;
import com.example.abbuilddream.ui.activity.fragments.setting_fragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainDashBoard extends AppCompatActivity {

    private ImageView home_icon, setting_icon, cart_icon, profile_icon;
    LinearLayout bottom_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setInitialFragment();
        setupIconClickListeners();
    }

    private void initializeViews() {
        home_icon = findViewById(R.id.home_icon);
        cart_icon = findViewById(R.id.cart_icon);
        profile_icon = findViewById(R.id.profile_icon);
        bottom_nav = findViewById(R.id.bottom_nav);
    }

    private void setInitialFragment() {
        loadFragment(new dashboard_fragment());
    }

    private void setupIconClickListeners() {
        home_icon.setOnClickListener(v -> {

            if(getSupportFragmentManager().findFragmentById(R.id.homeFrameLayout) instanceof dashboard_fragment) {
                return;
            }else
            {
                loadFragment(new dashboard_fragment());
            }
        });

        cart_icon.setOnClickListener(v -> loadFragment(new cart_fragment()));

        profile_icon.setOnClickListener(v -> loadFragment(new profile_fragment()));
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Set custom animations
        transaction.setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
        );

        // Check if the fragment is the dashboard_fragment
        if (fragment instanceof dashboard_fragment) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        transaction.replace(R.id.homeFrameLayout, fragment);
        transaction.commit();


        if(fragment instanceof cart_fragment)
        {
            bottom_nav.setVisibility(View.GONE);
        }
        else
        {
            bottom_nav.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {

        // Check if the current fragment is not the dashboard_fragment
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.homeFrameLayout);
        if (currentFragment instanceof dashboard_fragment) {
            super.onBackPressed();
        } else {
            loadFragment(new dashboard_fragment());
        }
    }



}
