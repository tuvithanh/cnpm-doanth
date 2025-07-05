package com.example.cnpm_thuchanh.View;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnpm_thuchanh.Dao.UserDao;
import com.example.cnpm_thuchanh.Model.User;
import com.example.cnpm_thuchanh.R;
import com.example.cnpm_thuchanh.Session.UserSession;

public class ThongTinTaiKhoanActivity extends AppCompatActivity {

    TextView tvName, tvUsername, tvEmail, tvPhone, tvAddress, tvRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_tai_khoan);

        tvName = findViewById(R.id.tvName);
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvAddress = findViewById(R.id.tvAddress);
        tvRole = findViewById(R.id.tvRole);

        UserSession session = new UserSession(this);
        String username = session.getUsername();

        if (username != null) {
            UserDao userDao = new UserDao(this);
            User user = userDao.getUserByUsername(username);

            if (user != null) {
                tvName.setText(user.getName());
                tvUsername.setText(user.getUsername());
                tvEmail.setText(user.getEmail());

                // ✅ fix lỗi Resources$NotFoundException bằng cách ép kiểu int -> String
                tvPhone.setText(String.valueOf(user.getPhone()));

                tvAddress.setText(user.getAddress());
                tvRole.setText(user.getRole().equals("admin") ? "Quản trị viên" : "Người dùng");
            }
        }
    }
}
