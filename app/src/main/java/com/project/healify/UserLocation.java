package com.project.healify;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.project.healify.data.repository.UserRepository;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class UserLocation extends AppCompatActivity {
    @Inject
    UserRepository userRepository;

    private Spinner spndivision, spndistrict, spnUpazila;
    private EditText edtPostalCode;
    private Button btnBack, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.user_location);

        spndivision = findViewById(R.id.spndivision);
        spndistrict = findViewById(R.id.spndistrict);
        spnUpazila = findViewById(R.id.spnUpazila);
        edtPostalCode = findViewById(R.id.edtPostalCode);
        btnBack = findViewById(R.id.userBack);
        btnNext = findViewById(R.id.btnusernext);

        btnBack.setOnClickListener(v -> finish());

        btnNext.setOnClickListener(v -> {
            if (validateInputs()) {
                saveDataAndNavigate();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private boolean validateInputs() {
        if (spndivision.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select a division", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (spndistrict.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select a district", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (spnUpazila.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select an upazila", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtPostalCode.getText().toString().trim().isEmpty()) {
            edtPostalCode.setError("Postal code is required");
            return false;
        }
        return true;
    }

    private void saveDataAndNavigate() {
        userRepository.getCurrentUser(user -> {
            if (user != null) {
                user.division = spndivision.getSelectedItem().toString();
                user.district = spndistrict.getSelectedItem().toString();
                user.upazila = spnUpazila.getSelectedItem().toString();
                user.postalCode = edtPostalCode.getText().toString().trim();
                userRepository.saveUser(user);
                
                runOnUiThread(() -> {
                    Intent intent = new Intent(UserLocation.this, Login_Ac3.class);
                    startActivity(intent);
                });
            } else {
                runOnUiThread(() -> Toast.makeText(this, "Error: User session not found", Toast.LENGTH_SHORT).show());
            }
        });
    }
}
