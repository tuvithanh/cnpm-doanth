package com.example.cnpm_thuchanh.Admin.Product;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cnpm_thuchanh.Dao.CategoryDao;
import com.example.cnpm_thuchanh.Dao.ProductDao;
import com.example.cnpm_thuchanh.Model.Category;
import com.example.cnpm_thuchanh.Model.Product;
import com.example.cnpm_thuchanh.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

public class AddProductActivity extends AppCompatActivity {

    EditText edtName, edtDesc, edtPrice;
    ImageView imgPreview;
    Button btnAdd, btnSelectImage;
    Spinner spinnerCategory;

    private Uri selectedImageUri;
    private String savedImagePath;

    ProductDao productDao;
    CategoryDao categoryDao;
    List<Category> categoryList;

    private static final int PICK_IMAGE_REQUEST = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        edtName = findViewById(R.id.edtName);
        edtDesc = findViewById(R.id.edtDescription);
        edtPrice = findViewById(R.id.edtPrice);
        imgPreview = findViewById(R.id.imgPreview);
        btnAdd = findViewById(R.id.btnAdd);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        spinnerCategory = findViewById(R.id.spinnerCategory);

        productDao = new ProductDao(this);
        categoryDao = new CategoryDao(this);

        // Load categories
        categoryList = categoryDao.getAll();
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categoryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        btnSelectImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        btnAdd.setOnClickListener(v -> {
            if (savedImagePath == null) {
                Toast.makeText(this, "Vui lòng chọn ảnh!", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = edtName.getText().toString();
            String desc = edtDesc.getText().toString();
            double price = Double.parseDouble(edtPrice.getText().toString());
            int cateId = ((Category) spinnerCategory.getSelectedItem()).getId();

            Product product = new Product(0, cateId, name, desc, price, savedImagePath);
            productDao.insert(product);

            Toast.makeText(this, "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            savedImagePath = copyImageToInternalStorage(selectedImageUri);
            if (savedImagePath != null) {
                imgPreview.setImageURI(Uri.fromFile(new File(savedImagePath)));
            }
        }
    }

    private String copyImageToInternalStorage(Uri uri) {
        try {
            ContentResolver resolver = getContentResolver();
            String fileName = getFileName(uri);
            File file = new File(getFilesDir(), fileName);

            try (InputStream in = resolver.openInputStream(uri);
                 FileOutputStream out = new FileOutputStream(file)) {

                byte[] buffer = new byte[1024];
                int len;

                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }

                return file.getAbsolutePath();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi lưu ảnh", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private String getFileName(Uri uri) {
        String result = "image_" + System.currentTimeMillis() + ".jpg";
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int idx = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            if (idx >= 0) result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}
