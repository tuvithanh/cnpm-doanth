package com.example.cnpm_thuchanh.View;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cnpm_thuchanh.Dao.ProductDao;
import com.example.cnpm_thuchanh.Model.Product;
import com.example.cnpm_thuchanh.R;
import com.example.cnpm_thuchanh.View.BarChartView;

import java.util.List;

public class ThongKeActivity extends AppCompatActivity {

    private BarChartView barChartView;
    private ProductDao productDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);

        barChartView = findViewById(R.id.barChartView);
        productDao = new ProductDao(this);

        List<Product> topProducts = productDao.getTopSellingProducts(5);
        barChartView.setData(topProducts);
    }
}
