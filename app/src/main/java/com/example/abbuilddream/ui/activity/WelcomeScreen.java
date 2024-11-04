package com.example.abbuilddream.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.abbuilddream.R;

public class WelcomeScreen extends AppCompatActivity {


    Button btnContinueAsGuest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        createReference();
        btnContinueAsGuest.setOnClickListener(v -> {
            startActivity(new Intent(WelcomeScreen.this, MainDashBoard.class));
            finish();
        });
    }

    private void createReference() {
        btnContinueAsGuest=findViewById(R.id.btnGuestContinue);
    }
}