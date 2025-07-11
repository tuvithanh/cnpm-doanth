package com.example.cnpm_thuchanh.Adapter;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnpm_thuchanh.Model.User;
import com.example.cnpm_thuchanh.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> list;
    private OnUserActionListener listener;

    public interface OnUserActionListener {
        void onEdit(User user);
        void onDelete(User user);
    }

    public UserAdapter(List<User> list, OnUserActionListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = list.get(position);
        holder.txtName.setText("Tên: " + user.getName());
        holder.txtUsername.setText("Username: " + user.getUsername());
        holder.txtRole.setText("Role: " + user.getRole());

        holder.itemView.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(v.getContext(), v);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_item_user, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_edit_user) {
                    listener.onEdit(user);
                } else if (item.getItemId() == R.id.menu_delete_user) {
                    listener.onDelete(user);
                }
                return true;
            });
            popup.show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtUsername, txtRole;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtRole = itemView.findViewById(R.id.txtRole);
        }
    }
}
