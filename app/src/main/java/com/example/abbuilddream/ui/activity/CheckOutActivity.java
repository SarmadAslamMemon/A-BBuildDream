package com.example.abbuilddream.ui.activity;

import static com.example.abbuilddream.network.RetrofitClient.baseUrl;
import static com.example.abbuilddream.utility.GeneralMethods.generateRandomOrderId;
import static com.example.abbuilddream.utility.GeneralMethods.showNoInternetDialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.abbuilddream.R;
import com.example.abbuilddream.model.AddOrderResponse;
import com.example.abbuilddream.model.CartItem;
import com.example.abbuilddream.model.Order;
import com.example.abbuilddream.model.Payment;
import com.example.abbuilddream.model.ProductOrder;
import com.example.abbuilddream.model.UserRegistered;
import com.example.abbuilddream.network.RetroInterface;
import com.example.abbuilddream.network.RetrofitClient;
import com.example.abbuilddream.utility.GeneralMethods;
import com.example.abbuilddream.utility.SessionManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_GALLERY = 2;
    ImageView backBtn;

    ImageView img;
    Button button;
    TextView totalTextView,overLayTextView,randomNameTextView;
    SessionManager sessionManager;
    FrameLayout frameLayout;
    ProgressBar progressBar;
    Bundle billingInfoBundle;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);



        getViews();
        getBillingAddressBundle();





        // Open the image selection dialog when the upload button is clicked
        frameLayout.setOnClickListener(view -> showImageOptionDialog());
        button.setOnClickListener(v -> {
            if(GeneralMethods.isInternetAvailable(CheckOutActivity.this)) {

                if(img.getDrawable() != null) {
                    GeneralMethods.showSpinner(progressBar);
                    sendPaymentDetailAPI();
                    sendOrderApi();
                }


            }else
            {
                progressBar.setVisibility(View.GONE);
                showNoInternetDialog(CheckOutActivity.this);
            }
        });

        backBtn.setOnClickListener(v -> {
            startActivity(new Intent(CheckOutActivity.this, MainDashBoard.class));
            finish();
        });

        getTotalTextAmount();










    }

    private void getBillingAddressBundle() {
        Intent intent = getIntent();
        if (intent != null) {
            billingInfoBundle = intent.getExtras();
        }
    }

    private void getViews() {
        img = findViewById(R.id.displayImage);
        button = findViewById(R.id.uploadButton);
        backBtn = findViewById(R.id.backButton);
        totalTextView = findViewById(R.id.totalAmountTextView);
        overLayTextView = findViewById(R.id.overlayText);
        randomNameTextView = findViewById(R.id.randomNameTextView);
        frameLayout = findViewById(R.id.imageUploadContainer);
        progressBar = findViewById(R.id.progressBarCH);
        sessionManager = new SessionManager(CheckOutActivity.this);
        String orderId=GeneralMethods.generateRandomOrderId(this);
        if(!orderId.isEmpty())
        {
            randomNameTextView.setText(orderId);
            sessionManager.setIsOrderDone(false);
        }
    }

    private void getTotalTextAmount() {
        List<ProductOrder> productOrders = sessionManager.getAddedToCartProducts();
        double totalPrice = 0.0;
        for (ProductOrder productOrder : productOrders) {
            totalPrice += productOrder.getTotalPrice();
        }
        totalTextView.setText(String.valueOf(totalPrice));
    }

    // Show the dialog to choose between Camera or Gallery
    private void showImageOptionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CheckOutActivity.this);
        builder.setTitle("Select Image")
                .setItems(new CharSequence[]{"Camera", "Gallery"},
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                switch (which) {
                                    case 0:
                                        clickFromCamera();
                                        break;
                                    case 1:
                                        openGallery();
                                        break;
                                    default:
                                        break;
                                }
                            }
                        })
                .show();
    }

    // Method to open camera
    private void clickFromCamera() {
        Intent openCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (openCamera.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(openCamera, REQUEST_IMAGE_CAPTURE);
        } else {
            Snackbar.make(findViewById(android.R.id.content), "No camera found", Snackbar.LENGTH_SHORT).show();
        }
    }

    // Method to open the Gallery
    private void openGallery() {
        Intent selectFromGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        selectFromGallery.setType("image/*");
        startActivityForResult(selectFromGallery, REQUEST_GALLERY);
    }

    // Handle result from camera or gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                // This was the result of the camera activity
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        img.setImageBitmap(imageBitmap);
                        setButtonText();

                    }
                }
                Toast.makeText(this, "Captured", Toast.LENGTH_SHORT).show();
            } else if (requestCode == REQUEST_GALLERY) {
                // This was the result of the gallery activity
                if (data != null) {
                    Uri selectedImageUri = data.getData();
                    img.setImageURI(selectedImageUri);
                    setButtonText();
                }
                Toast.makeText(this, "Selected from gallery", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void sendPaymentDetailAPI()
    {
        int id;
        if(sessionManager.getPaymentId() == 0) {
            id = generateRandomOrderId();
            sessionManager.savePaymentId(id);
        }else
        {
            id = sessionManager.getPaymentId();
        }

        Payment payment = new Payment();
        payment.setId(id);
        payment.setUserID(id);
        payment.setOrderDate(GeneralMethods.getCurrentDate());
        payment.setTotalItem(sessionManager.getAddedToCartProducts().toArray().length);
        payment.setTotalCost(sessionManager.getTotalAmount());
        payment.setStatus(0);
        payment.setPaymentImage(img.toString());

        Log.w("jarvis", "sendPaymentDetailAPI: " + payment.toString());


        Log.w("jarvis","check the payment details "+payment.toString());


        RetroInterface apiService = RetrofitClient.getClient(baseUrl).create(RetroInterface.class);
        Call<Payment> paymentCall= apiService.payment(payment);
        paymentCall.enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(@NonNull Call<Payment> call, @NonNull Response<Payment> response) {

                Log.w("jarvis", " payment "+ response);

            }

            @Override
            public void onFailure(@NonNull Call<Payment> call, @NonNull Throwable t) {
                Log.w("jarvis", " payment "+ t.getLocalizedMessage());

            }
        });




    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CheckOutActivity.this, MainDashBoard.class));
        finish();
    }

    public void setButtonText() {
        overLayTextView.setVisibility(View.GONE);
        button.setText("Pending Verification");
        button.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        button.setBackground(getDrawable(R.drawable.badges_background));
        button.setBackgroundColor(ContextCompat.getColor(CheckOutActivity.this, R.color.colorSecondary));

    }

    private void sendOrderApi()
    {
        Order order = new Order();
        List<ProductOrder> cartItems = sessionManager.getAddedToCartProducts();
        order.setCart(cartItems);
        if(billingInfoBundle != null)
        {
            order.setFirstName(billingInfoBundle.getString("firstName"));
            order.setLastName( billingInfoBundle.getString("lastName"));
            order.setAddress( billingInfoBundle.getString("address"));
            order.setCity( billingInfoBundle.getString("city"));
            order.setMobileNumber( billingInfoBundle.getString("mobileNumber"));
            order.setPassword("");
            order.setEmail( billingInfoBundle.getString("email"));
        }else
        {
            Snackbar.make(findViewById(android.R.id.content), "Can't Proceed Order ! Improper Billing Info", Snackbar.LENGTH_SHORT).show();
        }

        makeOrder(order);

        Log.w("jarvis", "sendOrderApi: " + order.toString());



    }

    private void makeOrder(Order order) {

        RetroInterface apiService = RetrofitClient.getClient(baseUrl).create(RetroInterface.class);
        Call<AddOrderResponse> createData = apiService.createData(order);
        createData.enqueue(new Callback<AddOrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<AddOrderResponse> call, @NonNull Response<AddOrderResponse> response) {
                if (response.isSuccessful()) {

                    AddOrderResponse addOrderResponse = response.body();
                    Log.w("jarvis", "onResponse:makeOrder " + addOrderResponse.getResult().getMessage());
                    sessionManager.setIsOrderDone(true);
                    sessionManager.clearAfterOrder();
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(@NonNull Call<AddOrderResponse> call, @NonNull Throwable t) {

                Log.w("jarvis", "onFailure:makeOrder " + t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });

    }


}