package com.example.yoga_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProgressActivity extends AppCompatActivity {

    private TextView textTotalWorkouts;
    private TextView textTotalMinutes;
    private TextView textTotalCalories;
    private TextView textStreak;
    private TextView textTodayDate;
    private TextView textHistoryList;
    private TextView textChartInfo;
    private Button btnResetProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_progress);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("AiYoga_app");
        setSupportActionBar(toolbar);

        textTotalWorkouts = findViewById(R.id.textTotalWorkouts);
        textTotalMinutes = findViewById(R.id.textTotalMinutes);
        textTotalCalories = findViewById(R.id.textTotalCalories);
        textStreak = findViewById(R.id.textStreak);
        textTodayDate = findViewById(R.id.textTodayDate);
        textHistoryList = findViewById(R.id.textHistoryList);
        textChartInfo = findViewById(R.id.textChartInfo);
        btnResetProgress = findViewById(R.id.btnResetProgress);

        loadProgress();

        btnResetProgress.setOnClickListener(v -> {
            SharedPreferences preferences =
                    getSharedPreferences("progress_data", MODE_PRIVATE);

            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();

            loadProgress();
        });
    }

    private void loadProgress() {

        SharedPreferences preferences =
                getSharedPreferences("progress_data", MODE_PRIVATE);

        int totalWorkouts =
                preferences.getInt("total_workouts", 0);

        int totalMinutes =
                preferences.getInt("total_minutes", 0);

        int totalCalories =
                preferences.getInt("total_calories", 0);

        int streakDays =
                preferences.getInt("streak_days", 0);

        String historyText =
                preferences.getString(
                        "history_text",
                        "No workout history available yet."
                );

        String chartText =
                preferences.getString(
                        "chart_data",
                        "No chart data available yet."
                );

        textTotalWorkouts.setText(String.valueOf(totalWorkouts));
        textTotalMinutes.setText(String.valueOf(totalMinutes));
        textTotalCalories.setText(String.valueOf(totalCalories));
        textStreak.setText(String.valueOf(streakDays));

        String today =
                new SimpleDateFormat(
                        "dd MMM yyyy",
                        Locale.getDefault()
                ).format(new Date());

        textTodayDate.setText("Today: " + today);

        textHistoryList.setText(historyText);
        textChartInfo.setText(chartText);
    }
}