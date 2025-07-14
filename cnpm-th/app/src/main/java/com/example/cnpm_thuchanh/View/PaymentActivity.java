package com.example.cnpm_thuchanh.View;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnpm_thuchanh.Adapter.PaymentProductAdapter;
import com.example.cnpm_thuchanh.Dao.CartDao;
import com.example.cnpm_thuchanh.Dao.CartItemDao;
import com.example.cnpm_thuchanh.DatabaseHelper.CheckoutHelper;
import com.example.cnpm_thuchanh.DatabaseHelper.DatabaseHelper;
import com.example.cnpm_thuchanh.Model.Cart;
import com.example.cnpm_thuchanh.Model.CartItem;
import com.example.cnpm_thuchanh.Model.Product;
import com.example.cnpm_thuchanh.R;
import com.example.cnpm_thuchanh.Session.UserSession;

import java.text.DecimalFormat;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    TextView txtMessage;
    Spinner spinnerMethod;
    Button btnConfirm;
    RecyclerView recyclerPaymentProducts;
    double total;
    List<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        txtMessage = findViewById(R.id.txtMessage);
        spinnerMethod = findViewById(R.id.spinnerMethod);
        btnConfirm = findViewById(R.id.btnConfirm);
        recyclerPaymentProducts = findViewById(R.id.recyclerPaymentProducts);

        // Lấy thông tin giỏ hàng từ DB
        UserSession session = new UserSession(this);
        int userId = session.getUserId();
        CartDao cartDao = new CartDao(this);
        CartItemDao cartItemDao = new CartItemDao(this);
        Cart cart = cartDao.getOrCreateCart(userId);
        cartItems = cartItemDao.getItemsByCartId(cart.getId());

        // Tính tổng tiền
        total = 0;
        for (CartItem item : cartItems) {
            if (item.getProduct() != null) {
                total += item.getQuantity() * item.getProduct().getPrice();
            }
        }



        DecimalFormat formatter = new DecimalFormat("#,###");
        String formattedTotal = formatter.format(total);
        txtMessage.setText("Tổng tiền: " + formattedTotal + " VNĐ");


        // Spinner phương thức thanh toán
        String[] methods = {"Tiền mặt", "Chuyển khoản", "Momo", "ZaloPay"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, methods);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMethod.setAdapter(adapter);

        // Hiển thị danh sách sản phẩm
        recyclerPaymentProducts.setLayoutManager(new LinearLayoutManager(this));
        recyclerPaymentProducts.setAdapter(new PaymentProductAdapter(cartItems));

        // Xử lý xác nhận thanh toán
        btnConfirm.setOnClickListener(v -> {
            String method = spinnerMethod.getSelectedItem().toString();
            CheckoutHelper helper = new CheckoutHelper(this);
            boolean success = helper.checkoutCart(userId, method);

            if (success) {
                Toast.makeText(this, "Thanh toán thành công với " + method, Toast.LENGTH_SHORT).show();

                // Lấy lại cart item list
                Cart cart1 = new CartDao(this).getOrCreateCart(userId);
                List<CartItem> cartItemList = new CartItemDao(this).getItemsByCartId(cart1.getId());
                DatabaseHelper dbHelper = new DatabaseHelper(this);

                for (CartItem item : cartItemList) {
                    Product product = item.getProduct();
                    if (product != null) {
                        int productId = product.getId();
                        int quantity = item.getQuantity();

                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.execSQL("UPDATE Product SET sold_quantity = IFNULL(sold_quantity, 0) + ? WHERE id = ?",
                                new Object[]{quantity, productId});
                    }
                }

                Intent intent = new Intent(this, TrangChuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Thanh toán thất bại!", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
