package com.example.cnpm_thuchanh.View;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cnpm_thuchanh.Dao.UserDao;
import com.example.cnpm_thuchanh.Model.User;
import com.example.cnpm_thuchanh.R;
import com.example.cnpm_thuchanh.Session.UserSession;
import com.google.android.material.button.MaterialButton;

public class ThongTinTaiKhoanActivity extends AppCompatActivity {

    EditText edtName, edtEmail, edtPhone, edtAddress;
    TextView tvUsername, tvRole;
    MaterialButton btnEditSave;

    User currentUser;
    boolean isEditMode = false;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_tai_khoan);

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtAddress = findViewById(R.id.edtAddress);
        tvUsername = findViewById(R.id.tvUsername);
        tvRole = findViewById(R.id.tvRole);
        btnEditSave = findViewById(R.id.btnEditSave);

        userDao = new UserDao(this);
        UserSession session = new UserSession(this);
        currentUser = userDao.getUserByUsername(session.getUsername());

        if (currentUser != null) {
            edtName.setText(currentUser.getName());
            edtEmail.setText(currentUser.getEmail());
            edtPhone.setText(String.valueOf(currentUser.getPhone()));
            edtAddress.setText(currentUser.getAddress());
            tvUsername.setText(currentUser.getUsername());
            tvRole.setText("A".equals(currentUser.getRole()) ? "Quản trị viên" : "Người dùng");
        }

        setEditable(false);

        btnEditSave.setOnClickListener(v -> {
            if (isEditMode) {
                // Lưu thông tin
                currentUser.setName(edtName.getText().toString().trim());
                currentUser.setEmail(edtEmail.getText().toString().trim());
                String phoneStr = edtPhone.getText().toString().trim();

                if (!phoneStr.isEmpty()) {
                    try {
                        int phone = Integer.parseInt(phoneStr);
                        currentUser.setPhone(phone);
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                        return; // Dừng lại nếu sai format
                    }
                } else {
                    Toast.makeText(this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                    return;
                }

                currentUser.setAddress(edtAddress.getText().toString().trim());

                if (userDao.update(currentUser)) {
                    Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }

                setEditable(false);
                btnEditSave.setText("Chỉnh sửa thông tin");
            } else {
                // Cho phép chỉnh sửa
                setEditable(true);
                btnEditSave.setText("Lưu thông tin");
            }
            isEditMode = !isEditMode;
        });
    }

    private void setEditable(boolean editable) {
        edtName.setEnabled(editable);
        edtEmail.setEnabled(editable);
        edtPhone.setEnabled(editable);
        edtAddress.setEnabled(editable);
    }
}
