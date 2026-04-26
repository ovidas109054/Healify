package com.project.healify;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Patient_Dash extends AppCompatActivity {

    private Button btnBookDoctor, btnReorder, btnRecords, btnHospital, btnBMI, btnReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.patient_dash);

        // Initialize the 6 Smart Action buttons
        btnBookDoctor = findViewById(R.id.btn_book_doctor);
        btnReorder = findViewById(R.id.btn_reorder);
        btnRecords = findViewById(R.id.btn_records);
        btnHospital = findViewById(R.id.btn_hospital);
        btnBMI = findViewById(R.id.btn_BMI);
        btnReminder = findViewById(R.id.btn_Reminder);

        // Set click listeners
        btnBookDoctor.setOnClickListener(v -> Toast.makeText(this, "Book Doctor Clicked", Toast.LENGTH_SHORT).show());
        btnReorder.setOnClickListener(v -> Toast.makeText(this, "Reorder Clicked", Toast.LENGTH_SHORT).show());
        btnRecords.setOnClickListener(v -> Toast.makeText(this, "Records Clicked", Toast.LENGTH_SHORT).show());
        btnHospital.setOnClickListener(v -> Toast.makeText(this, "Hospital Clicked", Toast.LENGTH_SHORT).show());
        btnBMI.setOnClickListener(v -> Toast.makeText(this, "BMI Calculator Clicked", Toast.LENGTH_SHORT).show());
        btnReminder.setOnClickListener(v -> Toast.makeText(this, "Set Reminder Clicked", Toast.LENGTH_SHORT).show());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
