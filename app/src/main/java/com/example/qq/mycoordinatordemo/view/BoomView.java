package com.example.qq.mycoordinatordemo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.example.qq.mycoordinatordemo.base.LittleBall;

import java.util.List;

/**
 * Created by Administrator on 2017/3/16 0016.
 */
public class BoomView extends View{
    private List<LittleBall> ballList;
    private Paint mBallPaint;
    private ValueAnimator mValueAnimator;
    private float mAnimValue;

    public BoomView(Context context) {
        this(context,null);
    }

    public BoomView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BoomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        mBallPaint = new Paint();
        mBallPaint.setStyle(Paint.Style.FILL);
        mValueAnimator = ValueAnimator.ofFloat(0,1f);
        mValueAnimator.setDuration(2000);
        mValueAnimator.setInterpolator(new BounceInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBall(canvas);
    }
    public void drawBall(Canvas canvas){
        if (null == ballList || ballList.size() <= 0){
            return;
        }
        for (int i = 0; i < ballList.size();i++){
            ballList.get(i).drawBall(canvas);
            if (mAnimValue != 0){
                ballList.get(i).setXY(mAnimValue);
                this.setAlpha(1 - mAnimValue);
            }
        }
    }
    public void startAnim(List<LittleBall> littleBalls) {
        this.ballList = littleBalls;
        if (mValueAnimator != null) {
            mValueAnimator.start();
        }
    }
}
