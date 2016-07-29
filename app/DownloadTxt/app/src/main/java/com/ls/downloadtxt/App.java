package com.ls.downloadtxt;

import android.app.Application;
import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ls.downloadtxt.db.dao.ConfigDAO;
import com.ls.downloadtxt.net.NetService;
import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.xutils.x;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class App extends Application {
    private NetService netService;
    private Gson gson;
    private OkHttpClient httpClient;
    private ConfigDAO configDAO;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);

        ImageLoaderConfiguration config = null;
        try {
            config = new ImageLoaderConfiguration.Builder(this)
                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                    .diskCacheFileCount(100) //缓存的文件数量
                    .diskCache(new LruDiskCache(getCacheDir(), new Md5FileNameGenerator(), 50 * 1024 * 1024))//自定义缓存路径
                    .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                    .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                    .defaultDisplayImageOptions(
                            new DisplayImageOptions.Builder()
                                    .cacheInMemory(true)                               //启用内存缓存
                                    .cacheOnDisk(true)                                 //启用外存缓存
                                    .considerExifParams(true)                          //启用EXIF和JPEG图像格式
                                    .build()
                    )
                    .build();
        } catch (IOException e) {
            config = new ImageLoaderConfiguration.Builder(this)
                    .build();
        }
        ImageLoader.getInstance().init(config);
    }

    public static App getInstace(Context context) {
        return (App) context.getApplicationContext();
    }

    public synchronized Gson getGson() {
        if (null == gson) {
            gson = new GsonBuilder().create();
        }
        return gson;
    }

    public synchronized OkHttpClient getHttpClient() {
        if (null == httpClient) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        }
        return httpClient;
    }

    public synchronized NetService getNetService() {
        if (null == netService) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(getHttpClient())
                    .baseUrl(NetService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();
            netService = retrofit.create(NetService.class);
        }
        return netService;
    }

    public synchronized ConfigDAO getConfigDAO() {
        if (null == configDAO) {
            configDAO = new ConfigDAO();
        }
        return configDAO;
    }
}
