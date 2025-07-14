package com.example.cnpm_thuchanh.Admin.Order;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnpm_thuchanh.Adapter.OrderAdapter;
import com.example.cnpm_thuchanh.Dao.OrderDao;
import com.example.cnpm_thuchanh.Model.Order;
import com.example.cnpm_thuchanh.R;

import java.util.List;

public class QLHoaDonActivity extends AppCompatActivity {

    RecyclerView recyclerOrder;
    OrderDao orderDao;
    List<Order> orderList;
    OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlhoa_don);

        recyclerOrder = findViewById(R.id.recyclerOrder);
        orderDao = new OrderDao(this);
        orderList = orderDao.getAllOrders(); // Hàm này cần thêm trong OrderDao

        orderAdapter = new OrderAdapter(orderList, new OrderAdapter.OnOrderActionListener() {
            @Override
            public void onView(Order order) {
                // Mở chi tiết hóa đơn nếu có màn hình chi tiết
                Intent intent = new Intent(QLHoaDonActivity.this, OrderDetailActivity.class);
                intent.putExtra("order_id", order.getId());
                startActivity(intent);
            }

            @Override
            public void onDelete(Order order) {
                new AlertDialog.Builder(QLHoaDonActivity.this)
                        .setTitle("Xác nhận xoá")
                        .setMessage("Bạn có chắc chắn muốn xoá hoá đơn #" + order.getId() + "?")
                        .setPositiveButton("Xoá", (dialog, which) -> {
                            orderDao.deleteOrder(order.getId());
                            orderList.remove(order);
                            orderAdapter.notifyDataSetChanged();
                            Toast.makeText(QLHoaDonActivity.this, "Đã xoá hoá đơn", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Huỷ", null)
                        .show();
            }
        });

        recyclerOrder.setLayoutManager(new LinearLayoutManager(this));
        recyclerOrder.setAdapter(orderAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        orderList.clear();
        orderList.addAll(orderDao.getAllOrders());
        orderAdapter.notifyDataSetChanged();
    }
}
