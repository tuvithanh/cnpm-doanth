package com.example.cnpm_thuchanh.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.cnpm_thuchanh.Model.Product;

import java.util.List;

public class BarChartView extends View {

    private List<Product> productList;
    private Paint barPaint, textPaint, axisPaint, labelPaint;

    public BarChartView(Context context, AttributeSet attrs) {
        super(context, attrs);

        barPaint = new Paint();
        barPaint.setColor(Color.parseColor("#03A9F4"));

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30f);
        textPaint.setTextAlign(Paint.Align.CENTER);

        axisPaint = new Paint();
        axisPaint.setColor(Color.GRAY);
        axisPaint.setStrokeWidth(4f);

        labelPaint = new Paint();
        labelPaint.setColor(Color.DKGRAY);
        labelPaint.setTextSize(28f);
        labelPaint.setTextAlign(Paint.Align.RIGHT); // Số bên trái
    }

    public void setData(List<Product> products) {
        this.productList = products;
        invalidate(); // Vẽ lại view
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (productList == null || productList.isEmpty()) return;

        int width = getWidth();
        int height = getHeight();
        int paddingLeft = 120; // chừa bên trái để vẽ số
        int paddingBottom = 100;
        int paddingTop = 50;
        int availableHeight = height - paddingBottom - paddingTop;

        int maxSold = 1;
        for (Product p : productList) {
            if (p.getSoldQuantity() > maxSold)
                maxSold = p.getSoldQuantity();
        }

        // Vẽ lưới ngang và số bên trái (chia thành 5 mức)
        int step = Math.max(1, maxSold / 5);
        for (int i = 0; i <= 5; i++) {
            int value = i * step;
            float y = height - paddingBottom - ((value * 1f / maxSold) * availableHeight);
            canvas.drawLine(paddingLeft, y, width - 50, y, axisPaint);
            canvas.drawText(String.valueOf(value), paddingLeft - 10, y + 10, labelPaint);
        }

        // Vẽ cột
        int barWidth = (width - paddingLeft - 50) / productList.size();
        int x = paddingLeft;

        for (Product p : productList) {
            float barHeight = (p.getSoldQuantity() * 1f / maxSold) * availableHeight;
            float left = x + 20;
            float right = left + barWidth - 40;
            float top = height - paddingBottom - barHeight;
            float bottom = height - paddingBottom;

            canvas.drawRect(left, top, right, bottom, barPaint);
            canvas.drawText(p.getName(), (left + right) / 2, height - paddingBottom + 40, textPaint);

            x += barWidth;
        }
    }
}
