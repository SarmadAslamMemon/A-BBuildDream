package com.example.abbuilddream.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.abbuilddream.R;
import com.example.abbuilddream.utility.GeneralMethods;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class LoginScreen extends AppCompatActivity {

    public TextInputEditText usernameEdt,userPassEdt;
    public Button loginBtn;
    public TextView signUpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_screen);

        GeneralMethods.hideSystemUI(this);
        getViews();
        setListener();


    }

    private void setListener() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateInput();

            }
        });

        signUpTextView.setOnClickListener(v -> {
            startActivity(new Intent(LoginScreen.this, SignUpActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        });

    }

    private void validateInput() {
         String username= Objects.requireNonNull(usernameEdt.getText()).toString();
         String password = Objects.requireNonNull(userPassEdt.getText()).toString();

         if(username.isEmpty())
         {
             usernameEdt.setError("Username is required");

         } else if (password.isEmpty()){
             usernameEdt.setError(null);
             userPassEdt.setError("Password is required");
         }else
         {
             usernameEdt.setError(null);
             userPassEdt.setError(null);



         }

    }

    private void getViews() {
        usernameEdt = findViewById(R.id.usernameEditInputText);
        userPassEdt = findViewById(R.id.userPasswordEditTextInput);
        loginBtn = findViewById(R.id.btnLogin);
        signUpTextView = findViewById(R.id.signUpTextView);
    }

    public void continueAsGuest(View view) {
        startActivity(new Intent(LoginScreen.this, MainDashBoard.class));
        finish();
    }
}