package com.example.cnpm_thuchanh.Adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnpm_thuchanh.Model.CartItem;
import com.example.cnpm_thuchanh.R;

import java.io.File;
import java.util.List;

public class PaymentProductAdapter extends RecyclerView.Adapter<PaymentProductAdapter.ViewHolder> {
    private final List<CartItem> cartItems;

    public PaymentProductAdapter(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtProductName, txtProductPrice, txtQuantity;

        public ViewHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtProductPrice = itemView.findViewById(R.id.txtProductPrice);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
        }
    }

    @NonNull
    @Override
    public PaymentProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentProductAdapter.ViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        if (item.getProduct() != null) {
            holder.txtProductName.setText(item.getProduct().getName());
            holder.txtProductPrice.setText(String.format("%.0f VNĐ", item.getProduct().getPrice()));
            holder.txtQuantity.setText("Số lượng: " + item.getQuantity());

            // Load ảnh từ đường dẫn (nếu có)
            if (item.getProduct().getImagePath() != null) {
                File imgFile = new File(item.getProduct().getImagePath());
                if (imgFile.exists()) {
                    holder.imgProduct.setImageURI(Uri.fromFile(imgFile));
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }
}

