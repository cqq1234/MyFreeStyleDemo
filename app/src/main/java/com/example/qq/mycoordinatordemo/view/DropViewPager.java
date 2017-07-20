package com.example.qq.mycoordinatordemo.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.qq.mycoordinatordemo.view.inter_face.Touchable;

/**
 * Created by Administrator on 2017/3/17 0017.
 */
public class DropViewPager extends ViewPager implements Touchable{
    private boolean touchable = true;
    public DropViewPager(Context context) {
        super(context);
    }

    public DropViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setTouchable(boolean touchable) {
        this.touchable = touchable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (touchable)
        return super.onTouchEvent(ev);
        else
            return true;
    }
}
