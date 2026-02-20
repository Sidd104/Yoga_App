package com.example.yoga_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class SecondActivity extends AppCompatActivity {

    int[] newArray;
    private AdView mAdView,mAdview1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);

        mAdView=findViewById(R.id.adView);
        AdRequest adRequest=new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mAdview1=findViewById(R.id.adView1);
        AdRequest adRequest1=new AdRequest.Builder().build();
        mAdview1.loadAd(adRequest1);






        Toolbar toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        newArray = new int[]{
                R.id.thing_1,
                R.id.thing_2,
                R.id.thing_3,
                R.id.thing_4,
                R.id.thing_5,
                R.id.thing_6,
                R.id.thing_7,
                R.id.thing_8,
                R.id.thing_9,
                R.id.thing_10,
                R.id.thing_11,
                R.id.thing_12,
                R.id.thing_13,
                R.id.thing_14,
                R.id.thing_15,
        };



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
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
    public void Imagebuttonclicked(View view) {

        for (int i = 0; i < newArray.length; i++) {
            if (view.getId() == newArray[i]) {
                int value = i + 1;
                Log.i("FIRST", String.valueOf(value));
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                intent.putExtra("value", String.valueOf(value));
                startActivity(intent);
            }
        }
    }
}