package com.example.yoga_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    Button button1, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button1 = findViewById(R.id.startyoga1);
        button2 = findViewById(R.id.startyoga2);

        button1.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SecondActivity.class)));

        button2.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SecondActivity2.class)));
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.id_Privacy) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.termsfeed.com/live/privacy-policy")));
            return true;
        }

        if (id == R.id.id_tarm) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.termsfeed.com/live/terms-and-conditions")));
            return true;
        }

        if (id == R.id.rate) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + getPackageName())));
            } catch (Exception ex) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
            }
            return true;
        }

        if (id == R.id.more) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/dev?id=9197857236982036353&hl=en_IN")));
            return true;
        }

        if (id == R.id.share) {
            Intent myintent = new Intent(Intent.ACTION_SEND);
            myintent.setType("text/plain");
            String sharebody =
                    "This is Best App for Yoga\n" +
                            "By this app Stretch Your Body\n" +
                            "Free Download Now:\n" +
                            "https://play.google.com/store/apps/details?id=" + getPackageName();
            myintent.putExtra(Intent.EXTRA_SUBJECT, "Yoga App");
            myintent.putExtra(Intent.EXTRA_TEXT, sharebody);
            startActivity(Intent.createChooser(myintent, "Share using"));
            return true;
        }

        return true;
    }

    public void beforeage18(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }

    public void afterage18(View view) {
        startActivity(new Intent(this, SecondActivity2.class));
    }

    public void food(View view) {
        startActivity(new Intent(this, FoodActivity.class));
    }
}