package com.example.cnpm_thuchanh.Admin.User;


import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnpm_thuchanh.Dao.UserDao;
import com.example.cnpm_thuchanh.Model.User;
import com.example.cnpm_thuchanh.R;

public class AddEditUserActivity extends AppCompatActivity {
    EditText edtName, edtUsername, edtPassword, edtEmail;
    Button btnSave;
    UserDao userDao;
    boolean isEdit = false;
    int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_user);

        edtName = findViewById(R.id.edtName);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtEmail = findViewById(R.id.edtEmail);
        btnSave = findViewById(R.id.btnSave);
        userDao = new UserDao(this);

        if (getIntent().hasExtra("edit_user")) {
            isEdit = true;
            User u = (User) getIntent().getSerializableExtra("edit_user");
            userId = u.getId();
            edtName.setText(u.getName());
            edtUsername.setText(u.getUsername());
            edtUsername.setEnabled(false);
            edtPassword.setText(u.getPassword());
            edtEmail.setText(u.getEmail());
        }

        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(username) ||
                    TextUtils.isEmpty(password) || TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isEdit) {
                User u = new User(userId, name, 0, username, password, email, "", "", "U", "");
                userDao.update(u);
                Toast.makeText(this, "Đã cập nhật tài khoản", Toast.LENGTH_SHORT).show();
            } else {
                User u = new User(0, name, 0, username, password, email, "", "", "U", "");
                boolean ok = userDao.register(u);
                if (ok)
                    Toast.makeText(this, "Thêm tài khoản thành công", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
            }

            finish();
        });
    }
}
