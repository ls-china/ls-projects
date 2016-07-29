package com.ls.downloadtxt.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.ls.downloadtxt.R;
import com.ls.downloadtxt.ui.activity.adapter.FileExploreListAdapter;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import java.io.File;
import java.util.ArrayList;

@ContentView(R.layout.activity_file_explore)
public class FileExploreActivity extends AppCompatActivity {

    @ViewInject(R.id.lv)
    private ListView lv;
    @ViewInject(android.R.id.title)
    private TextView tvTitle;

    private FileExploreListAdapter adapter;

    private File currentFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        findViewById(R.id.btn_left).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_right).setVisibility(View.GONE);
        tvTitle.setEllipsize(TextUtils.TruncateAt.MIDDLE);

        String path = getIntent().getStringExtra("PATH");
        if (path == null) {
            currentFile = Environment.getExternalStorageDirectory();
        } else {
            currentFile = new File(path);
        }
        if (currentFile.isFile()) {
            currentFile = currentFile.getParentFile();
        }

        tvTitle.setText(currentFile.getAbsolutePath());

        adapter = new FileExploreListAdapter(this);
        lv.setAdapter(adapter);

        loadPath();
    }

    @Event(value = {R.id.lv}, type = AdapterView.OnItemClickListener.class)
    private void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        File item = (File) parent.getItemAtPosition(position);
        if (item.getName().equals("..")) {
            currentFile = currentFile.getParentFile();
        } else {
            currentFile = item;
        }
        loadPath();
    }

    @Event(value = {R.id.lv}, type = AdapterView.OnItemLongClickListener.class)
    private boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        File item = (File) parent.getItemAtPosition(position);
        Intent intent = new Intent();
        intent.putExtra("PATH", item.getAbsolutePath());
        setResult(RESULT_OK, intent);
        finish();
        return true;
    }

    @Event({R.id.btn_left})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                finish();
                break;
        }
    }

    private void loadPath() {
        tvTitle.setText(currentFile.getAbsolutePath());
        Observable.create(new Observable.OnSubscribe<ArrayList<File>>() {
            @Override
            public void call(Subscriber<? super ArrayList<File>> subscriber) {
                try {
                    subscriber.onStart();
                    File[] children = currentFile.listFiles();
                    ArrayList<File> directoryFiles = new ArrayList<File>();
                    for (File file : children) {
                        if (file.isDirectory()) {
                            directoryFiles.add(file);
                        }
                    }
                    subscriber.onNext(directoryFiles);
                } catch (Throwable e) {
                    subscriber.onError(e);
                } finally {
                    subscriber.onCompleted();
                }
            }
        })
        .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
        .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
        .subscribe(new Observer<ArrayList<File>>() {
            @Override
            public void onNext(ArrayList<File> response) {
                adapter.clear();
                if (currentFile.getParentFile() != null) {
                    adapter.add(new File(".."));
                }
                adapter.addList(response);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        });
    }

}
