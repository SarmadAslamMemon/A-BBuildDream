package com.example.abbuilddream.adaptor;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.abbuilddream.R;
import com.example.abbuilddream.model.CartItem;
import com.example.abbuilddream.model.Product;

import java.util.List;

public class AddToCartAdapter extends RecyclerView.Adapter<AddToCartAdapter.ViewHolder> {

    private List<Product> cartItems;
    private Context context;

    // Constructor
    public AddToCartAdapter(Context context, List<Product> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
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
        Product item = cartItems.get(position);

        // Assuming your CartItem class has a method to get details
        holder.productName.setText(item.getName());
        holder.productPrice.setText(String.valueOf(item.getPrice()));
        holder.productDetail.setText(item.getDescription());
        Glide.with(context).load(item.getImage()).into(holder.imageDetail);
    }

    // Return the size of the dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageDetail;
        public TextView productName;
        public TextView productPrice;
        public TextView productDetail;

        public ViewHolder(View view) {
            super(view);
            imageDetail = view.findViewById(R.id.image_detail_view);
            productName = view.findViewById(R.id.product_name);
            productPrice = view.findViewById(R.id.Price_detail);
            productDetail = view.findViewById(R.id.product_pri);
        }
    }
}
