package com.example.abbuilddream.ui.activity.fragments;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.abbuilddream.R;
import com.example.abbuilddream.model.Product;
import com.example.abbuilddream.model.ProductOrder;
import com.example.abbuilddream.ui.activity.MainDashBoard;
import com.example.abbuilddream.utility.GeneralMethods;
import com.example.abbuilddream.utility.SessionManager;

import java.util.List;
import java.util.Objects;

public class product_detail_fragment extends Fragment {

    Product product;
    ImageView productImage, backButton, favoriteBtn;
    TextView productTitle;
    TextView productDescription;
    TextView productPrice, productQuantityTextView;
    LottieAnimationView addToFavLottie,addedToCartLottie;
    Button addToCartBtn,btnIncrease,btnDecrease;
    SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_detail_fragment, container, false);
        GeneralMethods.hideBottomNav((MainDashBoard) requireActivity());
        EdgeToEdge.enable(requireActivity());
        GeneralMethods.hideSystemUI(requireActivity());


        findViewById(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            product = (Product) bundle.getSerializable("product");
            if(product!=null)
            {
                setViews(product);
            }

        }

        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavLottie.setVisibility(View.VISIBLE);
                addToFavLottie.playAnimation();
                new Handler().postDelayed(() -> addToFavLottie.setVisibility(View.GONE),1500);
            }
        });


        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddedToCartLottie(v);
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
                GeneralMethods.showBottomNav((MainDashBoard) requireActivity());
            }
        });

        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseProductQuantity();
            }
        });


        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecreaseProductQuantity();
            }
        });
    return  view;
    }

    private void showAddedToCartLottie(View v) {

        LayoutInflater inflater = LayoutInflater.from(v.getContext());
                View dialogView = inflater.inflate(R.layout.added_to_cart_animation_layout, null);
                AlertDialog dialog = new AlertDialog.Builder(v.getContext())
                        .setView(dialogView)
                        .setCancelable(false)
                        .create();
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();

                addedToCartLottie=dialogView.findViewById(R.id.addedToCartLottie);
                addedToCartLottie.setVisibility(View.VISIBLE);
                addedToCartLottie.playAnimation();

                // Dismiss the dialog after 2 seconds
                new Handler().postDelayed(dialog::dismiss, 2000);

                sessionManager= new SessionManager(v.getContext());
                List<Product> cartItems = sessionManager.getCartItems();
                List<ProductOrder> addedToCartProducts = sessionManager.getAddedToCartProducts();

            ProductOrder newProductOrder = new ProductOrder();
            newProductOrder.setProductId(product.getId());
            newProductOrder.setTotalPrice(product.getPrice());
            newProductOrder.setCount(1);
            addedToCartProducts.add(newProductOrder);
            Log.d("ProductDetails", "New product added to cart. Product ID: " + product.getId());
            cartItems.add(product);
            sessionManager.saveCartItems(cartItems);


            }


    private void findViewById(View view) {
        productImage = view.findViewById(R.id.productImagePD);
        productTitle = view.findViewById(R.id.productTitlePD);
        productDescription = view.findViewById(R.id.productDescriptionPD);
        productPrice = view.findViewById(R.id.productPricePD);
        addToFavLottie = view.findViewById(R.id.addFavAnimationPD);
        backButton = view.findViewById(R.id.btnBack);
        favoriteBtn = view.findViewById(R.id.favoriteBtnPD);
        addToCartBtn=view.findViewById(R.id.addToCartBtnPD);
        btnIncrease=view.findViewById(R.id.btnIncreasePD);
        btnDecrease=view.findViewById(R.id.btnDecreasePD);
        productQuantityTextView=view.findViewById(R.id.productQuantityTextView);
    }


    private void setViews(Product product) {
        Log.w("jarvis", "setViews: " + product.getImage());
        Glide.with(requireContext()).load(product.getImage()).into(productImage);
        productPrice.setText("Rs. " + product.getPrice());
        productTitle.setText(product.getName());
        productDescription.setText(product.getDescription());

    }

    public void increaseProductQuantity()
    {
        int currentQuantity = Integer.parseInt(productQuantityTextView.getText().toString());
        currentQuantity++;
        productQuantityTextView.setText(String.valueOf(currentQuantity));
        addTotalPrice();
    }

    public void DecreaseProductQuantity()
    {
        int currentQuantity = Integer.parseInt(productQuantityTextView.getText().toString());
        if(currentQuantity>1) {
            currentQuantity--;
            productQuantityTextView.setText(String.valueOf(currentQuantity));
            subtractTotalPrice();
        }

    }

   public void addTotalPrice()
    {
        double price = Double.parseDouble(product.getPrice().toString());
        int quantity = Integer.parseInt(productQuantityTextView.getText().toString());
        double totalPrice = price * quantity;
        Log.w("jarvis", "addTotalPrice: " + totalPrice);
        productPrice.setText("Rs. " + totalPrice);


    }

    public void subtractTotalPrice()
    {
        double productActualPrice = Double.parseDouble(product.getPrice().toString());
        double totalPrice = Double.parseDouble(productPrice.getText().toString().replace("Rs. ", ""));
        Log.w("jarvis", "subtractTotalPrice: " + totalPrice);
        productPrice.setText("Rs. " + (totalPrice-productActualPrice));
    }


    public void badgeColorChanger()
    {}

}