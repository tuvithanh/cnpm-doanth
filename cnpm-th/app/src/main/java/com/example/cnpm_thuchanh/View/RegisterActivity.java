package com.example.cnpm_thuchanh.View;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cnpm_thuchanh.R;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnpm_thuchanh.Dao.UserDao;
import com.example.cnpm_thuchanh.Model.User;

public class RegisterActivity extends AppCompatActivity {
    EditText edtName, edtUsername, edtEmail, edtPassword;
    Button btnRegister;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtName = findViewById(R.id.edtName);
        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);

        userDao = new UserDao(this);

        btnRegister.setOnClickListener(v -> {
            User u = new User();
            u.setName(edtName.getText().toString());
            u.setUsername(edtUsername.getText().toString());
            u.setEmail(edtEmail.getText().toString());
            u.setPassword(edtPassword.getText().toString());

            if (userDao.register(u)) {
                Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                finish(); // Quay lại login
            } else {
                Toast.makeText(this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
