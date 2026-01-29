package com.example.yoga_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {

    private TextView startBtn, mtextView;
    private CountDownTimer countDownTimer;
    private boolean isTimerRunning = false;
    private long timeLeftMillis;
    private int buttonValue = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ---------- GET VALUE ----------
        Intent intent = getIntent();
        String value = intent.getStringExtra("value");
        if (value != null) {
            buttonValue = Integer.parseInt(value);
        }

        // ---------- SET LAYOUT ----------
        switch (buttonValue) {
            case 1: setContentView(R.layout.activity_bow1); break;
            case 2: setContentView(R.layout.activity_bow2); break;
            case 3: setContentView(R.layout.activity_bow3); break;
            case 4: setContentView(R.layout.activity_bow4); break;
            case 5: setContentView(R.layout.activity_bow5); break;
            case 6: setContentView(R.layout.activity_bow6); break;
            case 7: setContentView(R.layout.activity_bow7); break;
            case 8: setContentView(R.layout.activity_bow8); break;
            case 9: setContentView(R.layout.activity_bow9); break;
            case 10: setContentView(R.layout.activity_bow10); break;
            case 11: setContentView(R.layout.activity_bow11); break;
            case 12: setContentView(R.layout.activity_bow12); break;
            case 13: setContentView(R.layout.activity_bow13); break;
            case 14: setContentView(R.layout.activity_bow14); break;
            case 15: setContentView(R.layout.activity_bow15); break;
            default: setContentView(R.layout.activity_bow1);
        }

        // ---------- VIEWS ----------
        startBtn = findViewById(R.id.startbutton);
        mtextView = findViewById(R.id.time);

        // ---------- CLICK ----------
        startBtn.setOnClickListener(v -> {
            if (isTimerRunning) {
                stopTimer();
            } else {
                startTimer();
            }
        });
    }

    private void startTimer() {
        String timeText = mtextView.getText().toString(); // "01:00"
        int minutes = Integer.parseInt(timeText.substring(0, 2));
        int seconds = Integer.parseInt(timeText.substring(3, 5));

        timeLeftMillis = (minutes * 60 + seconds) * 1000L;

        countDownTimer = new CountDownTimer(timeLeftMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftMillis = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                int nextValue = buttonValue + 1;
                if (nextValue > 15) nextValue = 1;

                Intent i = new Intent(ThirdActivity.this, ThirdActivity.class);
                i.putExtra("value", String.valueOf(nextValue));
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        }.start();

        isTimerRunning = true;
        startBtn.setText("PAUSE");
    }

    private void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        isTimerRunning = false;
        startBtn.setText("START");
    }

    private void updateTimer() {
        int minutes = (int) (timeLeftMillis / 60000);
        int seconds = (int) (timeLeftMillis % 60000 / 1000);

        String time =
                (minutes < 10 ? "0" : "") + minutes + ":" +
                        (seconds < 10 ? "0" : "") + seconds;

        mtextView.setText(time);
    }
}