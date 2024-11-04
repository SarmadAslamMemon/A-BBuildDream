package com.example.abbuilddream.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.abbuilddream.R;
import com.example.abbuilddream.utility.GeneralMethods;

public class WelcomeScreen extends AppCompatActivity {


    Button btnContinueAsGuest,btnLogin,btnRegistration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_welcome_screen);
        GeneralMethods.hideSystemUI(this);
        createReference();
        btnContinueAsGuest.setOnClickListener(v -> {
            startActivity(new Intent(WelcomeScreen.this, MainDashBoard.class));
            finish();
        });


        btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(WelcomeScreen.this, LoginScreen.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        });


        btnRegistration.setOnClickListener(v -> {
            startActivity(new Intent(WelcomeScreen.this, SignUpActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        });

    }

    private void createReference() {
        btnContinueAsGuest=findViewById(R.id.btnGuestContinue);
        btnLogin=findViewById(R.id.btnLogin);
        btnRegistration=findViewById(R.id.btnRegistration);

    }
}