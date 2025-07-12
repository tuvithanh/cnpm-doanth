package com.example.cnpm_thuchanh.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView; // ✅ Sửa tại đây

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cnpm_thuchanh.Dao.UserDao;
import com.example.cnpm_thuchanh.Model.User;
import com.example.cnpm_thuchanh.R;
import com.example.cnpm_thuchanh.Session.UserSession;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    Button btnLogin;
    TextView btnGoRegister; // ✅ sửa lại đúng kiểu
    CheckBox chkRemember;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoRegister = findViewById(R.id.btnGoRegister); // ✅ OK
        chkRemember = findViewById(R.id.chkRemember);

        userDao = new UserDao(this);

        SharedPreferences prefs = getSharedPreferences("login_prefs", MODE_PRIVATE);
        boolean isRemembered = prefs.getBoolean("remember_username", false);
        if (isRemembered) {
            edtUsername.setText(prefs.getString("username", ""));
            chkRemember.setChecked(true);
        }

        btnLogin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (userDao.login(username, password)) {
                User user = userDao.getUserByUsername(username);
                if (user != null) {
                    new UserSession(this).saveUserInfo(user.getId(), user.getUsername(), user.getRole());
                }

                SharedPreferences.Editor editor = prefs.edit();
                if (chkRemember.isChecked()) {
                    editor.putString("username", username);
                    editor.putBoolean("remember_username", true);
                } else {
                    editor.clear();
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
