package com.example.cnpm_thuchanh.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnpm_thuchanh.Dao.CartItemDao;
import com.example.cnpm_thuchanh.Model.CartItem;
import com.example.cnpm_thuchanh.Model.Product;
import com.example.cnpm_thuchanh.R;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItems;
    private CartItemDao cartItemDao;

    public CartAdapter(List<CartItem> cartItems, CartItemDao cartItemDao) {
        this.cartItems = cartItems;
        this.cartItemDao = cartItemDao;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        Product p = item.getProduct();

        holder.txtName.setText(p.getName());

        // Định dạng giá tiền: 100.000 VNĐ
        DecimalFormat formatter = new DecimalFormat("#,###");
        String formattedPrice = formatter.format(p.getPrice());
        holder.txtPrice.setText(formattedPrice + " VNĐ");

        holder.txtQuantity.setText(String.valueOf(item.getQuantity()));

        File imgFile = new File(p.getImagePath());
        if (imgFile.exists()) {
            holder.imgProduct.setImageBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
        } else {
            holder.imgProduct.setImageResource(R.drawable.ic_image_placeholder);
        }

        holder.btnIncrease.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            cartItemDao.updateQuantity(item.getId(), item.getQuantity());
            notifyItemChanged(position);
        });

        holder.btnDecrease.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                cartItemDao.updateQuantity(item.getId(), item.getQuantity());
                notifyItemChanged(position);
            } else {
                // Nếu số lượng = 1, hỏi người dùng có muốn xóa không
                new AlertDialog.Builder(holder.itemView.getContext())
                        .setTitle("Xác nhận")
                        .setMessage("Bạn có muốn xóa sản phẩm này khỏi giỏ hàng không?")
                        .setPositiveButton("Xóa", (dialog, which) -> {
                            cartItemDao.deleteItem(item.getId());
                            cartItems.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, cartItems.size());
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(holder.itemView.getContext())
                    .setTitle("Xác nhận")
                    .setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        cartItemDao.deleteItem(item.getId());
                        cartItems.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, cartItems.size());
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtName, txtPrice, txtQuantity;
        Button btnIncrease, btnDecrease, btnDelete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProductCart);
            txtName = itemView.findViewById(R.id.txtProductNameCart);
            txtPrice = itemView.findViewById(R.id.txtProductPriceCart);
            txtQuantity = itemView.findViewById(R.id.txtQuantityCart);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnDelete = itemView.findViewById(R.id.btnDeleteCart);
        }
    }
}
