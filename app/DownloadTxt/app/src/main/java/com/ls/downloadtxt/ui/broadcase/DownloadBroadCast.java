package com.ls.downloadtxt.ui.broadcase;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import com.ls.downloadtxt.util.ToastUtil;

public class DownloadBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);                                                                                      //TODO 判断这个id与之前的id是否相等，如果相等说明是之前的那个要下载的文件
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(id);
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Cursor cursor = downloadManager.query(query);

            String title = null;
            while (cursor.moveToNext()) {
                title = cursor.getString( cursor.getColumnIndex("title") );
            }
            if (null != title) {
                ToastUtil.Toast(context, title + " 下载完成。");
            }
            cursor.close();
        }
    }
}
