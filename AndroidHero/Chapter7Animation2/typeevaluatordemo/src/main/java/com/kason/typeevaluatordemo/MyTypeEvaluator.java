package com.kason.typeevaluatordemo;

import android.animation.TypeEvaluator;

/**
 * Created by kason_zhang on 9/21/2016.
 */
public class MyTypeEvaluator implements TypeEvaluator<Point> {
    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {
        //Point point =
        float x = fraction*(endValue.getX()-startValue.getX())+startValue.getX();
        float y = fraction*(endValue.getY()-startValue.getY())+startValue.getY();
        Point point = new Point(x,y);
        return point;
    }
}
