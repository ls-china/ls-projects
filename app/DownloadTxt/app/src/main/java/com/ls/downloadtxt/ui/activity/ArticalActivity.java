package com.ls.downloadtxt.ui.activity;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.ls.downloadtxt.App;
import com.ls.downloadtxt.R;
import com.ls.downloadtxt.db.dao.ConfigDAO;
import com.ls.downloadtxt.db.entity.Config;
import com.ls.downloadtxt.net.NetService;
import com.ls.downloadtxt.net.resp.ArticalPage;
import com.ls.downloadtxt.net.resp.Items;
import com.ls.downloadtxt.ui.activity.adapter.ArticalListAdapter;
import com.ls.downloadtxt.ui.widget.RefreshLayout;
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

@ContentView(R.layout.activity_artical)
public class ArticalActivity extends AppCompatActivity {

    @ViewInject(R.id.srl)
    private RefreshLayout srl;
    @ViewInject(R.id.lv)
    private ListView lv;
    @ViewInject(android.R.id.text1)
    private TextView tv;
    @ViewInject(android.R.id.title)
    private TextView tvTitle;

    private ArticalListAdapter adapter;
    private boolean isLoadingData;
    private NetService netService;
    private String tag;
    private int page;
    private DownloadManager downloadManager;
    private ConfigDAO configDAO;
    private Config config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        findViewById(R.id.btn_left).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_right).setVisibility(View.GONE);

        configDAO = App.getInstace(this).getConfigDAO();
        config = configDAO.get(this);

        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        tag = getIntent().getStringExtra("TAG");
        tvTitle.setText(tag);

        netService = App.getInstace(this).getNetService();

        srl.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);

        adapter = new ArticalListAdapter(this);
        lv.setAdapter(adapter);

        srl.setFooterView(LayoutInflater.from(this).inflate(R.layout.listview_footer, null));
        srl.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                page++;
                loadArtical(page);
            }
        });
        loadArtical(1);
    }

    @Event(value = {R.id.lv}, type = AdapterView.OnItemLongClickListener.class)
    private boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Items item = (Items) parent.getItemAtPosition(position);

        String name = item.title + ".txt";

        Uri resource = Uri.parse(item.url);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(resource);
        startActivity(intent);

//        Uri resource = null;
//        try {
//            int index = item.url.lastIndexOf("/");
//            String url = item.url.substring(0, index) + "/" + URLEncoder.encode(item.url.substring(index + 1));
//            System.out.println( url );
//            resource = Uri.parse(url);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (null == resource) {
//            ToastUtil.Toast(this, "文件下载失败: " + name);
//            return true;
//        }
//        DownloadManager.Request request = new DownloadManager.Request(resource);
//        int networkType = DownloadManager.Request.NETWORK_WIFI;
//        if (config.isUseMobileNet()) {
//            networkType = DownloadManager.Request.NETWORK_MOBILE | networkType;
//        }
//        request.setAllowedNetworkTypes(networkType);
//        request.allowScanningByMediaScanner();  //表示允许MediaScanner扫描到这个文件，默认不允许。
//        request.setTitle(name);      //设置下载中通知栏的提示消息
//        request.setDescription(item.author);//设置设置下载中通知栏提示的介绍
//        request.setShowRunningNotification(true);
//        request.setVisibleInDownloadsUi(true);
//        request.setDestinationUri(Uri.fromFile(new File(config.getDownloadPath(), name)));
//        downloadManager.enqueue(request);
        ToastUtil.Toast(this, "开始下载文件 " + name);
        return true;
    }

    @Event({R.id.btn_right, R.id.btn_left, android.R.id.text1})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_right:
                break;
            case android.R.id.text1:
                loadArtical(1);
                break;
            case R.id.btn_left:
                finish();
                break;
        }
    }

    private void loadArtical(final int currentPage) {
        if (isLoadingData) {
            return;
        }
        isLoadingData = true;
        Observable.create(new Observable.OnSubscribe<ArticalPage>() {
            @Override
            public void call(Subscriber<? super ArticalPage> subscriber) {
                try {
                    subscriber.onStart();
                    Call<ArticalPage> call = netService.getItemsByTag(tag, currentPage);
                    Response<ArticalPage> response = call.execute();
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
                .subscribe(new Observer<ArticalPage>() {
                    @Override
                    public void onNext(ArticalPage response) {
                        page = response.currentPage;
                        if (currentPage <= 1) {
                            adapter.clear();
                        }
                        adapter.addList(response.items);
                        adapter.notifyDataSetChanged();
                        tv.setVisibility(adapter.getCount() > 0 ? View.GONE : View.VISIBLE);
                        ToastUtil.Toast(ArticalActivity.this, "加载成功.");
                    }

                    @Override
                    public void onCompleted() {
                        isLoadingData = false;
                        srl.setRefreshing(false);
                        srl.setLoading(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        ToastUtil.Toast(ArticalActivity.this, "加载失败!");
//                        srl.setRefreshing(false);
//                        srl.setLoading(false);
                    }
                });
    }

}
