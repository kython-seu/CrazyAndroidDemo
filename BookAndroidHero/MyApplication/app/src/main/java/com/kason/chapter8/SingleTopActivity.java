package com.kason.chapter8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SingleTopActivity extends AppCompatActivity {
    private static final String TAG = "SingleTopActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, this.toString());
        setContentView(R.layout.activity_single_top);
        /*Intent intent = new Intent(this,SingleTopActivity.class);
        startActivity(intent);*/
        Intent intent = new Intent(this,SingleTaskActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i(TAG, "onNewIntent: --->onNewIntent");
        super.onNewIntent(intent);
    }
}
