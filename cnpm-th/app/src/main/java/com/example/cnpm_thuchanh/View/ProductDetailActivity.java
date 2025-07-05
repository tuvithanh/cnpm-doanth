package com.example.cnpm_thuchanh.View;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnpm_thuchanh.Dao.CartDao;
import com.example.cnpm_thuchanh.Dao.CartItemDao;
import com.example.cnpm_thuchanh.Model.Cart;
import com.example.cnpm_thuchanh.Model.Product;
import com.example.cnpm_thuchanh.R;
import com.example.cnpm_thuchanh.Session.UserSession;

import java.io.File;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView imgProduct;
    TextView txtName, txtDesc, txtPrice;
    Button btnAddToCart;

    CartDao cartDao;
    CartItemDao cartItemDao;
    UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        imgProduct = findViewById(R.id.imgProductDetail);
        txtName = findViewById(R.id.txtNameDetail);
        txtDesc = findViewById(R.id.txtDescDetail);
        txtPrice = findViewById(R.id.txtPriceDetail);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        cartDao = new CartDao(this);
        cartItemDao = new CartItemDao(this);
        session = new UserSession(this);

        Product product = (Product) getIntent().getSerializableExtra("product");
        if (product != null) {
            txtName.setText(product.getName());
            txtDesc.setText(product.getDescription());
            txtPrice.setText(String.format("%.0f VNĐ", product.getPrice()));

            File imgFile = new File(product.getImagePath());
            if (imgFile.exists()) {
                imgProduct.setImageBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
            }

            btnAddToCart.setOnClickListener(v -> {
                int userId = session.getUserId();
                Cart cart = cartDao.getOrCreateCart(userId); // Tạo hoặc lấy giỏ hiện tại
                cartItemDao.addOrUpdateItem(cart.getId(), product.getId(), 1); // +1 sản phẩm
                Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            });
        }
    }
}
