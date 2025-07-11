package com.example.cnpm_thuchanh.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnpm_thuchanh.Adapter.CategoryWithProductsAdapter;
import com.example.cnpm_thuchanh.Admin.Category.QLCategoryActivity;
import com.example.cnpm_thuchanh.Admin.Product.QLProductActivity;
import com.example.cnpm_thuchanh.Admin.User.QLUserActivity;
import com.example.cnpm_thuchanh.Dao.CategoryDao;
import com.example.cnpm_thuchanh.Model.Category;
import com.example.cnpm_thuchanh.R;
import com.example.cnpm_thuchanh.Session.UserSession;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class TrangChuActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        session = new UserSession(this);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Xin chào, " + session.getUsername());

        // Lấy danh sách danh mục và gán vào adapter
        CategoryDao categoryDao = new CategoryDao(this);
        List<Category> categoryList = categoryDao.getAll();

        RecyclerView recyclerView = findViewById(R.id.recyclerCategoryProduct);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CategoryWithProductsAdapter(this, categoryList));

        // Ẩn/hiện menu theo vai trò
        navigationView.getMenu().findItem(R.id.nav_qlcategory).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_product).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_qluser).setVisible(false);

        if ("A".equalsIgnoreCase(session.getRole())) {
            navigationView.getMenu().findItem(R.id.nav_qlcategory).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_product).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_qluser).setVisible(true);
        }

        // Toggle mở/đóng menu
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Xử lý điều hướng menu
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_trangchu) {
                Toast.makeText(this, "Trang chủ", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_taikhoan) {
                if (session.isLoggedIn()) {
                    startActivity(new Intent(this, ThongTinTaiKhoanActivity.class));
                } else {
                    Toast.makeText(this, "Vui lòng đăng nhập trước", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                }
            } else if (id == R.id.nav_qlcategory) {
                startActivity(new Intent(this, QLCategoryActivity.class));
            } else if (id == R.id.nav_product) {
                startActivity(new Intent(this, QLProductActivity.class));
            } else if (id == R.id.nav_qluser) {
                startActivity(new Intent(this, QLUserActivity.class));
            } else if (id == R.id.nav_logout) {
                session.clear();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_cart) {
            Toast.makeText(this, "Đi đến giỏ hàng", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, CartActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
