package com.ls.downloadtxt.util;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

/**
 * Created by hx on 2016/4/19.
 */
public abstract class ViewUtil {

    public static void refreshSwipeRefreshLayout(SwipeRefreshLayout target, boolean notify) {
        Class type = SwipeRefreshLayout.class;
        View progressView = (View) ReflectUtil.getDeclaredField(type, target, "mCircleView");
        if (null != progressView) {
            progressView.setVisibility(View.VISIBLE);
        }
        ReflectUtil.invokeDeclaredMethod(type, target, "setRefreshing", new Class[] {boolean.class, boolean.class}, true, notify);
    }
}
