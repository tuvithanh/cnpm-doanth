package com.example.cnpm_thuchanh.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnpm_thuchanh.Adapter.ProductAdapter;
import com.example.cnpm_thuchanh.Dao.CategoryDao;
import com.example.cnpm_thuchanh.Dao.ProductDao;
import com.example.cnpm_thuchanh.Model.Category;
import com.example.cnpm_thuchanh.Model.Product;
import com.example.cnpm_thuchanh.R;

import java.util.List;

public class ProductViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btnCategoryFilter;
    ProductDao productDao;
    CategoryDao categoryDao;
    List<Product> productList;
    ProductAdapter adapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        recyclerView = findViewById(R.id.recyclerProduct);
        btnCategoryFilter = findViewById(R.id.btnCategoryFilter);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sản phẩm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        productDao = new ProductDao(this);
        categoryDao = new CategoryDao(this);

        btnCategoryFilter.setOnClickListener(this::showCategoryPopup);

        loadProducts(); // Load all at first
    }

    private void showCategoryPopup(View anchor) {
        List<Category> categories = categoryDao.getAll();
        androidx.appcompat.widget.PopupMenu popup = new androidx.appcompat.widget.PopupMenu(this, anchor);

        popup.getMenu().add(0, -1, 0, "Tất cả");

        for (Category c : categories) {
            popup.getMenu().add(0, c.getId(), 0, c.getName());
        }

        popup.setOnMenuItemClickListener(item -> {
            int cateId = item.getItemId();
            if (cateId == -1) {
                loadProducts(); // Load all
            } else {
                productList = productDao.getByCategory(cateId);
                setupAdapter();
            }
            return true;
        });

        popup.show();
    }

    private void loadProducts() {
        productList = productDao.getAll();
        setupAdapter();
    }

    private void setupAdapter() {
        adapter = new ProductAdapter(productList);
        adapter.setOnItemClickListener((product, view) -> {
            Intent intent = new Intent(this, ProductDetailActivity.class);
            intent.putExtra("product", product); // Truyền nguyên đối tượng
            startActivity(intent);
        });

        // Grid layout với 2 cột
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }
}
