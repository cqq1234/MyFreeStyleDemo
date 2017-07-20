package com.example.qq.mycoordinatordemo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qq.mycoordinatordemo.R;
import com.example.qq.mycoordinatordemo.base.BeautifulGirl;
import com.example.qq.mycoordinatordemo.utils.Define;
import com.example.qq.mycoordinatordemo.utils.MyOkHttp;
import com.example.qq.mycoordinatordemo.utils.SnackBarUtil;
import com.example.qq.mycoordinatordemo.utils.net.ImagePagerActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
public class RecycleActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private CoordinatorLayout coordinatorLayout;
    private MyAdapter mAdapter;
    private List<BeautifulGirl> beautifulGirls;
    private LinearLayoutManager mLayoutManager;
    private int lastVisibleItem ;
    private int page = 1;
    private ItemTouchHelper itemTouchHelper;
    private int screenWidth;
    private SwipeRefreshLayout swipeRefreshLayout;
    //存放图片地址的数组
    private static String[] mAllImgUrlArray;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //初始化ImageLoad
        Define.initImageLoader(this);

        initView();
        setListener();
        new GetData().execute("http://gank.io/api/data/福利/10/1");
        //获取屏幕宽度
        WindowManager wm = (WindowManager)RecycleActivity.this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
    }
    private void initView(){
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.linear_coordinator);
        recyclerView = (RecyclerView)findViewById(R.id.line_recycle);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.line_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark,R.color.colorAccent);
        swipeRefreshLayout.setProgressViewOffset(false,0,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
    }
    private void setListener(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                new GetData().execute("http://gank.io/api/data/福利/10/1");
            }
        });
        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

                int dragFlags = 0,swipeFlags = 0;
                if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager){
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                }else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager){
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    //设置侧滑方向为从左到右和从右到左都可以
                    swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                }
                return makeMovementFlags(dragFlags,swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                Collections.swap(beautifulGirls,from,to);
                mAdapter.notifyItemMoved(from,to);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mAdapter.removeItem(viewHolder.getAdapterPosition());
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG){
                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(Color.WHITE);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                viewHolder.itemView.setAlpha(1 - Math.abs(dX) / screenWidth);
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //  0：当前屏幕停止滚动；1时：屏幕在滚动 且 用户仍在触碰或手指还在屏幕上；2时：随用户的操作，屏幕上产生的惯性滑动；
                // 滑动状态停止并且剩余两个item时自动加载
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 2 >= mLayoutManager.getItemCount()){
                    new GetData().execute("http://gank.io/api/data/福利/10/"+(++page));
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }
    private class GetData extends AsyncTask<String,Integer,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... params) {
            return MyOkHttp.get(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (!TextUtils.isEmpty(result)){
            JSONObject jsonObject;
            Gson gson = new Gson();
            String jsonData = null;
            try {
                jsonObject = new JSONObject(result);
                jsonData = jsonObject.getString("results");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (beautifulGirls == null || beautifulGirls.size() == 0){
                beautifulGirls = gson.fromJson(jsonData,new TypeToken<List<BeautifulGirl>>() {}.getType());
                BeautifulGirl pages = new BeautifulGirl();
                pages.setPage(page);
                beautifulGirls.add(pages);
            }else {
                List<BeautifulGirl> more = gson.fromJson(jsonData,new TypeToken<List<BeautifulGirl>>() {}.getType());
                beautifulGirls.addAll(more);
                BeautifulGirl pages = new BeautifulGirl();
                pages.setPage(page);
                beautifulGirls.add(pages);
            }
                mAllImgUrlArray = new String[beautifulGirls.size()];
                for (int j = 0; j < beautifulGirls.size(); j++){
                    mAllImgUrlArray[j] = beautifulGirls.get(j).getUrl();
                }
            if (mAdapter == null){
                recyclerView.setAdapter(mAdapter = new MyAdapter());
                itemTouchHelper.attachToRecyclerView(recyclerView);
            }else {
                mAdapter.notifyDataSetChanged();
            }
        }
            swipeRefreshLayout.setRefreshing(false);
      }
    }
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements View.OnClickListener{
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(RecycleActivity.this).inflate(R.layout.line_meizi_item,parent,false);
            MyViewHolder holder = new MyViewHolder(view);
            view.setOnClickListener(this);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText("图" + position);
            Picasso.with(RecycleActivity.this).load(beautifulGirls.get(position).getUrl()).into(holder.iv);
        }

        @Override
        public int getItemCount() {
            return beautifulGirls.size();
        }

        @Override
        public void onClick(View view) {
            int position = recyclerView.getChildAdapterPosition(view);
            //查看大图
            Intent toLookBigTitleImg = new Intent(RecycleActivity.this, ImagePagerActivity.class);
            toLookBigTitleImg.putExtra("image_index", position);
            toLookBigTitleImg.putExtra("image_urls", mAllImgUrlArray);
            toLookBigTitleImg.putExtra("isLocal", false);
            startActivity(toLookBigTitleImg);
            SnackBarUtil.ShortSnackbar(coordinatorLayout,"点击第" + position + "个",SnackBarUtil.Info).show();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            private ImageView iv;
            private TextView tv;
            public MyViewHolder(View itemView) {
                super(itemView);
                iv = (ImageView)itemView.findViewById(R.id.line_item_iv);
                tv = (TextView)itemView.findViewById(R.id.line_item_tv);
            }
        }
        public void addItem(BeautifulGirl girl,int position){
            beautifulGirls.add(position,girl);
            notifyItemInserted(position);
            recyclerView.scrollToPosition(position);
        }
        public void removeItem(final int position){
            final BeautifulGirl removed = beautifulGirls.get(position);
            beautifulGirls.remove(position);
            notifyItemRemoved(position);
            SnackBarUtil.ShortSnackbar(coordinatorLayout,"你删除了第" + position + "个item",SnackBarUtil.Warning).setAction("撤销", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addItem(removed,position);
                    SnackBarUtil.ShortSnackbar(coordinatorLayout,"撤销了删除第"+position+"个item",SnackBarUtil.Confirm).show();
                }
            }).setActionTextColor(Color.WHITE).show();
        }
    }
}
