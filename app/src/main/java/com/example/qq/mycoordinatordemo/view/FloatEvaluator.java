package com.example.qq.mycoordinatordemo.view;

import android.animation.TypeEvaluator;

/**
 * Created by Administrator on 2017/3/20 0020.
 */
public class FloatEvaluator implements TypeEvaluator{

    private float[] value;
    private float dampingFactor = 5.f;
    private float velocityFactor = 30.f;
    private int sum;

    public FloatEvaluator(long time,float startValue,float endValue) {
        sum = (int)time * 60 / 1000;
        float diff = endValue - startValue;
        value = new float[sum];
        float x;
        for (int i = 0; i < sum;i++){
            x = i * 1.0f / sum;
            value[i] = endValue - (float)(diff * Math.pow(Math.E,-1 * dampingFactor * x) * Math.cos(velocityFactor * x));
        }
    }

    @Override
    public Object evaluate(float v, Object o, Object t1) {
        return value[(int)((sum - 1) * v)];
    }
}
