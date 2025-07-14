package com.example.cnpm_thuchanh.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnpm_thuchanh.Dao.ProductDao;
import com.example.cnpm_thuchanh.Model.OrderDetail;
import com.example.cnpm_thuchanh.Model.Product;
import com.example.cnpm_thuchanh.R;

import java.io.File;
import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {

    private Context context;
    private List<OrderDetail> orderDetailList;

    public OrderDetailAdapter(Context context, List<OrderDetail> orderDetailList) {
        this.context = context;
        this.orderDetailList = orderDetailList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDetail detail = orderDetailList.get(position);

        // Lấy product từ productId
        ProductDao productDao = new ProductDao(holder.itemView.getContext());
        Product product = productDao.getById(detail.getProductId());

        if (product != null) {
            holder.txtProductName.setText(product.getName());
            holder.txtPrice.setText("Giá: " + formatCurrency(product.getPrice()));
            holder.txtQuantity.setText("Số lượng: " + detail.getQuantity());

            double subtotal = product.getPrice() * detail.getQuantity();
            holder.txtSubtotal.setText("Tạm tính: " + formatCurrency(subtotal));

            String imagePath = product.getImagePath();
            if (imagePath != null && !imagePath.isEmpty()) {
                File imgFile = new File(imagePath);
                if (imgFile.exists()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    holder.imgProduct.setImageBitmap(bitmap);
                } else {
                    holder.imgProduct.setImageResource(R.drawable.ic_launcher_background);
                }
            } else {
                holder.imgProduct.setImageResource(R.drawable.ic_launcher_background);
            }
        }
    }


    @Override
    public int getItemCount() {
        return orderDetailList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtProductName, txtQuantity, txtPrice, txtSubtotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtSubtotal = itemView.findViewById(R.id.txtSubtotal);
        }
    }

    private String formatCurrency(double amount) {
        return String.format("%,.0fđ", amount);
    }
}
