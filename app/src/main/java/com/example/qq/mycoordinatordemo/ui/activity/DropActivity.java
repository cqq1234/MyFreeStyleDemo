package com.example.qq.mycoordinatordemo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.example.qq.mycoordinatordemo.R;
import com.example.qq.mycoordinatordemo.ui.fragment.BlankFragment;
import com.example.qq.mycoordinatordemo.view.DropIndicator;
import com.example.qq.mycoordinatordemo.view.DropViewPager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/17 0017.
 */
public class DropActivity extends AppCompatActivity{
    private DropViewPager mViewPager;
    private DropIndicator mCircleIndicator;
    private FragmentPagerAdapter mAdapter;
    private ArrayList<Fragment> mTabContents = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop);
        mViewPager = (DropViewPager)findViewById(R.id.viewPager_drop);
        mCircleIndicator = (DropIndicator)findViewById(R.id.circleIndicator);
        mTabContents.add(BlankFragment.newInstance("0",0));
        mTabContents.add(BlankFragment.newInstance("1",1));
        mTabContents.add(BlankFragment.newInstance("2",2));
        mTabContents.add(BlankFragment.newInstance("3",3));
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mTabContents.get(position);
            }

            @Override
            public int getCount() {
                return mTabContents.size();
            }
        };
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(mAdapter);
        mCircleIndicator.setViewPager(mViewPager);
    }
}
