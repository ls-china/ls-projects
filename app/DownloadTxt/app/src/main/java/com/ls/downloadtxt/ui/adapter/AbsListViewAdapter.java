package com.ls.downloadtxt.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.ls.downloadtxt.ui.viewholder.FrameViewHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class AbsListViewAdapter<E> extends BaseAdapter {
    protected final LayoutInflater layoutInflater;
    protected final Context context;
    protected ArrayList<E> datas;

    public AbsListViewAdapter(Context context) {
        datas = new ArrayList<E>(0);
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void addList(List<E> list) {
        datas.addAll(list);
    }

    public void add(E... items) {
        if (null != items) {
            for (E e : items) {
                addSingle(e);
            }
        }
    }

    public void addSingle(E item) {
        if (null != item) {
            datas.add(item);
        }
    }

    public int getCount() {
        return datas.size();
    }

    public long getItemId(int position) {
        return 0;
    }

    public void clear() {
        datas.clear();
    }

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
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(
                    getContentViewResIdByType(viewType), parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(holder, position);
        return convertView;
    }

    public abstract int getContentViewResIdByType(int viewType);

    public abstract void onBindViewHolder(ViewHolder holder, int position);

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public FrameViewHolder viewToucher;

        public ViewHolder(View view) {
            super(view);
            viewToucher = new FrameViewHolder(view);
        }
    }
}