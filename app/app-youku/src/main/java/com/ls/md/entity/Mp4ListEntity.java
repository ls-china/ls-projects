package com.ls.md.entity;


import com.ls.md.util.GsonUtils;

import java.util.ArrayList;

public class Mp4ListEntity {
    public String vid;
    public YKEntity entity;
    public String m3u8Url;
    public ArrayList<String> mp4Urls;

    @Override
    public String toString() {
        return "Mp4ListEntity{" +
                "entity=" + GsonUtils.toJson(entity) +
                ", m3u8Url='" + m3u8Url + '\'' +
                ", mp4Urls=" + mp4Urls +
                '}';
    }
}
