package com.ls.downloadtxt.ui.activity.adapter;

import android.content.Context;
import android.widget.TextView;
import com.ls.downloadtxt.ui.adapter.AbsRecyclerViewAdapter2;

/**
 * Created by hx on 16-7-21.
 */
public class AppMenuAdapter extends AbsRecyclerViewAdapter2<String> {
    public AppMenuAdapter(Context context) {
        super(context);
    }

    @Override
    public int getContentViewResIdByType(int viewType) {
        return android.R.layout.simple_list_item_1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ((TextView) holder.viewToucher.findViewById(android.R.id.text1)).setText(getItem(position));
    }
}
