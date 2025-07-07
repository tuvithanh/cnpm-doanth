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

        // Tạo order detail
        for (CartItem item : cartItems) {
            orderDetailDao.insertOrderDetail(orderId, item.getProduct().getId(), item.getQuantity(), item.getProduct().getPrice());
        }

        // Tạo payment
        paymentDao.insertPayment(orderId, paymentMethod, total);

        // Xóa giỏ hàng
        cartItemDao.clearCart(cart.getId());

        Toast.makeText(context, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
        return true;
    }
}

