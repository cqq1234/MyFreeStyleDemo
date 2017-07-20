package com.example.qq.mycoordinatordemo.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.qq.mycoordinatordemo.R;
import com.example.qq.mycoordinatordemo.view.ActionMenu;
import com.example.qq.mycoordinatordemo.view.inter_face.OnActionItemClickListener;

public class JellyActivity extends AppCompatActivity {
    private ActionMenu actionMenuTop;
    private ActionMenu actionMenuBottom;
    private ActionMenu actionMenuMiddle;
    private ActionMenu actionMenuLeft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jelly);
        initView();
    }
    private void initView(){
        actionMenuTop = (ActionMenu)findViewById(R.id.actionMenuTop);
        actionMenuBottom = (ActionMenu)findViewById(R.id.actionMenuBottom);
        actionMenuMiddle = (ActionMenu)findViewById(R.id.actionMenuMiddle);
        actionMenuLeft = (ActionMenu)findViewById(R.id.actionMenuLeft);
        //add menu items
        actionMenuTop.addView(R.drawable.search,getItemColor(R.color.menuNormalInfo),getItemColor(R.color.menuPressInfo));
        actionMenuTop.addView(R.drawable.like,getItemColor(R.color.menuNormalRed),getItemColor(R.color.menuPressRed));
        actionMenuTop.addView(R.drawable.write);

        actionMenuBottom.addView(R.drawable.search,getItemColor(R.color.menuNormalInfo),getItemColor(R.color.menuPressInfo));
        actionMenuBottom.addView(R.drawable.like,getItemColor(R.color.menuNormalRed),getItemColor(R.color.menuPressRed));
        actionMenuBottom.addView(R.drawable.write);

        actionMenuMiddle.addView(R.drawable.search,getItemColor(R.color.menuNormalInfo),getItemColor(R.color.menuPressInfo));
        actionMenuMiddle.addView(R.drawable.like,getItemColor(R.color.menuNormalRed),getItemColor(R.color.menuPressRed));
        actionMenuMiddle.addView(R.drawable.write);

        actionMenuLeft.addView(R.drawable.search,getItemColor(R.color.menuNormalInfo),getItemColor(R.color.menuPressInfo));
        actionMenuLeft.addView(R.drawable.like,getItemColor(R.color.menuNormalRed),getItemColor(R.color.menuPressRed));
        actionMenuLeft.addView(R.drawable.write);

        actionMenuBottom.setItemClickListener(new OnActionItemClickListener() {
            @Override
            public void onItemClick(int index) {
                showMessage("Click" + index);
            }

            @Override
            public void onAnimationEnd(boolean isOpen) {
                // TODO: 2017/3/20 0020
            }
        });
    }
    private int getItemColor(int colorID){
        return getResources().getColor(colorID);
    }
    private void showMessage(String content){
        Toast.makeText(JellyActivity.this, content, Toast.LENGTH_SHORT).show();
    }
}
