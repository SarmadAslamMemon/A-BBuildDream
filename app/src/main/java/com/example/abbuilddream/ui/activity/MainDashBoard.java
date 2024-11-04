package com.example.abbuilddream.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.abbuilddream.R;
import com.example.abbuilddream.ui.activity.fragments.cart_fragment;
import com.example.abbuilddream.ui.activity.fragments.dashboard_fragment;
import com.example.abbuilddream.ui.activity.fragments.product_detail_fragment;
import com.example.abbuilddream.ui.activity.fragments.profile_fragment;
import com.example.abbuilddream.ui.activity.fragments.setting_fragment;
import com.example.abbuilddream.utility.GeneralMethods;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainDashBoard extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);



        initializeViews();
        setInitialFragment();
        setupBottomNavListener();
    }

    private void initializeViews() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    private void setInitialFragment() {
        loadFragment(new dashboard_fragment());
    }

    private void setupBottomNavListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.home) {
                selectedFragment = new dashboard_fragment();
            } else if (item.getItemId() == R.id.catagory) {
                selectedFragment = new setting_fragment(); // Adjust if this represents the category section
            } else if (item.getItemId() == R.id.cart) {
                selectedFragment = new cart_fragment();
            } else if (item.getItemId() == R.id.profile) {
                selectedFragment = new profile_fragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                return true;
            }
            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
        );

        if (fragment instanceof dashboard_fragment) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        transaction.replace(R.id.homeFrameLayout, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.homeFrameLayout);
        if (currentFragment instanceof dashboard_fragment) {
            super.onBackPressed();
        } else if (currentFragment instanceof product_detail_fragment) {
            bottomNavigationView.setVisibility(View.VISIBLE);
            loadFragment(new dashboard_fragment());
        } else {
            loadFragment(new dashboard_fragment());
        }
    }
}
