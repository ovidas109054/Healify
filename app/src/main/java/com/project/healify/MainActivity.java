package com.project.healify;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.project.healify.data.repository.UserRepository;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.mainactivity);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Splash screen delay: 1.5 seconds then check user state for UX
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            userRepository.getCurrentUser(user -> {
                runOnUiThread(() -> {
                    if (user != null && user.name != null && !user.name.isEmpty()) {
                        // User already exists, go to Welcome screen
                        startActivity(new Intent(MainActivity.this, Wellcome_Ac4.class));
                    } else {
                        // New user, go to Login
                        startActivity(new Intent(MainActivity.this, Login_Ac1.class));
                    }
                    finish();
                });
            });
        }, 1500);
    }
}