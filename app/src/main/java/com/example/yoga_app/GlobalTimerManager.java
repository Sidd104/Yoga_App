package com.example.yoga_app;

import android.os.CountDownTimer;

public class GlobalTimerManager {

    private static GlobalTimerManager instance;
    private CountDownTimer countDownTimer;
    private long timeLeftMillis;
    private boolean isRunning = false;

    public interface TimerListener {
        void onTick(long millisUntilFinished);
        void onFinish();
    }

    private TimerListener listener;

    private GlobalTimerManager() {}

    public static GlobalTimerManager getInstance() {
        if (instance == null) {
            instance = new GlobalTimerManager();
        }
        return instance;
    }

    public void setListener(TimerListener listener) {
        this.listener = listener;
    }

    public void startTimer(long durationMillis) {

        if (isRunning) return;

        timeLeftMillis = durationMillis;

        countDownTimer = new CountDownTimer(timeLeftMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftMillis = millisUntilFinished;
                if (listener != null) {
                    listener.onTick(millisUntilFinished);
                }
            }

            @Override
            public void onFinish() {
                isRunning = false;
                if (listener != null) {
                    listener.onFinish();
                }
            }
        }.start();

        isRunning = true;
    }

    public void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        isRunning = false;
    }
}