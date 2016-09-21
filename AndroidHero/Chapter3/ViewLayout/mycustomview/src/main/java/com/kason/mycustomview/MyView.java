package com.kason.mycustomview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by kason_zhang on 9/19/2016.
 */
public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    int mCircleXY;
    float mRadius;
    RectF mArcRectF;
    @Override
    protected void onDraw(Canvas canvas) {
        int length = getResources().getDisplayMetrics().widthPixels;//the width of the pingmu
        //circle,圆心坐标和半径
        mCircleXY = length/2;
        mRadius = (float)(length*0.5/2);
        //arcle
        mArcRectF = new RectF((float)(length*0.1),(float)(length*0.1)
        ,(float)(length*0.9),(float)(length*0.9));
        Paint mCiclePaint = new Paint();
        mCiclePaint.setAntiAlias(false);
        mCiclePaint.setColor(Color.RED);
        canvas.drawCircle(mCircleXY,mCircleXY,mRadius,mCiclePaint);

        Paint mArcPaint = new Paint();
        mArcPaint.setAntiAlias(false);
        mArcPaint.setColor(Color.GREEN);
        int mSweepAngle = 70;
        canvas.drawArc(mArcRectF,270,mSweepAngle,false,mArcPaint);
    }
}
