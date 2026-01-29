package com.example.yoga_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FoodActivityDeatils extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_food_deatils);

        textView = findViewById(R.id.text);
        String detailsStory = getIntent().getStringExtra("story");
        textView.setText(detailsStory);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // The modern fix for the red onBackPressed error
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                goBackToFoodList();
            }
        });
    }





    // Fixed typo: 'gooback' changed to 'goBack'
    public void goBack(View view) {
        goBackToFoodList();
    }

    private void goBackToFoodList() {
        Intent intent = new Intent(FoodActivityDeatils.this, FoodActivity.class);
        startActivity(intent);
        finish();
    }
}