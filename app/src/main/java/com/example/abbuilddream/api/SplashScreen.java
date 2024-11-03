package com.example.abbuilddream.api;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.abbuilddream.R;
import com.example.abbuilddream.ui.activity.WelcomeScreen;

public class SplashScreen extends AppCompatActivity {

    private LottieAnimationView lottieAnimationView;
    TextView mottoTextView,subMottoTextView;
    ImageView logoImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        EdgeToEdge.enable(this);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        logoImageView=findViewById(R.id.logoImageView);
        subMottoTextView=findViewById(R.id.projectLineTxt);

        logoImageView.setAnimation(AnimationUtils.loadAnimation(this,R.anim.zoom_in));
        subMottoTextView.setAnimation(AnimationUtils.loadAnimation(this,R.anim.bottom_to_top));

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreen.this, WelcomeScreen.class));
            finish();
        },3000);





    }

}