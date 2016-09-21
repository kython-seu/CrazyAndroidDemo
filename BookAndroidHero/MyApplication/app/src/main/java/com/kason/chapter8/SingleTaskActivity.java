package com.kason.chapter8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SingleTaskActivity extends AppCompatActivity {

    private static final String TAG = "SingleTaskActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, this.toString());
        setContentView(R.layout.activity_single_task);
        Intent intent = new Intent(this,SingleTopActivity.class);
        startActivity(intent);
    }
}
