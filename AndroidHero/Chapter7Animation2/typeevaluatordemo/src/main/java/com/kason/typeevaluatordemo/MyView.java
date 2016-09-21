package com.kason.typeevaluatordemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by kason_zhang on 9/21/2016.
 */
public class MyView extends View {
    private Paint mPaint;
    private static final int RADIUS = 50;
    private Point currentPoint;
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(currentPoint == null){
            currentPoint = new Point(RADIUS,RADIUS);
            float x = currentPoint.getX();
            float y = currentPoint.getY();
            canvas.drawCircle(x,y,RADIUS,mPaint);
            startAnimation();
        }else{
            float x = currentPoint.getX();
            float y = currentPoint.getY();
            canvas.drawCircle(x,y,RADIUS,mPaint);
        }

    }
    private void startAnimation(){
        Point start = new Point(RADIUS,RADIUS);
        Point end = new Point(getWidth()-RADIUS,getHeight()-RADIUS);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new MyTypeEvaluator(),start,end);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point)animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(5000);
        valueAnimator.start();
    }
}
