package com.example.qq.mycoordinatordemo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.qq.mycoordinatordemo.R;
import com.example.qq.mycoordinatordemo.base.BeautifulGirl;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener,View.OnLongClickListener{

    private Context mContext;
    private List<BeautifulGirl> datas;

    public static interface OnRecyclerViewItemClickListener{
        void onItemClick(View view);
        void onItemLongClick(View view);
    }
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    public GridAdapter(Context context,List<BeautifulGirl> datas){
        mContext = context;
        this.datas = datas;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0){
            View view = LayoutInflater.from(mContext).inflate(R.layout.grid_meizi_item,parent,false);
            MyViewHolder holder = new MyViewHolder(view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            return holder;
        }else {
            MyViewHolder2 holder2 = new MyViewHolder2(LayoutInflater.from(mContext).inflate(R.layout.page_item,parent,false));
            return holder2;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){
            Picasso.with(mContext).load(datas.get(position).getUrl()).into(((MyViewHolder)holder).iv);

        }else if (holder instanceof MyViewHolder2){
            ((MyViewHolder2)holder).tv.setText(datas.get(position).getPage() + "页");
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    //加载不同布局
    @Override
    public int getItemViewType(int position) {
        //判断item是图还是显示页数(图片有url)
        if (!TextUtils.isEmpty(datas.get(position).getUrl())){
            return 0;
        }else {
            return 1;
        }
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(view);
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (mOnItemClickListener != null){
            mOnItemClickListener.onItemLongClick(view);
        }
        return false;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageButton iv;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (ImageButton)itemView.findViewById(R.id.iv);
        }
    }
    class MyViewHolder2 extends RecyclerView.ViewHolder{
        private TextView tv;
        public MyViewHolder2(View itemView) {
            super(itemView);
            tv = (TextView)itemView.findViewById(R.id.tv);
        }
    }
}
