package com.example.qq.mycoordinatordemo.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;

import com.example.qq.mycoordinatordemo.R;
import com.example.qq.mycoordinatordemo.base.BeautifulGirl;
import com.example.qq.mycoordinatordemo.ui.adapter.GridAdapter;
import com.example.qq.mycoordinatordemo.utils.Define;
import com.example.qq.mycoordinatordemo.utils.MyOkHttp;
import com.example.qq.mycoordinatordemo.utils.SnackBarUtil;
import com.example.qq.mycoordinatordemo.utils.net.ImagePagerActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class RecycleStaggeredActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private CoordinatorLayout coordinatorLayout;
    private GridAdapter mAdapter;
    private StaggeredGridLayoutManager mLayoutManager;
    private List<BeautifulGirl> beautifulGirls;
    private int lastVisibleItem;
    private int page = 1;
    private ItemTouchHelper itemTouchHelper;
    private SwipeRefreshLayout swipeRefreshLayout;
    //存放图片地址的数组
    private static String[] mAllImgUrlArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staggered_recycler);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //初始化ImageLoad
        Define.initImageLoader(this);

        initView();
        setListener();
        new GetData().execute("http://gank.io/api/data/福利/10/1");
    }
    private void initView(){
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.staggered_coordinatorLayout);
        recyclerView = (RecyclerView)findViewById(R.id.staggered_recycler);
        mLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.staggered_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark,R.color.colorAccent);
        swipeRefreshLayout.setProgressViewOffset(false, 0,  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
    }
    private void setListener(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                new GetData().execute("http://gank.io/api/data/福利/10/1");
            }
        });

        itemTouchHelper=new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags=0;
                if(recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager){
                    dragFlags=ItemTouchHelper.UP|ItemTouchHelper.DOWN|ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
                }
                return makeMovementFlags(dragFlags,0);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int from=viewHolder.getAdapterPosition();
                int to=target.getAdapterPosition();
                BeautifulGirl moveItem=beautifulGirls.get(from);
                beautifulGirls.remove(from);
                beautifulGirls.add(to,moveItem);
                mAdapter.notifyItemMoved(from,to);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public boolean isLongPressDragEnabled() {
                return false;
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                0：当前屏幕停止滚动；1时：屏幕在滚动 且 用户仍在触碰或手指还在屏幕上；2时：随用户的操作，屏幕上产生的惯性滑动；
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem +2>=mLayoutManager.getItemCount()) {
                    new GetData().execute("http://gank.io/api/data/福利/10/"+(++page));
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int[] positions= mLayoutManager.findLastVisibleItemPositions(null);
                lastVisibleItem = Math.max(positions[0],positions[1]);
            }
        });
    }
    private class GetData extends AsyncTask<String,Integer,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //设置swipeRefreshLayout为刷新状态
            swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... strings) {
            return MyOkHttp.get(strings[0]);
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
                    recyclerView.setAdapter(mAdapter = new GridAdapter(RecycleStaggeredActivity.this,beautifulGirls));
                    mAdapter.setOnItemClickListener(new GridAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view) {
                            int position = recyclerView.getChildAdapterPosition(view);
                            //查看大图
                            Intent toLookBigTitleImg = new Intent(RecycleStaggeredActivity.this, ImagePagerActivity.class);
                            toLookBigTitleImg.putExtra("image_index", position);
                            toLookBigTitleImg.putExtra("image_urls", mAllImgUrlArray);
                            toLookBigTitleImg.putExtra("isLocal", false);
                            startActivity(toLookBigTitleImg);
                        }

                        @Override
                        public void onItemLongClick(View view) {
                            itemTouchHelper.startDrag(recyclerView.getChildViewHolder(view));
                            int position = recyclerView.getChildAdapterPosition(view);
                            SnackBarUtil.ShortSnackbar(coordinatorLayout,"点击第" + position + "个",SnackBarUtil.Info).show();
                        }
                    });
                    itemTouchHelper.attachToRecyclerView(recyclerView);
                }else {
                    mAdapter.notifyDataSetChanged();
                }
            }
            //停止swipeRefreshLayout加载动画
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
