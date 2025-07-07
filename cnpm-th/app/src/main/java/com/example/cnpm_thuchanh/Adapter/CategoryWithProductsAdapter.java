package com.example.cnpm_thuchanh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnpm_thuchanh.Dao.ProductDao;
import com.example.cnpm_thuchanh.Model.Category;
import com.example.cnpm_thuchanh.Model.Product;
import com.example.cnpm_thuchanh.R;

import java.util.List;

public class CategoryWithProductsAdapter extends RecyclerView.Adapter<CategoryWithProductsAdapter.CategoryViewHolder> {

    private Context context;
    private List<Category> categoryList;
    private ProductDao productDao;

    public CategoryWithProductsAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
        this.productDao = new ProductDao(context);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category_with_products, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.txtCategory.setText(category.getName());

        // Load sản phẩm theo danh mục
        List<Product> productList = productDao.getByCategory(category.getId());
        ProductHomeAdapter productAdapter = new ProductHomeAdapter(productList);  // ✅ dùng giao diện mới

        holder.recyclerProduct.setLayoutManager(
                new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerProduct.setAdapter(productAdapter);
    }


    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategory;
        RecyclerView recyclerProduct;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCategory = itemView.findViewById(R.id.txtCategoryName);
            recyclerProduct = itemView.findViewById(R.id.recyclerProductHorizontal);
        }
    }
}

