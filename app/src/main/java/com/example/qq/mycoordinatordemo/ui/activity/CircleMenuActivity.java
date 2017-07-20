package com.example.qq.mycoordinatordemo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.qq.mycoordinatordemo.R;
import com.imangazaliev.circlemenu.CircleMenu;
import com.imangazaliev.circlemenu.CircleMenuButton;

/**
 * Created by Administrator on 2017/3/21 0021.
 */
public class CircleMenuActivity extends AppCompatActivity{
    private Handler handler = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circlemenu);
        CircleMenu circleMenu = (CircleMenu)findViewById(R.id.circleMenu);
        final Intent intent = new Intent();
        circleMenu.setOnItemClickListener(new CircleMenu.OnItemClickListener() {
            @Override
            public void onItemClick(CircleMenuButton menuButton) {
                switch (menuButton.getId()){
                    case R.id.favorite:
                        showMessage("you clicked 线性布局...");
                        intent.setClass(CircleMenuActivity.this,RecycleActivity.class);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(intent);
                            }
                        },1000);
                        break;
                    case R.id.search:
                        showMessage("you clicked 滑动显示隐藏布局...");
                        intent.setClass(CircleMenuActivity.this,RecycleOneActivity.class);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(intent);
                            }
                        },1000);
                        break;
                    case R.id.alert:
                        showMessage("you clicked 网格布局...");
                        intent.setClass(CircleMenuActivity.this,RecycleGridActivity.class);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(intent);
                            }
                        },1000);
                        break;
                    case R.id.place:
                        showMessage("you clicked 瀑布流布局...");
                        intent.setClass(CircleMenuActivity.this,RecycleStaggeredActivity.class);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(intent);
                            }
                        },1000);
                        break;
                    case R.id.edit:
                        showMessage("you clicked edit...");
                        break;
                }

            }
        });
        circleMenu.setStateUpdateListener(new CircleMenu.OnStateUpdateListener() {
            @Override
            public void onMenuExpanded() {
                Log.d("CircleMenuStatus", "Expanded");
            }

            @Override
            public void onMenuCollapsed() {
                Log.d("CircleMenuStatus", "Collapsed");
            }
        });
    }
    private void showMessage(String content){
        Toast.makeText(CircleMenuActivity.this, content, Toast.LENGTH_SHORT).show();
    }
}
