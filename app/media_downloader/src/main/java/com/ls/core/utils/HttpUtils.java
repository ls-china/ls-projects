package com.ls.core.utils;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public abstract class HttpUtils {
    static HttpClient httpClient = HttpClients.createDefault();

    public static String get(String url, Header ... headers) {
        HttpGet get = new HttpGet(url);
        HttpResponse response = null;
        try {
            if (null != headers) {
                for (Header header : headers) {
                    get.addHeader(header);
                }
            }
            response = httpClient.execute(get);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
