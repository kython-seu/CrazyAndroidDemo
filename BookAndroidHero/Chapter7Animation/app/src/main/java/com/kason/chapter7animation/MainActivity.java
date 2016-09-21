package com.kason.chapter7animation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = (ImageView)findViewById(R.id.image);
        imageView.setImageResource(R.mipmap.ic_launcher);
        textView = (TextView)findViewById(R.id.text);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f,1.0f);
        alphaAnimation.setDuration(5000);
        textView.startAnimation(alphaAnimation);
        //alphaAnimation.start();

    }
}
