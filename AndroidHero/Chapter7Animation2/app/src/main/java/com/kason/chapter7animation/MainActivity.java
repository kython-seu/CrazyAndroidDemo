package com.kason.chapter7animation;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private String text;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.text);
        //Alpha();
        //rotate();
        //trans1();
        //trans2();
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1.0f,0.5f,1.0f,0.5f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f
        );
        scaleAnimation.setDuration(10000);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.i(TAG, "onAnimationStart: Start");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.i(TAG, "onAnimationEnd: End");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.i(TAG, "onAnimationRepeat: Repeat");
            }
        });
        textView.startAnimation(scaleAnimation);

        f2();
    }

    private void f2() {
        for(int i=0;i<128;i++){
            Log.i(TAG, "f2: -->"+i);
            text ="String"+i;
            textView.setText(text);
        }
        f1();
    }

    private void f1() {
        for(String s:new String[]{"hehe","hahha"}){
            Log.i(TAG, "f1: s--->"+s);
            text = s;
            textView.setText(text);
        }
    }

    private void trans2() {
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.ABSOLUTE,textView.getX(),Animation.RELATIVE_TO_PARENT,0.5f
                ,Animation.ABSOLUTE,textView.getY(),Animation.RELATIVE_TO_SELF,3.0f
        );
        translateAnimation.setDuration(5000);
        textView.startAnimation(translateAnimation);
    }

    private void trans1() {
        TranslateAnimation translateAnimation = new TranslateAnimation(0
                ,(float)(getResources().getDisplayMetrics().heightPixels*0.5),0,0);
        translateAnimation.setDuration(5000);
        textView.startAnimation(translateAnimation);
    }

    private void rotate() {
        RotateAnimation rotateAnimation = new RotateAnimation(0,360,100,100);
        rotateAnimation.setDuration(5000);
        textView.startAnimation(rotateAnimation);
    }

    private void Alpha() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f,1.0f);
        alphaAnimation.setDuration(3000);
        textView.startAnimation(alphaAnimation);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        Log.i(TAG," == onConfigurationChanged");

    }
}
