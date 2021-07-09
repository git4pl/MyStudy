package com.pltech.study.android;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.wb.study.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_startActivity)
                .setOnClickListener(v -> {
                    Intent intent = new Intent(MainActivity.this, TargetActivity.class);
                    startActivity(intent);
                });
    }
}
