package com.example.abbuilddream.ui.activity;

import static com.example.abbuilddream.network.RetrofitClient.baseUrl;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.abbuilddream.R;
import com.example.abbuilddream.network.RetroInterface;
import com.example.abbuilddream.network.RetrofitClient;
import com.example.abbuilddream.model.UserRegistered;
import com.example.abbuilddream.model.UserRegistration;
import com.example.abbuilddream.utility.GeneralMethods;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText usernameEditText,lastNameEditText, passwordEditText, emailEditText, mobileEditText, addressEditText, confirmPasswordEditText;
    Button signupButton;
    TextView loginTextView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        GeneralMethods.hideSystemUI(this);

        getReferences();
        signupButton.setOnClickListener(v -> {
            Log.w("jarvis", "clicked");
            if (areFieldsValid()) {
                registerUser();
            }
        });

        loginTextView.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, LoginScreen.class));
            finish();
        });



    }

    private boolean areFieldsValid() {
        return checkField(usernameEditText, "Username is required") &&
                checkField(lastNameEditText, "Last name is required") &&
                checkField(passwordEditText, "Password is required") &&
                checkField(emailEditText, "Email is required") &&
                checkField(mobileEditText, "Mobile is required") &&
                checkField(addressEditText, "Address is required") &&
                checkField(confirmPasswordEditText, "Confirm Password is required") &&
                checkPasswordsMatch();
    }

    private boolean checkField(EditText field, String errorMessage) {
        if (Objects.requireNonNull(field.getText()).toString().isEmpty()) {
            field.setError(errorMessage);
            return false;
        }
        return true;
    }

    private boolean checkPasswordsMatch() {
        if (!Objects.requireNonNull(passwordEditText.getText()).toString().equals(Objects.requireNonNull(confirmPasswordEditText.getText()).toString()))
        {
            confirmPasswordEditText.setError("Passwords do not match");
            return false;
        }
        return true;
    }


    private void makeApiCall(UserRegistration userRegistration) {

        RetroInterface apiService = RetrofitClient.getClient(baseUrl).create(RetroInterface.class);
        Call<UserRegistered> call = apiService.userRegister(userRegistration);
        Log.w("jarvis", "User registration details: " + userRegistration.getFirstName());
        call.enqueue(new Callback<UserRegistered>() {
            @Override
            public void onResponse(@NonNull Call<UserRegistered> call, @NonNull Response<UserRegistered> response) {
                progressBar.setVisibility(View.GONE);
                    Snackbar.make(findViewById(android.R.id.content), "Successfully Registered", Snackbar.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, LoginScreen.class));
                    finish();
            }

            @Override
            public void onFailure(@NonNull Call<UserRegistered> call, @NonNull Throwable t) {
                Log.w("jarvis", "test here "+t.getLocalizedMessage());
                progressBar.setVisibility(View.GONE);
                Snackbar.make(findViewById(android.R.id.content), "Try Again Later", Snackbar.LENGTH_SHORT).show();

            }
        });

    }

    private void clearError() {
        usernameEditText.setError(null);
        passwordEditText.setError(null);
        emailEditText.setError(null);
        mobileEditText.setError(null);
        addressEditText.setError(null);
    }

    private void getReferences() {
        usernameEditText=findViewById(R.id.usernameEditText);
        lastNameEditText=findViewById(R.id.lastnameEditText);
        passwordEditText=findViewById(R.id.passEditText);
        emailEditText=findViewById(R.id.emailEditText);
        mobileEditText=findViewById(R.id.mobileEditText);
        addressEditText=findViewById(R.id.addressEditText);
        confirmPasswordEditText=findViewById(R.id.confirmPassEditText);
        signupButton=findViewById(R.id.signUpBtn);
        loginTextView=findViewById(R.id.loginTextView);
        progressBar=findViewById(R.id.progressBar);

    }

    private void registerUser() {
        UserRegistration userRegistration = new UserRegistration();
        userRegistration.setFirstName(Objects.requireNonNull(usernameEditText.getText()).toString());
        userRegistration.setLastName(Objects.requireNonNull(lastNameEditText.getText()).toString());
        userRegistration.setPassword(Objects.requireNonNull(passwordEditText.getText()).toString());
        userRegistration.setEmail(Objects.requireNonNull(emailEditText.getText()).toString());
        userRegistration.setMobileNumber(Objects.requireNonNull(mobileEditText.getText()).toString());
        userRegistration.setAddress(Objects.requireNonNull(addressEditText.getText()).toString());
        userRegistration.setIsActive(true);

        Log.w("jarvis", "User registration details: " + userRegistration);
        progressBar.setVisibility(View.VISIBLE);
        makeApiCall(userRegistration);

    }


    public void continueAsGuest(View view) {
        startActivity(new Intent(SignUpActivity.this, MainDashBoard.class));
        finish();
    }
}