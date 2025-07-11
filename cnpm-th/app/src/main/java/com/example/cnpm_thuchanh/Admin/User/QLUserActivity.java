package com.example.cnpm_thuchanh.Admin.User;



import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnpm_thuchanh.Adapter.UserAdapter;
import com.example.cnpm_thuchanh.Dao.UserDao;
import com.example.cnpm_thuchanh.Model.User;
import com.example.cnpm_thuchanh.R;

import java.util.List;

public class QLUserActivity extends AppCompatActivity {

    RecyclerView recyclerUser;
    Button btnAddUser;
    UserDao userDao;
    List<User> list;
    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qluser);

        recyclerUser = findViewById(R.id.recyclerUser);
        btnAddUser = findViewById(R.id.btnAddUser);
        userDao = new UserDao(this);
        list = userDao.getAllUsers();

        adapter = new UserAdapter(list, new UserAdapter.OnUserActionListener() {
            @Override
            public void onEdit(User user) {
                // Mở EditActivity
                Intent i = new Intent(QLUserActivity.this, AddEditUserActivity.class);
                i.putExtra("edit_user", user);
                startActivity(i);
            }

            @Override
            public void onDelete(User user) {
                new AlertDialog.Builder(QLUserActivity.this)
                        .setTitle("Xác nhận xoá")
                        .setMessage("Bạn có chắc chắn muốn xoá tài khoản: " + user.getUsername() + " ?")
                        .setPositiveButton("Xoá", (dialog, which) -> {
                            userDao.delete(user.getId());
                            list.remove(user);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(QLUserActivity.this, "Đã xoá " + user.getUsername(), Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Huỷ", null)
                        .show();
            }
        });


        recyclerUser.setLayoutManager(new LinearLayoutManager(this));
        recyclerUser.setAdapter(adapter);

        btnAddUser.setOnClickListener(v -> {
            startActivity(new Intent(this, AddEditUserActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        list.clear();
        list.addAll(userDao.getAll());
        adapter.notifyDataSetChanged();
    }
}

