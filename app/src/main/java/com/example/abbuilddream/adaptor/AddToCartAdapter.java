package com.example.abbuilddream.adaptor;



import static androidx.core.app.PendingIntentCompat.getActivity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.abbuilddream.R;
import com.example.abbuilddream.model.Product;
import com.example.abbuilddream.model.ProductOrder;
import com.example.abbuilddream.ui.activity.fragments.cart_fragment;
import com.example.abbuilddream.utility.CartUpdateListener;
import com.example.abbuilddream.utility.SessionManager;
import com.example.abbuilddream.utility.ShareViewModel;

import java.util.List;

public class AddToCartAdapter extends RecyclerView.Adapter<AddToCartAdapter.ViewHolder> {

    private List<Product> cartItems;
    private Context context;
    SessionManager sessionManager;
    private static int totalItemCount=0;
    public static double totalSum=0.0;
    private final ShareViewModel viewModel;
    private CartUpdateListener cartUpdateListener;

    // Constructor
    public AddToCartAdapter(Context context, List<Product> cartItems, ShareViewModel viewModel, CartUpdateListener cartUpdateListener) {
        this.context = context;
        this.cartItems = cartItems;
        this.viewModel = viewModel;
        this.cartUpdateListener = cartUpdateListener;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_to_cart_item_layout, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        sessionManager=new SessionManager(context);
        Product item = cartItems.get(position);
        int itemCount=sessionManager.getCountOfAddedProduct(item.getId());
        // Assuming your CartItem class has a method to get details
        holder.productName.setText(item.getName());
        holder.productPrice.setText(String.valueOf(item.getPrice()*itemCount));
        holder.productDetail.setText(item.getDescription());
        Glide.with(context).load(item.getImage()).into(holder.imageDetail);
        holder.btnAddQuantity.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(holder.productQuantity.getText().toString());
            holder.productQuantity.setText(String.valueOf(currentQuantity + 1));
            holder.productPrice.setText(String.valueOf(item.getPrice()*(currentQuantity+1)));

            List<ProductOrder> productOrders = sessionManager.getAddedToCartProducts();
            for(ProductOrder productOrder:productOrders)
            {
                if(productOrder.getProductId()==item.getId())
                {
                    productOrder.setCount(productOrder.getCount()+1);
                    productOrder.setTotalPrice(productOrder.getTotalPrice()+item.getPrice());
                    sessionManager.saveCart(productOrders);
                    cartUpdateListener.increaseItem(productOrder.getCount(),item.getPrice());
                }

            }


        });


        holder.btnRemoveQuantity.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(holder.productQuantity.getText().toString());
            if (currentQuantity > 1) {
                holder.productQuantity.setText(String.valueOf(currentQuantity - 1));
                holder.productPrice.setText(String.valueOf(item.getPrice()*(currentQuantity-1)));
                List<ProductOrder> productOrders = sessionManager.getAddedToCartProducts();
                for(ProductOrder productOrder:productOrders)
                {
                    if(productOrder.getProductId()==item.getId())
                    {
                        productOrder.setCount(productOrder.getCount()-1);
                        productOrder.setTotalPrice(productOrder.getTotalPrice()-item.getPrice());
                        sessionManager.saveCart(productOrders);
                        cartUpdateListener.decreaseItem(productOrder.getCount(), item.getPrice());
                    }
                }
            }
        });
        holder.productQuantity.setText(String.valueOf(itemCount));
        totalSum+=item.getPrice()*itemCount;
        totalItemCount +=itemCount;


//        sessionManager.totalAmountCount(totalItemCount,totalSum);
        Log.d("jarvis", "Total Item Count: " + totalItemCount  + " Total Sum: " + totalSum);



        holder.cancelButton.setOnClickListener(v -> {
            cartItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
            sessionManager.removeCartItemById(item.getId());
            sessionManager.removeCartItem(item.getId());
            sessionManager.reduceTotalCount(itemCount,totalSum);
            cartUpdateListener.refreshTheFragment();

        });
    }

    // Return the size of the dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageDetail, cancelButton;
        public TextView productName;
        public TextView productPrice;
        public TextView productDetail;
        public TextView productQuantity;
        public Button btnAddQuantity, btnRemoveQuantity;

        public ViewHolder(View view) {
            super(view);
            imageDetail = view.findViewById(R.id.image_detail_view);
            productName = view.findViewById(R.id.product_name);
            productPrice = view.findViewById(R.id.Price_detail);
            productDetail = view.findViewById(R.id.product_pri);
            productQuantity = view.findViewById(R.id.productQuantityTextView);
            btnAddQuantity = view.findViewById(R.id.btnIncreasePD);
            btnRemoveQuantity = view.findViewById(R.id.btnDecreasePD);
            cancelButton = view.findViewById(R.id.btnCancelAC);
        }
    }
}
