package com.project.healify;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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
public class Login_Ac2 extends AppCompatActivity {

    @Inject UserRepository userRepository;
    private Button btnBack, btnNext, btnPatient, btnDoctor, btnMedicn_ltd, btnHospital;
    private String selectedRole = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_ac2);

        btnBack = findViewById(R.id.btnBack);
        btnNext = findViewById(R.id.btnLogin);
        btnPatient = findViewById(R.id.btnPatient);
        btnDoctor = findViewById(R.id.btnDoctor);
        btnMedicn_ltd = findViewById(R.id.btnMedicn_ltd);
        btnHospital = findViewById(R.id.btnHospital);

        btnPatient.setOnClickListener(v -> {
            selectedRole = "Patient";
            updateButtonSelection(btnPatient);
        });

        btnDoctor.setOnClickListener(v -> {
            selectedRole = "Doctor";
            updateButtonSelection(btnDoctor);
        });

        btnMedicn_ltd.setOnClickListener(v -> {
            selectedRole = "Medicen Ltd.";
            updateButtonSelection(btnMedicn_ltd);
        });

        btnHospital.setOnClickListener(v -> {
            selectedRole = "Hospital";
            updateButtonSelection(btnHospital);
        });

        btnBack.setOnClickListener(v -> onBackPressed());

        btnNext.setOnClickListener(v -> {
            if (selectedRole.isEmpty()) {
                Toast.makeText(this, "Please select a role", Toast.LENGTH_SHORT).show();
                return;
            }

            userRepository.getCurrentUser(user -> {
                if (user != null) {
                    user.role = selectedRole;
                    userRepository.saveUser(user);
                }
            });

            if (selectedRole.equals("Patient")) {
                startActivity(new Intent(this, UserLocation.class));
            } else {
                Toast.makeText(this, "Only Patient & Doctor flow is available", Toast.LENGTH_SHORT).show();
            }

            if (selectedRole.equals("Doctor")) {
                startActivity(new Intent(this, DoctorLogin_Ac_1.class));
            } else {
                Toast.makeText(this, "Only Patient & Doctor flow is available", Toast.LENGTH_SHORT).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void updateButtonSelection(Button selected) {
        btnPatient.setBackgroundTintList(getResources().getColorStateList(R.color.teal_700));
        btnDoctor.setBackgroundTintList(getResources().getColorStateList(R.color.teal_700));
        btnMedicn_ltd.setBackgroundTintList(getResources().getColorStateList(R.color.teal_700));
        btnHospital.setBackgroundTintList(getResources().getColorStateList(R.color.teal_700));
        selected.setBackgroundTintList(getResources().getColorStateList(R.color.black));
    }
}
