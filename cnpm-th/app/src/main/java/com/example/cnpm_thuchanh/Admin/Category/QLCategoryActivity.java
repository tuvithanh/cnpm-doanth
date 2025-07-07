package com.example.cnpm_thuchanh.Admin.Category;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnpm_thuchanh.Adapter.CategoryAdapter;
import com.example.cnpm_thuchanh.Dao.CategoryDao;
import com.example.cnpm_thuchanh.Model.Category;
import com.example.cnpm_thuchanh.R;

import java.util.List;

public class QLCategoryActivity extends AppCompatActivity {
    private EditText edtId, edtName;
    private Button btnAdd, btnUpdate, btnDelete;
    private RecyclerView recyclerView;
    private CategoryDao categoryDao;
    private List<Category> categoryList;
    private CategoryAdapter adapter;
    private Category selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlcategory);

        // Ánh xạ view
        edtId = findViewById(R.id.edtId);      // ✅ đã thêm
        edtName = findViewById(R.id.edtName);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        recyclerView = findViewById(R.id.recyclerCategory);

        // Khởi tạo DAO
        categoryDao = new CategoryDao(this);
        categoryList = categoryDao.getAll();

        // Tạo adapter và xử lý click
        adapter = new CategoryAdapter(categoryList, category -> {
            selected = category;
            edtId.setText(String.valueOf(category.getId()));
            edtName.setText(category.getName());
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Thêm danh mục
        btnAdd.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            if (!name.isEmpty()) {
                categoryDao.insert(new Category(0, name));
                refreshData();
            }
        });

        // Sửa danh mục
        btnUpdate.setOnClickListener(v -> {
            if (selected != null) {
                selected.setName(edtName.getText().toString());
                categoryDao.update(selected);
                refreshData();
            }
        });

        // Xóa danh mục
        btnDelete.setOnClickListener(v -> {
            if (selected != null) {
                categoryDao.delete(selected.getId());
                refreshData();
            }
        });
    }

    private void refreshData() {
        categoryList.clear();
        categoryList.addAll(categoryDao.getAll());
        adapter.notifyDataSetChanged();
        edtId.setText("");
        edtName.setText("");
        selected = null;
    }
}
