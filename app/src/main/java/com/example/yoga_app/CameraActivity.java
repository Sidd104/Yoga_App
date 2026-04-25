package com.example.yoga_app;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.pose.PoseDetection;
import com.google.mlkit.vision.pose.PoseDetector;
import com.google.mlkit.vision.pose.accurate.AccuratePoseDetectorOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ExperimentalGetImage
public class CameraActivity extends AppCompatActivity
        implements GlobalTimerManager.TimerListener,
        PoseOverlay.BodyListener {

    private static final int PERMISSION_CODE = 100;

    private PreviewView previewView;
    private PoseOverlay poseOverlay;
    private TextView timerText;
    private ImageView exerciseImage;

    private PoseDetector poseDetector;
    private ExecutorService cameraExecutor;

    private long durationMillis;
    private int poseValue;

    private SpeechRecognizer speechRecognizer;
    private TextToSpeech tts;

    private boolean timerStarted = false;
    private boolean bodyVisible = false;

    private Handler handler = new Handler();
    private Runnable reminderRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        previewView = findViewById(R.id.previewView);
        poseOverlay = findViewById(R.id.poseOverlay);
        timerText = findViewById(R.id.cameraTimer);
        exerciseImage = findViewById(R.id.exerciseImage);

        poseOverlay.setBodyListener(this);

        poseValue = Integer.parseInt(getIntent().getStringExtra("value"));
        setExerciseImage();

        String timeString = getIntent().getStringExtra("time");

        int minutes = Integer.parseInt(timeString.substring(0, 2));
        int seconds = Integer.parseInt(timeString.substring(3, 5));
        durationMillis = (minutes * 60 + seconds) * 1000L;

        GlobalTimerManager.getInstance().setListener(this);

        cameraExecutor = Executors.newSingleThreadExecutor();

        AccuratePoseDetectorOptions options =
                new AccuratePoseDetectorOptions.Builder()
                        .setDetectorMode(AccuratePoseDetectorOptions.STREAM_MODE)
                        .build();

        poseDetector = PoseDetection.getClient(options);

        initTTS();
        checkPermissions();
    }

    private void setExerciseImage() {

        int imageRes;

        switch (poseValue) {
            case 1:
                imageRes = R.drawable.exercise_1;
                break;
            case 2:
                imageRes = R.drawable.exercise_2;
                break;
            case 3:
                imageRes = R.drawable.exercise_3;
                break;
            case 4:
                imageRes = R.drawable.exercise_4;
                break;
            case 5:
                imageRes = R.drawable.exercise_5;
                break;
            case 6:
                imageRes = R.drawable.exercise_6;
                break;
            case 7:
                imageRes = R.drawable.exercise_7;
                break;
            case 8:
                imageRes = R.drawable.exercise_8;
                break;
            case 9:
                imageRes = R.drawable.exercise_9;
                break;
            case 10:
                imageRes = R.drawable.exercise_10;
                break;
            case 11:
                imageRes = R.drawable.exercise_11;
                break;
            case 12:
                imageRes = R.drawable.exercise_12;
                break;
            case 13:
                imageRes = R.drawable.exercise_13;
                break;
            case 14:
                imageRes = R.drawable.exercise_14;
                break;
            case 15:
                imageRes = R.drawable.exercise_15;
                break;
            default:
                imageRes = R.drawable.exercise_1;
                break;
        }

        exerciseImage.setImageResource(imageRes);
    }

    private void saveProgress() {

        SharedPreferences preferences = getSharedPreferences("progress_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        int totalWorkouts = preferences.getInt("total_workouts", 0);
        int totalMinutes = preferences.getInt("total_minutes", 0);
        int totalCalories = preferences.getInt("total_calories", 0);
        int streakDays = preferences.getInt("streak_days", 0);

        String lastCompletedDate = preferences.getString("last_completed_date", "");
        String todayDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());

        int completedMinutes = (int) (durationMillis / 60000);
        if (completedMinutes <= 0) {
            completedMinutes = 1;
        }

        int calories = completedMinutes * 5;

        totalWorkouts = totalWorkouts + 1;
        totalMinutes = totalMinutes + completedMinutes;
        totalCalories = totalCalories + calories;

        if (lastCompletedDate.isEmpty()) {
            streakDays = 1;
        } else if (!todayDate.equals(lastCompletedDate)) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                Date lastDate = format.parse(lastCompletedDate);
                Date today = format.parse(todayDate);

                Calendar lastCalendar = Calendar.getInstance();
                Calendar todayCalendar = Calendar.getInstance();

                lastCalendar.setTime(lastDate);
                todayCalendar.setTime(today);

                long difference = todayCalendar.getTimeInMillis() - lastCalendar.getTimeInMillis();
                long daysDifference = difference / (24 * 60 * 60 * 1000);

                if (daysDifference == 1) {
                    streakDays = streakDays + 1;
                } else if (daysDifference > 1) {
                    streakDays = 1;
                }

            } catch (Exception e) {
                streakDays = streakDays + 1;
            }
        }

        String formattedDate = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());
        String historyText = preferences.getString("history_text", "");
        String newHistoryLine = formattedDate + " - Workout " + poseValue + " - " + completedMinutes + " min - " + calories + " cal";

        if (historyText.isEmpty()) {
            historyText = newHistoryLine;
        } else {
            historyText = newHistoryLine + "\n" + historyText;
        }

        String chartData = preferences.getString("chart_data", "");
        String todayChartLine = formattedDate + " : " + completedMinutes + " min";

        if (!chartData.contains(formattedDate + " :")) {
            if (chartData.isEmpty()) {
                chartData = todayChartLine;
            } else {
                chartData = chartData + "\n" + todayChartLine;
            }
        }

        editor.putString("last_completed_date", todayDate);
        editor.putInt("total_workouts", totalWorkouts);
        editor.putInt("total_minutes", totalMinutes);
        editor.putInt("total_calories", totalCalories);
        editor.putInt("streak_days", streakDays);
        editor.putString("history_text", historyText);
        editor.putString("chart_data", chartData);
        editor.apply();
    }

    // ================= TEXT TO SPEECH =================

    private void initTTS() {
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.US);
                tts.setSpeechRate(0.9f);
                startReminderLoop();
            }
        });
    }

    private void speak(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    // ================= REMINDER LOOP =================

    private void startReminderLoop() {

        handler.removeCallbacksAndMessages(null);

        reminderRunnable = new Runnable() {
            @Override
            public void run() {

                if (!timerStarted) {

                    if (!bodyVisible) {
                        speak("Please come on the screen.");
                    } else {
                        speak("Say start to begin exercise.");
                    }
                }

                handler.postDelayed(this, 5000);
            }
        };

        handler.post(reminderRunnable);
    }

    // ================= PERMISSIONS =================

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.RECORD_AUDIO
                    },
                    PERMISSION_CODE
            );
        } else {
            startCamera();
            setupVoice();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        startCamera();
        setupVoice();
    }

    // ================= CAMERA =================

    private void startCamera() {

        ListenableFuture<ProcessCameraProvider> future =
                ProcessCameraProvider.getInstance(this);

        future.addListener(() -> {
            try {

                ProcessCameraProvider provider = future.get();

                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                ImageAnalysis analysis =
                        new ImageAnalysis.Builder()
                                .setBackpressureStrategy(
                                        ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                .build();

                analysis.setAnalyzer(cameraExecutor, imageProxy -> {

                    if (imageProxy.getImage() == null) {
                        imageProxy.close();
                        return;
                    }

                    InputImage image =
                            InputImage.fromMediaImage(
                                    imageProxy.getImage(),
                                    imageProxy.getImageInfo().getRotationDegrees()
                            );

                    poseDetector.process(image)
                            .addOnSuccessListener(pose ->
                                    runOnUiThread(() ->
                                            poseOverlay.setPose(pose)))
                            .addOnCompleteListener(task ->
                                    imageProxy.close());
                });

                provider.unbindAll();
                provider.bindToLifecycle(
                        this,
                        CameraSelector.DEFAULT_FRONT_CAMERA,
                        preview,
                        analysis
                );

            } catch (Exception e) {
                e.printStackTrace();
            }

        }, ContextCompat.getMainExecutor(this));
    }

    // ================= VOICE =================

    private void setupVoice() {

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        speechRecognizer.setRecognitionListener(new RecognitionListener() {

            @Override public void onReadyForSpeech(Bundle params) {}
            @Override public void onBeginningOfSpeech() {}
            @Override public void onRmsChanged(float rmsdB) {}
            @Override public void onBufferReceived(byte[] buffer) {}
            @Override public void onEndOfSpeech() {}

            @Override
            public void onResults(Bundle results) {

                ArrayList<String> matches =
                        results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                if (matches != null) {
                    for (String command : matches) {

                        command = command.toLowerCase().trim();

                        // START
                        if (command.equals("start")) {

                            if (!bodyVisible) {
                                speak("Please first come on the screen.");
                                break;
                            }

                            if (!timerStarted) {
                                timerStarted = true;

                                handler.removeCallbacks(reminderRunnable);

                                speak("Time has been started.");

                                GlobalTimerManager.getInstance()
                                        .startTimer(durationMillis);
                            }
                            break;
                        }

                        // STOP
                        if (command.equals("stop") && timerStarted) {

                            timerStarted = false;

                            GlobalTimerManager.getInstance().stopTimer();

                            speak("Time has been stopped.");

                            startReminderLoop();
                            break;
                        }

                        // HOW
                        if (command.contains("how")) {

                            List<String> steps =
                                    ExerciseInstructions.get(poseValue);

                            if (steps != null) {

                                new Thread(() -> {
                                    try {
                                        for (String step : steps) {
                                            runOnUiThread(() -> speak(step));
                                            Thread.sleep(4000);
                                        }
                                    } catch (Exception ignored) {
                                    }
                                }).start();
                            }
                            break;
                        }
                    }
                }

                speechRecognizer.startListening(intent);
            }

            @Override
            public void onError(int error) {
                speechRecognizer.startListening(intent);
            }

            @Override public void onPartialResults(Bundle partialResults) {}
            @Override public void onEvent(int eventType, Bundle params) {}
        });

        speechRecognizer.startListening(intent);
    }

    // ================= BODY DETECTION =================

    @Override
    public void onBodyDetected(boolean detected) {

        bodyVisible = detected;

        if (!detected) {

            if (timerStarted) {
                timerStarted = false;
                GlobalTimerManager.getInstance().stopTimer();
            }

            speak("Please come on the screen.");

            startReminderLoop();
        }
    }

    // ================= TIMER =================

    @Override
    public void onTick(long millisUntilFinished) {

        int minutes = (int) (millisUntilFinished / 60000);
        int seconds = (int) (millisUntilFinished % 60000 / 1000);

        String time =
                (minutes < 10 ? "0" : "") + minutes + ":" +
                        (seconds < 10 ? "0" : "") + seconds;

        timerText.setText(time);
    }

    @Override
    public void onFinish() {
        saveProgress();

        Intent i = new Intent(this, ThirdActivity.class);
        i.putExtra("value", String.valueOf(poseValue + 1));
        startActivity(i);
        finish();
    }

    @Override
    protected void onDestroy() {
        GlobalTimerManager.getInstance().stopTimer();
        if (speechRecognizer != null) speechRecognizer.destroy();
        if (tts != null) tts.shutdown();
        cameraExecutor.shutdown();
        super.onDestroy();
    }
}