package com.example.cnpm_thuchanh.Admin.Product;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnpm_thuchanh.Adapter.ProductAdapter;
import com.example.cnpm_thuchanh.Dao.ProductDao;
import com.example.cnpm_thuchanh.Model.Product;
import com.example.cnpm_thuchanh.R;

import java.util.List;

public class QLProductActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProductDao productDao;
    List<Product> productList;
    ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlproduct);

        recyclerView = findViewById(R.id.recyclerProduct);
        productDao = new ProductDao(this);

        findViewById(R.id.btnAddProduct).setOnClickListener(v -> {
            startActivity(new Intent(this, AddProductActivity.class));
        });

        loadProducts();
    }

    private void loadProducts() {
        productList = productDao.getAll();
        adapter = new ProductAdapter(productList);

        adapter.setOnItemClickListener((product, view) -> {
            PopupMenu popupMenu = new PopupMenu(this, view);
            popupMenu.getMenuInflater().inflate(R.menu.product_context_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_edit) {
                    Intent intent = new Intent(this, EditProductActivity.class);
                    intent.putExtra("product_id", product.getId());
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.menu_delete) {
                    productDao.delete(product.getId());
                    Toast.makeText(this, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
                    loadProducts();
                    return true;
                }
                return false;
            });

            popupMenu.show();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadProducts();
    }
}
