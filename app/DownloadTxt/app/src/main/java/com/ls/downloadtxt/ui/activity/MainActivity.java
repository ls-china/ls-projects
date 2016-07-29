package com.ls.downloadtxt.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.ls.downloadtxt.App;
import com.ls.downloadtxt.R;
import com.ls.downloadtxt.net.NetService;
import com.ls.downloadtxt.ui.activity.adapter.AppMenuListAdapter;
import com.ls.downloadtxt.util.FrameUtil;
import com.ls.downloadtxt.util.ToastUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.lv)
    private ListView lv;
    @ViewInject(android.R.id.text1)
    private TextView tv;
    @ViewInject(R.id.commonLoadingWrapper)
    private View loadingWrapper;

    private AppMenuListAdapter adapter;
    private boolean isLoadingMenu;
    private NetService netService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        ((TextView) findViewById(android.R.id.title)).setText(R.string.title_app);

        netService = App.getInstace(this).getNetService();

        adapter = new AppMenuListAdapter(this);
        lv.setAdapter(adapter);

        loadMenu();
    }

    @Event({R.id.btn_right, android.R.id.text1})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_right:
                FrameUtil.startActivity(this, SettingActivity.class);
                break;
            case android.R.id.text1:
                loadMenu();
                break;
        }
    }

    @Event(value = {R.id.lv}, type = AdapterView.OnItemClickListener.class)
    private void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle data = new Bundle();
        data.putString("TAG", (String) parent.getItemAtPosition(position));
        FrameUtil.startActivity(this, ArticalActivity.class, data);
    }

    private void loadMenu() {
        if (isLoadingMenu) {
            return;
        }
        isLoadingMenu = true;
        loadingWrapper.setVisibility(View.VISIBLE);
        Observable.create(new Observable.OnSubscribe<String[]>() {
            @Override
            public void call(Subscriber<? super String[]> subscriber) {
                try {
                    subscriber.onStart();
                    Call<String[]> call = netService.tags();
                    Response<String[]> response = call.execute();
                    subscriber.onNext(response.body());
                } catch (Throwable e) {
                    subscriber.onError(e);
                } finally {
                    subscriber.onCompleted();
                }
            }
        })
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<String[]>() {
                    @Override
                    public void onNext(String[] response) {
                        adapter.clear();
                        adapter.add(response);
                        adapter.notifyDataSetChanged();
                        tv.setVisibility(response.length > 0 ? View.GONE : View.VISIBLE);
                        ToastUtil.Toast(MainActivity.this, "加载成功.");
                    }

                    @Override
                    public void onCompleted() {
                        isLoadingMenu = false;
                        loadingWrapper.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        ToastUtil.Toast(MainActivity.this, "加载失败!");
                    }
                });
    }

}
