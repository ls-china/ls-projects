package com.ls.downloadtxt.ui.activity.adapter;

import android.content.Context;
import android.widget.TextView;
import com.ls.downloadtxt.ui.adapter.AbsListViewAdapter;

public class AppMenuListAdapter extends AbsListViewAdapter<String> {

    public AppMenuListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getContentViewResIdByType(int viewType) {
        return android.R.layout.simple_list_item_1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ((TextView) holder.viewToucher.getContentView()).setText(getItem(position));
    }

}
