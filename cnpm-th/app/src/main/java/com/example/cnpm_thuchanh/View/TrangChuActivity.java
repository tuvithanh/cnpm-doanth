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

import com.example.cnpm_thuchanh.Admin.Product.QLProductActivity;
import com.example.cnpm_thuchanh.R;
import com.example.cnpm_thuchanh.Session.UserSession;
import com.google.android.material.navigation.NavigationView;

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

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_trangchu) {
                Toast.makeText(this, "Trang chủ", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_taikhoan) {
                Toast.makeText(this, "Tài khoản", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_sanpham) {
                startActivity(new Intent(this, QLProductActivity.class));
            } else if (id == R.id.nav_logout) {
                // Xóa session
                session.clear();

                // Xóa SharedPreferences lưu username
                getSharedPreferences("login_prefs", MODE_PRIVATE).edit().clear().apply();

                // Chuyển về màn hình đăng nhập
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
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
