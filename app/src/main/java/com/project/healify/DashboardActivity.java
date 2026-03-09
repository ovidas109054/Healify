package com.project.healify;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.activity.SystemBarStyle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.project.healify.data.repository.UserRepository;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DashboardActivity extends AppCompatActivity {

    @Inject UserRepository userRepository;
    private TextView tvWelcomeName;
    private CardView cardBookAppointment, cardVitals, cardRecords, cardConsultations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Fix for Top Notch covering information - Android 16 (API 36) compatible
        EdgeToEdge.enable(this, 
            SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
            SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        );
        
        setContentView(R.layout.activity_dashboard);

        tvWelcomeName = findViewById(R.id.tvWelcomeName);
        cardBookAppointment = findViewById(R.id.cardBookAppointment);
        cardVitals = findViewById(R.id.cardVitals);
        cardRecords = findViewById(R.id.cardRecords);
        cardConsultations = findViewById(R.id.cardConsultations);

        // Apply Window Insets to handle Notch/Status Bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.toolbar), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(v.getPaddingLeft(), systemBars.top, v.getPaddingRight(), v.getPaddingBottom());
            return insets;
        });

        // Load user data
        userRepository.getCurrentUser(user -> {
            if (user != null && user.name != null) {
                runOnUiThread(() -> tvWelcomeName.setText("Welcome back, " + user.name + "!"));
            }
        });

        // Set click listeners for the module's features
        cardBookAppointment.setOnClickListener(v -> {
            Toast.makeText(this, "Opening Appointment Booking...", Toast.LENGTH_SHORT).show();
        });

        cardVitals.setOnClickListener(v -> {
            startActivity(new Intent(this, VitalsActivity.class));
        });

        cardRecords.setOnClickListener(v -> {
            Toast.makeText(this, "Opening Medical Records...", Toast.LENGTH_SHORT).show();
        });

        cardConsultations.setOnClickListener(v -> {
            Toast.makeText(this, "Starting Online Consultation...", Toast.LENGTH_SHORT).show();
        });
    }
}
