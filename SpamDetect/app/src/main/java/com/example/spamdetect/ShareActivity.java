package com.example.spamdetect;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;

public class ShareActivity extends AppCompatActivity {
    Button shareapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        shareapp = findViewById(R.id.shareapp);
        //generate apk file and share the app via selected communication app
        shareapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationInfo api =getApplicationContext().getApplicationInfo();
                String apkpath = api.sourceDir;
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("application/vnd.android.package-aechive");
                intent.putExtra(intent.EXTRA_STREAM, Uri.fromFile(new File(apkpath)));
                startActivity(Intent.createChooser(intent,"Share via"));

            }
        });


    }
}