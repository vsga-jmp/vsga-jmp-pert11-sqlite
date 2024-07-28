package com.sqlite.sqlitevsga;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sqlite.sqlitevsga.model.User;
import com.sqlite.sqlitevsga.repository.UserRepository;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemListNameAdapter itemListNameAdapter;
    private UserRepository userRepository;
    private FloatingActionButton btnAddItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initalizeViews();
        initializeOnClickListeners();
    }

    private void initalizeViews() {
        recyclerView = findViewById(R.id.recyclerViewItemName);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnAddItem = findViewById(R.id.btnAddItem);
    }

    private void initializeOnClickListeners() {
        btnAddItem.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddUserActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserData();
    }

    private void loadUserData() {
        userRepository = new UserRepository(this);
        userRepository.open();

        List<User> userList = userRepository.getAllUsers();
        if (itemListNameAdapter == null) {
            itemListNameAdapter = new ItemListNameAdapter(userList, this::showOptionsDialog);
            recyclerView.setAdapter(itemListNameAdapter);
        } else {
            itemListNameAdapter.updateUserList(userList);
            itemListNameAdapter.notifyDataSetChanged();
        }

        userRepository.close();
    }

    private void showOptionsDialog(User user) {
        new AlertDialog.Builder(this)
                .setTitle("Mau ngapain, nih?")
                .setItems(new String[]{"Update", "Delete"}, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            Intent intent = new Intent(MainActivity.this, EditUserActivity.class);
                            intent.putExtra("USER_ID", user.getId());
                            startActivity(intent);
                            break;
                        case 1:
                            deleteUser(user);
                            break;
                    }
                })
                .show();
    }

    private void deleteUser(User user) {
        userRepository.open();
        userRepository.deleteUser(user.getId());
        userRepository.close();
        loadUserData();
        Toast.makeText(this, "User deleted", Toast.LENGTH_SHORT).show();
    }
}
