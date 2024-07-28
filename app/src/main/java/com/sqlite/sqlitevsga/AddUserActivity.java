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

import com.sqlite.sqlitevsga.repository.UserRepository;

public class AddUserActivity extends AppCompatActivity {

    private EditText etName, etDomisili;
    private ImageButton btnBack;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initalizeViews();
        initializeOnClickListeners();
    }

    void initializeOnClickListeners() {
        btnBack.setOnClickListener(v -> {
            finish();  // Close activity and return to MainActivity
        });

        btnSubmit.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String domisili = etDomisili.getText().toString();

            if (name.isEmpty() || domisili.isEmpty()) {
                Toast.makeText(AddUserActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            } else {
                insertDataToSQLite(name, domisili);
                Toast.makeText(AddUserActivity.this, "User data submitted", Toast.LENGTH_SHORT).show();
                finish();  // Close activity and return to MainActivity
            }
        });
    }

    void initalizeViews() {
        etName = findViewById(R.id.etName);
        etDomisili = findViewById(R.id.etDomisili);
        btnBack = findViewById(R.id.btnBack);
        btnSubmit = findViewById(R.id.btnSubmit);
    }

    void insertDataToSQLite(String name, String domisili) {
        UserRepository userRepository = new UserRepository(this);
        userRepository.open();
        userRepository.createUser(name, domisili);
        userRepository.close();
    }
}
