package com.example.qq.mycoordinatordemo.utils.net;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.TextView;

import com.example.qq.mycoordinatordemo.R;


public class ImagePagerActivity extends FragmentActivity {
    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";

    private HackyViewPager mPager;
    private int pagerPosition;
    private TextView indicator;


    private boolean isLocal = false;
    private int getPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_detail_pager);

        isLocal = getIntent().getBooleanExtra("isLocal", false);
        getPosition = getIntent().getIntExtra("position", -1);
        pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        final String[] urls = getIntent().getStringArrayExtra(EXTRA_IMAGE_URLS);
        mPager = (HackyViewPager) this.findViewById(R.id.pager);
        ImagePagerAdapter mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), urls);
        mPager.setAdapter(mAdapter);
        indicator = (TextView) this.findViewById(R.id.indicator);
        indicator.setText((pagerPosition + 1) + "/" + urls.length);
        // 更新下标
        mPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                indicator.setText(arg0 + 1 + "/" + urls.length);
            }
        });
        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }
        mPager.setCurrentItem(pagerPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, mPager.getCurrentItem());
    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        public String[] fileList;

        public ImagePagerAdapter(FragmentManager fm, String[] fileList) {
            super(fm);
            this.fileList = fileList;
        }

        @Override
        public int getCount() {
            return fileList == null ? 0 : fileList.length;
        }

        @Override
        public Fragment getItem(int position) {
            String url = fileList[position];
            if (isLocal)
                return ImageDetailFragment.newInstance(url, isLocal, getPosition);
            else
                return ImageDetailFragment.newInstance(url);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //当你不知道在什么时候还会再次用到这个引用的时候请不要手动释放bitmap（屏蔽以下代码）,否则会出现Canvas：try to use the bitmap has been recycled.
//        //释放mImageView的bitmap
//        Drawable drawable = ImageDetailFragment.mImageView.getDrawable();
//        if (drawable != null && drawable instanceof BitmapDrawable) {
//            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
//            Bitmap bitmap = bitmapDrawable.getBitmap();
//            if (bitmap != null && !bitmap.isRecycled()) {
//                bitmap.recycle();
//            }
//        }

    }
}