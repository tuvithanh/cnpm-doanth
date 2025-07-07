package com.example.cnpm_thuchanh.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnpm_thuchanh.Adapter.CartAdapter;
import com.example.cnpm_thuchanh.Dao.CartDao;
import com.example.cnpm_thuchanh.Dao.CartItemDao;
import com.example.cnpm_thuchanh.DatabaseHelper.CheckoutHelper;
import com.example.cnpm_thuchanh.Model.Cart;
import com.example.cnpm_thuchanh.Model.CartItem;
import com.example.cnpm_thuchanh.R;
import com.example.cnpm_thuchanh.Session.UserSession;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerCart;
    Button btnCheckout;
    TextView txtTotalPrice;

    CartDao cartDao;
    CartItemDao cartItemDao;
    UserSession session;

    double total = 0; // ✅ Đưa total ra làm biến toàn cục

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Ánh xạ view
        recyclerCart = findViewById(R.id.recyclerCart);
        btnCheckout = findViewById(R.id.btnCheckout);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);

        // Khởi tạo session và DAO
        session = new UserSession(this);
        cartDao = new CartDao(this);
        cartItemDao = new CartItemDao(this);

        // Lấy giỏ hàng
        int userId = session.getUserId();
        Cart cart = cartDao.getOrCreateCart(userId);
        List<CartItem> cartItems = cartItemDao.getItemsByCartId(cart.getId());

        // 👉 Tính tổng tiền
        total = 0;
        for (CartItem item : cartItems) {
            if (item.getProduct() != null) {
                total += item.getQuantity() * item.getProduct().getPrice();
            }
        }
        DecimalFormat formatter = new DecimalFormat("#,###");
        String formattedTotal = formatter.format(total);
        txtTotalPrice.setText("Tổng tiền: " + formattedTotal + " VNĐ");

        // Set Adapter cho RecyclerView
        CartAdapter adapter = new CartAdapter(cartItems, cartItemDao);
        recyclerCart.setLayoutManager(new LinearLayoutManager(this));
        recyclerCart.setAdapter(adapter);

        // Xử lý nút thanh toán
        btnCheckout.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
            intent.putExtra("total", total);
            startActivity(intent);
        });




    }
}
