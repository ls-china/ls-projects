package com.ls.downloadtxt.ui.activity.adapter;

import android.content.Context;
import android.widget.TextView;
import com.ls.downloadtxt.R;
import com.ls.downloadtxt.ui.adapter.AbsListViewAdapter;

import java.io.File;

public class FileExploreListAdapter extends AbsListViewAdapter<File> {

    public FileExploreListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getContentViewResIdByType(int viewType) {
        return R.layout.item_file;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        File item = getItem(position);
        ((TextView) holder.viewToucher.findViewById(android.R.id.title)).setText(item.getName());
    }

}
