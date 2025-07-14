package com.example.cnpm_thuchanh.Admin.Order;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnpm_thuchanh.Adapter.OrderDetailAdapter;
import com.example.cnpm_thuchanh.Dao.OrderDao;
import com.example.cnpm_thuchanh.Dao.OrderDetailDao;
import com.example.cnpm_thuchanh.Model.Order;
import com.example.cnpm_thuchanh.Model.OrderDetail;
import com.example.cnpm_thuchanh.R;

import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {

    TextView txtOrderId, txtUserId, txtStatus, txtDate, txtTotal;
    RecyclerView recyclerOrderDetail;

    OrderDao orderDao;
    OrderDetailDao orderDetailDao;
    OrderDetailAdapter adapter;
    List<OrderDetail> detailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        txtOrderId = findViewById(R.id.txtOrderId);
        txtUserId = findViewById(R.id.txtUserId);
        txtStatus = findViewById(R.id.txtStatus);
        txtDate = findViewById(R.id.txtDate);
        txtTotal = findViewById(R.id.txtTotal);
        recyclerOrderDetail = findViewById(R.id.recyclerOrderDetail);

        int orderId = getIntent().getIntExtra("order_id", -1);
        if (orderId == -1) finish();

        orderDao = new OrderDao(this);
        orderDetailDao = new OrderDetailDao(this);

        Order order = orderDao.getOrderById(orderId);
        detailList = orderDetailDao.getDetailsByOrderId(orderId);

        // Set thông tin hóa đơn
        txtOrderId.setText("Hóa đơn #" + order.getId());
        txtUserId.setText("User ID: " + order.getUserId());
        txtStatus.setText("Trạng thái: " + order.getStatus());
        txtDate.setText("Ngày tạo: " + order.getCreatedAt());
        txtTotal.setText("Tổng tiền: " + order.getTotal() + "đ");

        // Gắn adapter hiển thị sản phẩm trong hóa đơn
        adapter = new OrderDetailAdapter(this, detailList);
        recyclerOrderDetail.setLayoutManager(new LinearLayoutManager(this));
        recyclerOrderDetail.setAdapter(adapter);
    }
}
