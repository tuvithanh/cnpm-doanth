package com.example.cnpm_thuchanh.View;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnpm_thuchanh.R;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cnpm_thuchanh.Dao.UserDao;
import com.example.cnpm_thuchanh.Session.UserSession;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.CheckBox;

import android.content.SharedPreferences;
import android.widget.CheckBox;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    Button btnLogin, btnGoRegister;
    CheckBox chkRemember;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoRegister = findViewById(R.id.btnGoRegister);
        chkRemember = findViewById(R.id.chkRemember);

        userDao = new UserDao(this);

        // 👉 Load username đã lưu nếu có
        SharedPreferences prefs = getSharedPreferences("login_prefs", MODE_PRIVATE);
        boolean isRemembered = prefs.getBoolean("remember_username", false);
        if (isRemembered) {
            edtUsername.setText(prefs.getString("username", ""));
            chkRemember.setChecked(true);
        }

        btnLogin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString();
            String password = edtPassword.getText().toString();

            if (userDao.login(username, password)) {
                new UserSession(this).saveUsername(username);

                // 👉 Ghi nhớ username nếu có tick
                SharedPreferences.Editor editor = prefs.edit();
                if (chkRemember.isChecked()) {
                    editor.putString("username", username);
                    editor.putBoolean("remember_username", true);
                } else {
                    editor.clear(); // Xoá nếu không nhớ nữa
                }
                editor.apply();

                startActivity(new Intent(this, TrangChuActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Sai thông tin", Toast.LENGTH_SHORT).show();
            }
        });

        btnGoRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}


