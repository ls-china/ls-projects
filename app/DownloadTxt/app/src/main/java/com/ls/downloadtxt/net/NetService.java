package com.ls.downloadtxt.net;

import com.ls.downloadtxt.net.resp.ArticalPage;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NetService {
    public static final String BASE_URL = "http://222.73.85.26:19088";
//    public static final String BASE_URL = "http://10.7.6.21";

    @GET("/api/tags")
//    @GET("/menu")
    Call<String[]> tags();

//    @FormUrlEncoded
//    @POST("/artical")
//    Call<ArticalPage> getItemsByTag(@Field("tag") String tag,@Field("page") int page);

    @GET("/api/{tag}/{page}")
    Call<ArticalPage> getItemsByTag(
            @Path("tag") String tag,
            @Path("page") int page
    );
}
