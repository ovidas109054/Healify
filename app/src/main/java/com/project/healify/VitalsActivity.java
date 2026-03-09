package com.project.healify;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.activity.SystemBarStyle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.textfield.TextInputEditText;
import com.project.healify.data.local.HealthDataEntity;
import com.project.healify.data.local.UserDao;
import com.project.healify.data.repository.UserRepository;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@AndroidEntryPoint
public class VitalsActivity extends AppCompatActivity {

    @Inject UserRepository userRepository;
    @Inject UserDao userDao;
    
    private TextInputEditText etHeartRate, etWeight;
    private Button btnSaveVitals;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        EdgeToEdge.enable(this, 
            SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
            SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        );
        
        setContentView(R.layout.activity_vitals);

        etHeartRate = findViewById(R.id.etHeartRate);
        etWeight = findViewById(R.id.etWeight);
        btnSaveVitals = findViewById(R.id.btnSaveVitals);

        btnSaveVitals.setOnClickListener(v -> {
            String hrStr = etHeartRate.getText().toString();
            String weightStr = etWeight.getText().toString();

            if (hrStr.isEmpty() || weightStr.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int heartRate = Integer.parseInt(hrStr);
            double weight = Double.parseDouble(weightStr);

            userRepository.getCurrentUser(user -> {
                if (user != null) {
                    HealthDataEntity data = new HealthDataEntity(
                        user.email, weight, heartRate, System.currentTimeMillis()
                    );
                    executorService.execute(() -> {
                        userDao.insertHealthData(data);
                        runOnUiThread(() -> {
                            Toast.makeText(VitalsActivity.this, "Vitals saved successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    });
                }
            });
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_vitals_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
