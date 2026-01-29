package com.example.yoga_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FoodActivity extends AppCompatActivity {

    ListView listView;
    String[] titleStory;
    String[] detailStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_food);

        titleStory = getResources().getStringArray(R.array.title_story);
        detailStory = getResources().getStringArray(R.array.details_stroy);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row, R.id.rowtext, titleStory);
        listView.setAdapter(adapter);

        // Fixed with Lambda and correct logic
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String storyContent = detailStory[position];
            Intent intent = new Intent(FoodActivity.this, FoodActivityDeatils.class);
            intent.putExtra("story", storyContent);
            startActivity(intent);
        });

        // The modern fix for onBackPressed
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                goBackToMain();
            }
        });
    }

    public void foodGoBack(View view) {
        goBackToMain();
    }

    private void goBackToMain() {
        Intent intent = new Intent(FoodActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}