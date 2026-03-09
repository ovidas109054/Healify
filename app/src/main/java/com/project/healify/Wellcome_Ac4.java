package com.project.healify;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.activity.SystemBarStyle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.project.healify.data.repository.UserRepository;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class Wellcome_Ac4 extends AppCompatActivity {

    @Inject UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        EdgeToEdge.enable(this, 
            SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
            SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        );

        setContentView(R.layout.wellcome_ac4);

        TextView tvGreeting = findViewById(R.id.tvGreeting);
        Button btnDashboard = findViewById(R.id.btnDashboard);

        // Retrieve User profile from Secure Room DB via Repository
        userRepository.getCurrentUser(user -> {
            runOnUiThread(() -> {
                if (user != null && user.name != null && !user.name.isEmpty()) {
                    tvGreeting.setText("Hello, " + user.name + " 👋");
                } else {
                    tvGreeting.setText("Hello, 👋");
                }
            });
        });

        // Updated button to navigate to DashboardActivity (the real app home)
        btnDashboard.setOnClickListener(v -> {
            startActivity(new Intent(this, DashboardActivity.class));
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}