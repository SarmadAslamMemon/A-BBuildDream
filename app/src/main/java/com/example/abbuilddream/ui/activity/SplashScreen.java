package com.example.abbuilddream.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.abbuilddream.R;
import com.example.abbuilddream.utility.GeneralMethods;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    TextView subMottoTextView;
    ImageView logoImageView;

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
}
