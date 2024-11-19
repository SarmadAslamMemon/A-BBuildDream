package com.example.abbuilddream.ui.activity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.abbuilddream.R;
import com.example.abbuilddream.ui.activity.CheckOutActivity;
import com.example.abbuilddream.utility.GeneralMethods;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.widget.AppCompatButton;

import java.util.Objects;

public class BillingAddressFragment extends Fragment {

    View view;

    // Declare TextInputLayout and EditText views
    private TextInputLayout firstNameLayout, lastNameLayout, addressLayout, cityLayout, mobileNumberLayout, emailLayout;
    private TextInputEditText firstNameInput, lastNameInput, addressInput, cityInput, mobileNumberInput, emailInput;
    private AppCompatButton submitButton;
    ImageView backButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_billing_address, container, false);
        GeneralMethods.showSystemUI(requireActivity());
        getFindViewById();

        // Set up the click listener for the submit button
        submitButton.setOnClickListener(v -> validateAndSubmit());
        backButton.setOnClickListener(v -> requireActivity().onBackPressed());

        return view;
    }

    private void getFindViewById() {


        firstNameLayout = view.findViewById(R.id.firstNameLayout);
        lastNameLayout = view.findViewById(R.id.lastNameLayout);
        addressLayout = view.findViewById(R.id.addressLayout);
        cityLayout = view.findViewById(R.id.cityLayout);
        mobileNumberLayout = view.findViewById(R.id.mobileNumberLayout);
        emailLayout = view.findViewById(R.id.emailLayout);



        firstNameInput = view.findViewById(R.id.firstNameInput);
        lastNameInput = view.findViewById(R.id.lastNameInput);
        addressInput = view.findViewById(R.id.addressInput);
        cityInput = view.findViewById(R.id.cityInput);
        mobileNumberInput = view.findViewById(R.id.mobileNumberInput);
        emailInput = view.findViewById(R.id.emailInput);
        backButton = view.findViewById(R.id.backButtonBA);
        submitButton = view.findViewById(R.id.submitBillingInfoButton);
    }

    private void validateAndSubmit() {

        // Validate each field and set errors if necessary
        if (TextUtils.isEmpty(Objects.requireNonNull(firstNameInput.getText()).toString().trim())) {
            firstNameLayout.setError("First name is required.");
        } else if (TextUtils.isEmpty(Objects.requireNonNull(lastNameInput.getText()).toString().trim())) {

            lastNameLayout.setError("Last name is required.");
        } else if (TextUtils.isEmpty(Objects.requireNonNull(addressInput.getText()).toString().trim())) {
            addressLayout.setError("Address is required.");
        } else if (TextUtils.isEmpty(Objects.requireNonNull(cityInput.getText()).toString().trim())) {
            cityLayout.setError("City is required.");
        } else if (TextUtils.isEmpty(Objects.requireNonNull(mobileNumberInput.getText()).toString().trim())) {
            mobileNumberLayout.setError("Mobile number is required.");
        } else if (!mobileNumberInput.getText().toString().matches("\\d{10}")) {
            mobileNumberLayout.setError("Invalid mobile number.");
        } else if (TextUtils.isEmpty(Objects.requireNonNull(emailInput.getText()).toString().trim())) {
            emailLayout.setError("Email is required.");
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput.getText().toString()).matches()) {
            emailLayout.setError("Invalid email address.");
        } else {
            // If all fields are valid, create a Bundle and proceed
            Bundle billingInfoBundle = new Bundle();
            billingInfoBundle.putString("firstName", firstNameInput.getText().toString().trim());
            billingInfoBundle.putString("lastName", lastNameInput.getText().toString().trim());
            billingInfoBundle.putString("address", addressInput.getText().toString().trim());
            billingInfoBundle.putString("city", cityInput.getText().toString().trim());
            billingInfoBundle.putString("mobileNumber", mobileNumberInput.getText().toString().trim());
            billingInfoBundle.putString("email", emailInput.getText().toString().trim());

            // For now, show a success message
            Toast.makeText(getActivity(), "Billing info submitted successfully!", Toast.LENGTH_SHORT).show();

            // You can pass the data to the next activity, e.g. CheckoutActivity

            Intent intent = new Intent(getActivity(), CheckOutActivity.class);
            intent.putExtras(billingInfoBundle);
            startActivity(intent);

        }
    }
}
