package com.ls.downloadtxt.ui.viewholder;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by hx on 2016/3/21.
 */
public class FrameViewHolder {
    private View view;
    private Context context;
    public FrameViewHolder(Activity activity) {
        this.context = activity;
        this.view = activity.getWindow().getDecorView();
    }
    public FrameViewHolder(View view) {
        this.view = view;
        this.context = view.getContext();
    }

    public View getContentView() {
        return view;
    }

    public View findViewById(int viewid) {
        return this.view.findViewById(viewid);
    }

    public void setTextViewText(int resid, int ... viewid) {
        if (null == viewid ) return;
        for (int id : viewid) {
            ((TextView)view.findViewById(id)).setText(resid);
        }
    }

    public void setTextViewText(CharSequence text, int ... viewid) {
        if (null == viewid ) return;
        for (int id : viewid) {
            ((TextView)view.findViewById(id)).setText(text);
        }
    }

    public void setImageViewResource(int resid, int ... viewid) {
        if (null == viewid ) return;
        for (int id : viewid) {
            ((ImageView)view.findViewById(id)).setImageResource(resid);
        }
    }

    public void setImageViewBackgroundResource(int resid, int ... viewid) {
        if (null == viewid ) return;
        for (int id : viewid) {
            ((ImageView)view.findViewById(id)).setBackgroundResource(resid);
        }
    }
}
