package com.example.cnpm_thuchanh.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnpm_thuchanh.Model.Order;
import com.example.cnpm_thuchanh.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;
    private final OnOrderActionListener listener;

    public interface OnOrderActionListener {
        void onView(Order order);
        void onDelete(Order order);
    }

    public OrderAdapter(List<Order> orderList, OnOrderActionListener listener) {
        this.orderList = orderList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.txtOrderId.setText("Hóa đơn #" + order.getId());
        holder.txtUserId.setText("User ID: " + order.getUserId());
        holder.txtTotal.setText("Tổng tiền: " + order.getTotal() + "đ");
        holder.txtStatus.setText("Trạng thái: " + order.getStatus());
        holder.txtDate.setText("Ngày tạo: " + order.getCreatedAt());

        holder.btnView.setOnClickListener(v -> listener.onView(order));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(order));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView txtOrderId, txtUserId, txtTotal, txtStatus, txtDate;
        ImageButton btnView, btnDelete;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderId = itemView.findViewById(R.id.txtOrderId);
            txtUserId = itemView.findViewById(R.id.txtUserId);
            txtTotal = itemView.findViewById(R.id.txtTotal);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtDate = itemView.findViewById(R.id.txtDate);
            btnView = itemView.findViewById(R.id.btnViewOrder);
            btnDelete = itemView.findViewById(R.id.btnDeleteOrder);
        }
    }
}
