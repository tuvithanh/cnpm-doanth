package com.example.cnpm_thuchanh.Admin.Product;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cnpm_thuchanh.Dao.CategoryDao;
import com.example.cnpm_thuchanh.Dao.ProductDao;
import com.example.cnpm_thuchanh.Model.Category;
import com.example.cnpm_thuchanh.Model.Product;
import com.example.cnpm_thuchanh.R;

import java.io.*;
import java.util.List;

public class EditProductActivity extends AppCompatActivity {

    EditText edtName, edtDescription, edtPrice, edtImage;
    Spinner spinnerCategory;
    ImageView imgPreview;
    Button btnSelectImage, btnUpdate;
    Uri selectedImageUri;
    int productId = -1;
    ProductDao productDao;
    CategoryDao categoryDao;
    List<Category> categoryList;
    Product productToEdit;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        edtName = findViewById(R.id.edtName);
        edtDescription = findViewById(R.id.edtDescription);
        edtPrice = findViewById(R.id.edtPrice);
        edtImage = findViewById(R.id.edtImage);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        imgPreview = findViewById(R.id.imgPreview);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnUpdate = findViewById(R.id.btnUpdate);

        productDao = new ProductDao(this);
        categoryDao = new CategoryDao(this);
        productId = getIntent().getIntExtra("product_id", -1);

        if (productId == -1) {
            Toast.makeText(this, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadCategories();
        loadProductData();

        btnSelectImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        btnUpdate.setOnClickListener(v -> {
            String name = edtName.getText().toString();
            String desc = edtDescription.getText().toString();
            double price = Double.parseDouble(edtPrice.getText().toString());
            int cateId = ((Category) spinnerCategory.getSelectedItem()).getId();
            String imagePath = edtImage.getText().toString();

            productToEdit.setName(name);
            productToEdit.setDescription(desc);
            productToEdit.setPrice(price);
            productToEdit.setCateId(cateId);
            productToEdit.setImagePath(imagePath);

            productDao.update(productToEdit);
            Toast.makeText(this, "Đã cập nhật sản phẩm", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void loadCategories() {
        categoryList = categoryDao.getAll();
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
    }

    private void loadProductData() {
        for (Product p : productDao.getAll()) {
            if (p.getId() == productId) {
                productToEdit = p;
                break;
            }
        }

        if (productToEdit != null) {
            edtName.setText(productToEdit.getName());
            edtDescription.setText(productToEdit.getDescription());
            edtPrice.setText(String.valueOf(productToEdit.getPrice()));
            edtImage.setText(productToEdit.getImagePath());

            File imgFile = new File(productToEdit.getImagePath());
            if (imgFile.exists()) {
                imgPreview.setImageBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
            }

            for (int i = 0; i < categoryList.size(); i++) {
                if (categoryList.get(i).getId() == productToEdit.getCateId()) {
                    spinnerCategory.setSelection(i);
                    break;
                }
            }
        }
    }

    private String saveImageToInternalStorage(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            File directory = new File(getFilesDir(), "images");
            if (!directory.exists()) directory.mkdir();

            String fileName = "img_" + System.currentTimeMillis() + ".jpg";
            File file = new File(directory, fileName);
            FileOutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outputStream.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                String path = saveImageToInternalStorage(selectedImageUri);
                if (path != null) {
                    edtImage.setText(path);
                    imgPreview.setImageBitmap(BitmapFactory.decodeFile(path));
                }
            }
        }
    }
}
