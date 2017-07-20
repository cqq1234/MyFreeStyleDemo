package com.example.qq.mycoordinatordemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.qq.mycoordinatordemo.base.LittleBall;
import com.example.qq.mycoordinatordemo.ui.activity.ButterKnifeActivity;
import com.example.qq.mycoordinatordemo.ui.activity.CircleMenuActivity;
import com.example.qq.mycoordinatordemo.ui.activity.DropActivity;
import com.example.qq.mycoordinatordemo.ui.activity.JellyActivity;
import com.example.qq.mycoordinatordemo.view.BoomView;
import com.example.qq.mycoordinatordemo.view.ParabolaView;
import com.richpath.RichPath;
import com.richpath.RichPathView;
import com.richpathanimator.RichPathAnimator;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ParabolaView.AnimEndInterface {
    private RelativeLayout rl;
    private ParabolaView parabolaView;
    private BoomView boomView;
    //点击进入波纹指示器
    private Button btnDrop;
    //点击进入果冻效果页面
    private Button btnJelly;
    //点击进入圆形菜单
    private Button btnCircle;
    //ButterKnife(特性)使用
    private Button btnKnife;
    //vector动画效果
    private RichPathView mIsCommand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        boomView = (BoomView)findViewById(R.id.boomView);
        rl = (RelativeLayout)findViewById(R.id.rl);
        parabolaView = (ParabolaView)findViewById(R.id.parent_panel);
        mIsCommand = (RichPathView)findViewById(R.id.ic_command);
        rl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    parabolaView.startAnim((int) motionEvent.getX(),(int) motionEvent.getY());
                }
                return true;
            }
        });
        btnDrop = (Button)findViewById(R.id.drop_indicator);
        btnDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DropActivity.class);
                startActivity(intent);
            }
        });
        btnJelly = (Button)findViewById(R.id.jelly_activity);
        btnJelly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, JellyActivity.class);
                startActivity(intent);
            }
        });
        btnCircle = (Button)findViewById(R.id.circle_menu);
        btnCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this, CircleMenuActivity.class);
                startActivity(in);
            }
        });
        btnKnife = (Button)findViewById(R.id.butter_knife);
        btnKnife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this, ButterKnifeActivity.class);
                startActivity(in);
            }
        });
        animationCommand();
    }

    private void animationCommand(){
        final RichPath part1 = mIsCommand.findRichPathByName("part1");
        final RichPath part2 = mIsCommand.findRichPathByName("part2");
        final RichPath part3 = mIsCommand.findRichPathByName("part3");
        final RichPath part4 = mIsCommand.findRichPathByName("part4");
        final RichPath part5 = mIsCommand.findRichPathByName("part5");
        final RichPath part6 = mIsCommand.findRichPathByName("part6");
        final RichPath part7 = mIsCommand.findRichPathByName("part7");
        final RichPath part8 = mIsCommand.findRichPathByName("part8");

        RichPathAnimator.animate(part1).trimPathOffset(0,1.0f)
                .andAnimate(part2)
                .trimPathOffset(0.125f, 1.125f)

                .andAnimate(part3)
                .trimPathOffset(0.250f, 1.250f)

                .andAnimate(part4)
                .trimPathOffset(0.375f, 1.375f)

                .andAnimate(part5)
                .trimPathOffset(0.500f, 1.500f)

                .andAnimate(part6)
                .trimPathOffset(0.625f, 1.625f)

                .andAnimate(part7)
                .trimPathOffset(0.750f, 1.750f)

                .andAnimate(part8)
                .trimPathOffset(0.875f, 1.875f)

                .durationSet(2000)
                .repeatModeSet(RichPathAnimator.RESTART)
                .repeatCountSet(RichPathAnimator.INFINITE)
                .interpolatorSet(new LinearInterpolator())
                .start();
    }

    @Override
    public void onDrawBall(List<LittleBall> littleBalls) {
        boomView.startAnim(littleBalls);
    }
}
