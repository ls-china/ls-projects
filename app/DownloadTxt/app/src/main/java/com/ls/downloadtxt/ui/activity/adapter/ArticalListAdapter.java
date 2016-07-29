package com.ls.downloadtxt.ui.activity.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ls.downloadtxt.R;
import com.ls.downloadtxt.net.resp.Items;
import com.ls.downloadtxt.ui.adapter.AbsListViewAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ArticalListAdapter extends AbsListViewAdapter<Items> {

    public ArticalListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getContentViewResIdByType(int viewType) {
        return R.layout.item_artical;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Items item = getItem(position);
        final ImageView imageView = (ImageView) holder.viewToucher.findViewById(android.R.id.icon);
        ImageLoader.getInstance().loadImage(item.image, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                imageView.setImageBitmap(loadedImage);
            }
        });
//        ((ImageView) holder.viewToucher.findViewById(android.R.id.icon)).setImageURI(Uri.parse(item.image));
        ((TextView) holder.viewToucher.findViewById(android.R.id.title)).setText(item.title);
        ((TextView) holder.viewToucher.findViewById(R.id.author)).setText(item.author);
        ((TextView) holder.viewToucher.findViewById(R.id.size)).setText(item.size);
        ((TextView) holder.viewToucher.findViewById(R.id.status)).setText(item.status);
        ((TextView) holder.viewToucher.findViewById(R.id.update)).setText(item.updateTime);
        ((TextView) holder.viewToucher.findViewById(R.id.describtion)).setText(item.describtion);
    }

}
