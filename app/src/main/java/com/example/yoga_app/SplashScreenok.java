package com.example.yoga_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashScreenok extends AppCompatActivity {

    Animation up, down;
    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_splash_screen);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ✅ Initialize views CORRECTLY
        imageView = findViewById(R.id.appsplash);
        textView = findViewById(R.id.appname);

        // ✅ Load animations
        up = AnimationUtils.loadAnimation(this, R.anim.up);
        down = AnimationUtils.loadAnimation(this, R.anim.down);

        // ✅ Apply animations to correct views
        imageView.startAnimation(up);
        textView.startAnimation(down);

        // ✅ Move to MainActivity after 3.5 seconds
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreenok.this, MainActivity.class));
            finish();
        }, 3500);
    }
}