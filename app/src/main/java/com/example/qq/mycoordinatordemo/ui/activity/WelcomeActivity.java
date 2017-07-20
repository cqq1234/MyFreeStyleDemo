package com.example.qq.mycoordinatordemo.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.example.qq.mycoordinatordemo.MainActivity;
import com.example.qq.mycoordinatordemo.R;
import com.example.qq.mycoordinatordemo.view.DIYTextView;
import com.richpath.RichPath;
import com.richpath.RichPathView;
import com.richpathanimator.AnimationListener;
import com.richpathanimator.RichPathAnimator;


public class WelcomeActivity extends AppCompatActivity {

    private DIYTextView mDiyTextView;
    private RichPathView mIsAndroidPath;
    Handler mHandler = new Handler();
    //flag判断自动跳转还是手动跳转
    private boolean isSkip = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
    }
    private void initView(){
        mDiyTextView = (DIYTextView)findViewById(R.id.tv_count_down);
        mIsAndroidPath = (RichPathView)findViewById(R.id.ic_android);
        //Android机器人动画
        isAnimationAndroid();

        mDiyTextView.setTimes(5);
        mDiyTextView.beginRun();
        initListener();

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isSkip){
                        Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            },4000);

    }

    private void isAnimationAndroid(){
        final RichPath head = mIsAndroidPath.findRichPathByName("head");
        final RichPath body = mIsAndroidPath.findRichPathByName("body");
        final RichPath rEye = mIsAndroidPath.findRichPathByName("r_eye");
        final RichPath lEye = mIsAndroidPath.findRichPathByName("l_eye");
        final RichPath rHand = mIsAndroidPath.findRichPathByName("r_hand");
        final RichPath lHand = mIsAndroidPath.findRichPathByName("l_hand");

        RichPathAnimator.animate(head,rEye,lEye,body,rHand,lHand)
                .trimPathEnd(0,1).duration(800).animationListener(new AnimationListener() {
            @Override
            public void onStart() {
                head.setFillColor(Color.TRANSPARENT);
                body.setFillColor(Color.TRANSPARENT);
                rHand.setFillColor(Color.TRANSPARENT);
                lHand.setFillColor(Color.TRANSPARENT);
                rHand.setRotation(0);
            }

            @Override
            public void onStop() {

            }
        }).thenAnimate(head,rEye,lEye,body,rHand,lHand)
                .fillColor(Color.TRANSPARENT,0xFFa4c639)
                .interpolator(new AccelerateInterpolator())
                .duration(800)
                .thenAnimate(rHand)
                .rotation(-150)
                .duration(600)
                .thenAnimate(rHand)
                .rotation(-150, -130, -150, -130, -150, -130, -150)
                .duration(2000)
                .thenAnimate(rHand)
                .rotation(0)
                .duration(400)
                .start();
    }

    private void initListener(){
        mDiyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                isSkip = false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
