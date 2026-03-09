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
public class Login_Ac3 extends AppCompatActivity {

    @Inject UserRepository userRepository;
    private Button btnBack2, btnNext2;
    private EditText edtName, edtAge, edtWeight, edtFeet, edtInches;
    private Spinner spnblood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_ac3);

        btnBack2 = findViewById(R.id.btnBack2);
        btnNext2 = findViewById(R.id.btnNext2);
        edtName = findViewById(R.id.edtName);
        edtAge = findViewById(R.id.edtAge);
        edtWeight = findViewById(R.id.edtWeight);
        edtFeet = findViewById(R.id.edtFeet);
        edtInches = findViewById(R.id.edtInches);
        spnblood = findViewById(R.id.spnblood);

        btnBack2.setOnClickListener(v -> onBackPressed());

        btnNext2.setOnClickListener(v -> {
            if (validateInputs()) {
                userRepository.getCurrentUser(user -> {
                    if (user != null) {
                        user.name = edtName.getText().toString().trim();
                        user.age = Integer.parseInt(edtAge.getText().toString().trim());
                        user.weight = Double.parseDouble(edtWeight.getText().toString().trim());
                        user.bloodGroup = spnblood.getSelectedItem().toString();
                        user.height = edtFeet.getText().toString() + "'" + edtInches.getText().toString();
                        userRepository.saveUser(user);
                    }
                });

                Toast.makeText(this, "Profile Saved to Secure DB", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Wellcome_Ac4.class));
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Only apply padding to the view, do not consume insets to allow status bar to be visible
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private boolean validateInputs() {
        if (edtName.getText().toString().trim().isEmpty()) {
            edtName.setError("Name is required");
            return false;
        }
        if (edtAge.getText().toString().trim().isEmpty()) {
            edtAge.setError("Age is required");
            return false;
        }
        if (edtWeight.getText().toString().trim().isEmpty()) {
            edtWeight.setError("Weight is required");
            return false;
        }
        if (edtFeet.getText().toString().trim().isEmpty()) {
            edtFeet.setError("Feet is required");
            return false;
        }
        if (edtInches.getText().toString().trim().isEmpty()) {
            edtInches.setError("Inches is required");
            return false;
        }
        return true;
    }
}
