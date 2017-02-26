package com.xuejinwei.aboutrecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by xuejinwei on 2017/2/25.
 * Email:xuejinwei@outlook.com
 * 统一recyclerview的adapt，简单封装
 */

public abstract class CommonRVAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {
    public static final int VIEW_TYPE_HEADER = 1024;
    public static final int VIEW_TYPE_FOOTER = 1025;

    protected View headerView;
    protected View footerView;

    protected Context                  mContext;
    protected int                      mLayoutId;
    protected List<T>                  mDatas;
    protected LayoutInflater           mInflater;
    private   OnGItemClickListener     onGItemClickListener;
    private   OnGItemLongClickListener onGItemLongClickListener;

    public CommonRVAdapter(Context context, int layoutId, List<T> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = new ArrayList<>();
        mDatas.addAll(datas);
    }

    public CommonRVAdapter(Context context, int layoutId) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = new ArrayList<>();
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            return new CommonViewHolder(mContext, headerView);
        } else if (viewType == VIEW_TYPE_FOOTER) {
            return new CommonViewHolder(mContext, footerView);
        } else {
            return CommonViewHolder.createViewHolder(mContext, parent, mLayoutId);
        }
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_HEADER:
            case VIEW_TYPE_FOOTER:
                break;
            default:
                // 这里如果有header的话，就减去header的个数
                convert(holder, mDatas.get(position - getHeaderExtraViewCount()));
                setListener(holder, position - getHeaderExtraViewCount());
                break;
        }
    }

    public abstract void convert(CommonViewHolder gViewHolder, T t);


    @Override
    public int getItemViewType(int position) {
        if (headerView != null && position == 0) {
            return VIEW_TYPE_HEADER;
        } else if (footerView != null && position == mDatas.size() + getHeaderExtraViewCount()) {
            return VIEW_TYPE_FOOTER;
        } else {
            return super.getItemViewType(position);
        }
    }


    /**
     * 设置item点击
     */
    private void setListener(final CommonViewHolder viewHolder, final int position) {
        if (onGItemClickListener != null) {
            viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onGItemClickListener != null) {
                        if (position == RecyclerView.NO_POSITION) {// 如果等于-1，忽略这次点击事件
                            return;
                        }
                        onGItemClickListener.onItemClick(mDatas.get(position), position);
                    }
                }
            });
        }
        if (onGItemLongClickListener != null) {
            viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onGItemLongClickListener != null) {
                        int position = viewHolder.getAdapterPosition();
                        if (position == RecyclerView.NO_POSITION) {// 如果等于-1，忽略这次点击事件
                            return false;
                        }
                        return onGItemLongClickListener.onItemLongClick(mDatas.get(position), position);
                    }
                    return false;
                }
            });
        }
    }

    /**
     * 添加HeaderView
     *
     * @param headerView 顶部View对象
     */
    public void addHeaderView(View headerView) {
        if (headerView == null) {
            Log.w(TAG, "add the header view is null");
            return;
        }
        this.headerView = headerView;
        notifyDataSetChanged();
    }

    /**
     * 移除HeaderView
     */
    public void removeHeaderView() {
        if (headerView != null) {
            headerView = null;
            notifyDataSetChanged();
        }
    }

    /**
     * 添加FooterView
     *
     * @param footerView View对象
     */
    public void addFooterView(View footerView) {
        if (footerView == null) {
            Log.w(TAG, "add the footer view is null");
            return;
        }
        this.footerView = footerView;
        notifyDataSetChanged();
    }

    /**
     * 移除FooterView
     */
    public void removeFooterView() {
        if (footerView != null) {
            footerView = null;
            notifyDataSetChanged();
        }
    }

    /**
     * 获取附加View的数量,包括HeaderView和FooterView
     *
     * @return 数量
     */
    public int getExtraViewCount() {
        int extraViewCount = 0;
        if (headerView != null) {
            extraViewCount++;
        }
        if (footerView != null) {
            extraViewCount++;
        }
        return extraViewCount;
    }

    /**
     * 获取顶部附加View数量,即HeaderView数量
     *
     * @return 数量
     */
    public int getHeaderExtraViewCount() {
        return headerView == null ? 0 : 1;
    }

    /**
     * 获取底部附加View数量,即FooterView数量
     *
     * @return 数量, 0或1
     */
    public int getFooterExtraViewCount() {
        return footerView == null ? 0 : 1;
    }

    @Override
    public int getItemCount() {
        if (mDatas == null) {
            return getExtraViewCount();
        }
        return mDatas.size() + getExtraViewCount();
    }


    /****************数据操作相关*************/

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
    /****************数据操作相关end*************/


    /**
     * 设置RV的item点击监听
     *
     * @param onGItemClickListener 传入{@link OnGItemClickListener}
     */
    public void setOnGItemClickListener(OnGItemClickListener onGItemClickListener) {
        this.onGItemClickListener = onGItemClickListener;
    }

    /**
     * 设置RV的item长按监听事件
     *
     * @param onGItemLongClickListener 传入 {@link OnGItemLongClickListener}
     */
    public void setOnGItemLongClickListener(OnGItemLongClickListener onGItemLongClickListener) {
        this.onGItemLongClickListener = onGItemLongClickListener;

    }

    public interface OnGItemClickListener<T> {
        void onItemClick(T data, int position);
    }

    public interface OnGItemLongClickListener<T> {
        boolean onItemLongClick(T data, int position);
    }
}
