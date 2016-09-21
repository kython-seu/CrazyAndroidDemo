package com.kason.chapter8;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.button2)
    Button button2;
    @Bind(R.id.button3)
    Button button3;
    @Bind(R.id.button4)
    Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, this.toString());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        /*Intent intent = new Intent(MainActivity.this,SingleTopActivity.class);
        startActivity(intent);*/
        //Intent intent = new Intent(this, SingleTaskActivity.class);
        //startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent != null){
            long time = intent.getLongExtra("time", 0);
            Log.i(TAG, "start onNewIntent time: "+time);

        }
        Log.i(TAG, "start onNewIntent");
    }

    @OnClick({R.id.button, R.id.button2, R.id.button3, R.id.button4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("time",System.currentTimeMillis());
                startActivity(intent);
                break;
            case R.id.button2:
                Intent intent2 = new Intent(this, SecondActivity.class);
                intent2.putExtra("time",System.currentTimeMillis());
                startActivity(intent2);
                break;
            case R.id.button3:
                break;
            case R.id.button4:
                break;
        }
    }
}
