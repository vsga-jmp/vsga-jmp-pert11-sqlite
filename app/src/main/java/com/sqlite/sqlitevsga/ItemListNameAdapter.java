package com.sqlite.sqlitevsga;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sqlite.sqlitevsga.model.User;

import java.util.List;

public class ItemListNameAdapter extends RecyclerView.Adapter<ItemListNameAdapter.ItemViewHolder> {

    private List<User> userList;

    public ItemListNameAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_name, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        User user = userList.get(position);
        holder.tvName.setText(user.getName());
        holder.tvDomisili.setText(user.getDomisili());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void updateUserList(List<User> userList) {
        this.userList = userList;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvDomisili;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDomisili = itemView.findViewById(R.id.tvDomisili);
        }
    }
}
