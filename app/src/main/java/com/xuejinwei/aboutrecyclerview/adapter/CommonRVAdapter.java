package com.xuejinwei.aboutrecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuejinwei on 2017/2/25.
 * Email:xuejinwei@outlook.com
 * 统一recyclerview的adapt，简单封装
 */

public abstract class CommonRVAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {

    protected Context mContext;
    protected int     mLayoutId;
    protected List<T> mDatas = new ArrayList<>();
    protected LayoutInflater           mInflater;
    private   OnGItemClickListener     onGItemClickListener;
    private   OnGItemLongClickListener onGItemLongClickListener;

    public CommonRVAdapter(Context context, int layoutId, List<T> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
    }

    public CommonRVAdapter(Context context, int layoutId) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = new ArrayList<>();
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommonViewHolder gViewHolder;
        gViewHolder = CommonViewHolder.createViewHolder(mContext, parent, mLayoutId);
        setListener(parent, gViewHolder, viewType);
        return gViewHolder;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        convert(holder, mDatas.get(position));
    }

    public abstract void convert(CommonViewHolder gViewHolder, T t);

    @Override
    public int getItemCount() {
        if (mDatas == null) {
            return 0;
        }
        return mDatas.size();
    }

    /**
     * 添加数据
     */
    public void addAll(List<T> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 获得数据
     */
    public List<T> getAll() {
        return mDatas;
    }

    /**
     * 增加一行数据
     */
    public void addItem(T data) {
        mDatas.add(data);
        notifyItemInserted(mDatas.size());
    }

    /**
     * 增加一行数据
     */
    public void addItem(int position, T data) {
        mDatas.add(position, data);
        notifyItemInserted(position);
    }

    /**
     * 删除一行数据
     */
    public void removeItem(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 清空数据
     */
    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    /**
     * 获得position
     */
    protected int getPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }

    /**
     * 设置item点击
     */
    protected void setListener(final ViewGroup parent, final CommonViewHolder viewHolder, int viewType) {
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onGItemClickListener != null) {
                    int position = getPosition(viewHolder);
                    if (position == RecyclerView.NO_POSITION) {// 如果等于-1，忽略这次点击事件
                        return;
                    }
                    onGItemClickListener.onItemClick(parent, v, mDatas.get(position), position);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onGItemLongClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    if (position == RecyclerView.NO_POSITION) {// 如果等于-1，忽略这次点击事件
                        return false;
                    }
                    return onGItemLongClickListener.onItemLongClick(v, viewHolder, position);
                }
                return false;
            }
        });
    }

    /**
     * 设置RV的item点击监听
     * @param onGItemClickListener 传入{@link OnGItemClickListener}
     */
    public void setOnGItemClickListener(OnGItemClickListener onGItemClickListener) {
        this.onGItemClickListener = onGItemClickListener;
    }

    /**
     * 设置RV的item长按监听事件
     * @param onGItemLongClickListener 传入 {@link OnGItemLongClickListener}
     */
    public void setOnGItemLongClickListener(OnGItemLongClickListener onGItemLongClickListener){
        this.onGItemLongClickListener = onGItemLongClickListener;

    }

    public interface OnGItemClickListener<T> {
        void onItemClick(ViewGroup parent, View view, T data, int position);
    }

    public interface OnGItemLongClickListener<T>{
        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }
}
