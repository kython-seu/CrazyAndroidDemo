package com.kason.chapter8;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThirdActivity extends AppCompatActivity {

    @Bind(R.id.button6)
    Button button6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button6)
    public void onClick() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
