package com.example.qq.mycoordinatordemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/3/15 0015.
 */
public class DIYTextView extends TextView implements Runnable{
    private long mSecond;
    private boolean isRun = false;//是否启动的状态
    public DIYTextView(Context context) {
        super(context);
    }

    public DIYTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DIYTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化倒计时的时间
     * @param times
     */
    public void setTimes(long times){
        mSecond = times;
    }
    /**
     * 倒计时计算
     */
    private void computeTime(){
        mSecond--;
    }
    /**
     * 判断是否在倒计时
     * @return
     */
    public boolean isRun(){
        return isRun;
    }
    /**
     * 开始倒计时
     */
    public void beginRun(){
        this.isRun = true;
        run();
    }
    /**
     * 暂停倒计时
     */
    public void stopRun(){
        this.isRun = false;
    }
    @Override
    public void run() {
        if (isRun){
            computeTime();
            if (mSecond == 0){
                stopRun();
                return;
            }
            String strTime = mSecond + "秒后自动跳过";
            setText(strTime);
            postDelayed(this,1000);
        }else {
            removeCallbacks(this);
        }
    }

}
