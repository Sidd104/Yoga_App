package com.example.yoga_app;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BmiActivity extends AppCompatActivity {

    private EditText editHeight, editWeight, editAge;
    private Spinner spinnerGender;
    private Button btnCalculate, btnReset;
    private TextView textBmiValue, textBmiStatus, textBmiMessage, textUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bmi);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editHeight = findViewById(R.id.editHeight);
        editWeight = findViewById(R.id.editWeight);
        editAge = findViewById(R.id.editAge);
        spinnerGender = findViewById(R.id.spinnerGender);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnReset = findViewById(R.id.btnReset);
        textBmiValue = findViewById(R.id.textBmiValue);
        textBmiStatus = findViewById(R.id.textBmiStatus);
        textBmiMessage = findViewById(R.id.textBmiMessage);
        textUserInfo = findViewById(R.id.textUserInfo);

        String[] genderList = {"Male", "Female", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                genderList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateBmi();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetFields();
            }
        });
    }

    private void calculateBmi() {

        String heightText = editHeight.getText().toString().trim();
        String weightText = editWeight.getText().toString().trim();
        String ageText = editAge.getText().toString().trim();
        String genderText = spinnerGender.getSelectedItem().toString();

        if (heightText.isEmpty()) {
            editHeight.setError("Enter height in cm");
            editHeight.requestFocus();
            return;
        }

        if (weightText.isEmpty()) {
            editWeight.setError("Enter weight in kg");
            editWeight.requestFocus();
            return;
        }

        if (ageText.isEmpty()) {
            editAge.setError("Enter age");
            editAge.requestFocus();
            return;
        }

        double heightCm = Double.parseDouble(heightText);
        double weightKg = Double.parseDouble(weightText);
        int age = Integer.parseInt(ageText);

        if (heightCm <= 0) {
            editHeight.setError("Height must be greater than 0");
            editHeight.requestFocus();
            return;
        }

        if (weightKg <= 0) {
            editWeight.setError("Weight must be greater than 0");
            editWeight.requestFocus();
            return;
        }

        if (age <= 0) {
            editAge.setError("Age must be greater than 0");
            editAge.requestFocus();
            return;
        }

        double heightMeter = heightCm / 100.0;
        double bmi = weightKg / (heightMeter * heightMeter);

        String bmiFormatted = String.format("%.2f", bmi);
        textBmiValue.setText(bmiFormatted);
        textUserInfo.setText("Age: " + age + "   |   Gender: " + genderText);

        if (bmi < 18.5) {
            textBmiStatus.setText("Underweight");
            textBmiStatus.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
            textBmiMessage.setText("Your BMI is below the normal range. Try to improve your nutrition and maintain a healthy workout routine.");
        } else if (bmi >= 18.5 && bmi < 25) {
            textBmiStatus.setText("Healthy");
            textBmiStatus.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            textBmiMessage.setText("Great job! Your BMI is in the healthy range. Keep maintaining your balanced diet and regular exercise.");
        } else if (bmi >= 25 && bmi < 30) {
            textBmiStatus.setText("Overweight");
            textBmiStatus.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
            textBmiMessage.setText("Your BMI is above the healthy range. Regular exercise and better food habits can help improve it.");
        } else {
            textBmiStatus.setText("Obese");
            textBmiStatus.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            textBmiMessage.setText("Your BMI is in the obese range. Please follow a healthy plan and consult a medical or fitness expert.");
        }
    }

    private void resetFields() {
        editHeight.setText("");
        editWeight.setText("");
        editAge.setText("");
        spinnerGender.setSelection(0);

        textBmiValue.setText("0.00");
        textBmiStatus.setText("Status");
        textBmiStatus.setTextColor(getResources().getColor(R.color.text_primary));
        textBmiMessage.setText("Enter your details to calculate BMI.");
        textUserInfo.setText("Age: --   |   Gender: --");
    }
}