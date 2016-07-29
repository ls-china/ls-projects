package com.ls.downloadtxt.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ls.downloadtxt.ui.viewholder.FrameViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hx on 2016/3/21.
 */
public abstract class AbsRecyclerViewAdapter1<E, VH extends AbsRecyclerViewAdapter1.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected final LayoutInflater layoutInflater;
    protected final Context context;
    protected ArrayList<E> datas;

    public AbsRecyclerViewAdapter1(Context context) {
        datas = new ArrayList<E>(0);
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void addList(List<E> list) {
        datas.addAll(list);
    }

    public void add(E ... items) {
        if (null != items ) {
            for (E e : items) {
                addSingle(e);
            }
        }
    }

    public void addSingle(E item) {
        if (null != item ) {
            datas.add(item);
        }
    }

    public void clear() { datas.clear();}

    public void remove(int index) {
        datas.remove(index);
    }

    public void remove(E e) {
        datas.remove(e);
    }

    public E getItem(int position) {
        return datas.get(position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public abstract int getContentViewResIdByType(int viewType);

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return (VH) new ViewHolder(layoutInflater.inflate(getContentViewResIdByType(viewType), parent, false));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public FrameViewHolder viewToucher;
        public ViewHolder(View view) {
            super(view);
            viewToucher = new FrameViewHolder(view);
        }
    }
}