package com.sqlite.sqlitevsga;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sqlite.sqlitevsga.model.User;
import com.sqlite.sqlitevsga.repository.UserRepository;

public class EditUserActivity extends AppCompatActivity {

    private EditText etName, etDomisili;
    private ImageButton btnBack;
    private Button btnSubmit;
    private UserRepository userRepository;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        userRepository = new UserRepository(this);
        userId = getIntent().getLongExtra("USER_ID", -1);         if (userId == -1) {
            Toast.makeText(this, "Invalid user ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initalizeViews();
        initializeOnClickListeners();
        loadUserData();
    }

    private void initalizeViews() {
        etName = findViewById(R.id.etName);
        etDomisili = findViewById(R.id.etDomisili);
        btnBack = findViewById(R.id.btnBack);
        btnSubmit = findViewById(R.id.btnSubmit);
    }

    private void initializeOnClickListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnSubmit.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String domisili = etDomisili.getText().toString().trim();

            if (name.isEmpty() || domisili.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            } else {
                updateDataToSQLite(userId, name, domisili);
                Toast.makeText(this, "User data updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void loadUserData() {
        userRepository.open();
        User user = userRepository.getUserById(userId);
        userRepository.close();

        if (user != null) {
            etName.setText(user.getName());
            etDomisili.setText(user.getDomisili());
        } else {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void updateDataToSQLite(long id, String name, String domisili) {
        userRepository.open();
        userRepository.updateUser(id, name, domisili);
        userRepository.close();
    }
}
