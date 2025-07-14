package com.example.cnpm_thuchanh.DatabaseHelper;

import android.content.Context;
import android.widget.Toast;

import com.example.cnpm_thuchanh.Dao.CartDao;
import com.example.cnpm_thuchanh.Dao.CartItemDao;
import com.example.cnpm_thuchanh.Dao.OrderDao;
import com.example.cnpm_thuchanh.Dao.OrderDetailDao;
import com.example.cnpm_thuchanh.Dao.PaymentDao;
import com.example.cnpm_thuchanh.Model.Cart;
import com.example.cnpm_thuchanh.Model.CartItem;

import java.util.List;

public class CheckoutHelper {

    private Context context;
    private CartDao cartDao;
    private CartItemDao cartItemDao;
    private OrderDao orderDao;
    private OrderDetailDao orderDetailDao;
    private PaymentDao paymentDao;

    public CheckoutHelper(Context context) {
        this.context = context;
        cartDao = new CartDao(context);
        cartItemDao = new CartItemDao(context);
        orderDao = new OrderDao(context);
        orderDetailDao = new OrderDetailDao(context);
        paymentDao = new PaymentDao(context);
    }

    public boolean checkoutCart(int userId, String paymentMethod) {
        Cart cart = cartDao.getOrCreateCart(userId);
        List<CartItem> cartItems = cartItemDao.getItemsByCartId(cart.getId());

        if (cartItems.isEmpty()) {
            Toast.makeText(context, "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Tính tổng tiền
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }

        // Tạo order
        int orderId = orderDao.insertOrder(userId, total);
        if (orderId == -1) return false;

        // Tạo order detail + cập nhật sold_quantity
        for (CartItem item : cartItems) {
            int productId = item.getProduct().getId();
            int quantity = item.getQuantity();
            double price = item.getProduct().getPrice();

            // 1. Thêm chi tiết đơn hàng
            orderDetailDao.insertOrderDetail(orderId, productId, quantity, price);

            // 2. Cập nhật sold_quantity
            updateSoldQuantity(productId, quantity);
        }


        // Tạo payment
        paymentDao.insertPayment(orderId, paymentMethod, total);

        // Xóa giỏ hàng
        cartItemDao.clearCart(cart.getId());

        Toast.makeText(context, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
        return true;
    }
    private void updateSoldQuantity(int productId, int quantity) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        try {
            dbHelper.getWritableDatabase().execSQL(
                    "UPDATE Product SET sold_quantity = IFNULL(sold_quantity, 0) + ? WHERE id = ?",
                    new Object[]{quantity, productId}
            );
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Lỗi cập nhật thống kê sản phẩm", Toast.LENGTH_SHORT).show();
        } finally {
            dbHelper.close();
        }
    }

}

