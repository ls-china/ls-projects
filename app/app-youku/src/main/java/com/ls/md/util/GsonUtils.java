package com.ls.md.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;

public abstract class GsonUtils {
    private static Gson gson = new GsonBuilder().create();

    public static String fromJson(Object src) {
        return gson.toJson(src);
    }

    public static <T> T fromJson(String json, Class<T> type) {
        return gson.fromJson(json, type);
    }

    public static String toJson(JsonElement src) {
        return gson.toJson(src);
    }

    public static String toJson(Object src) {
        return gson.toJson(src);
    }

    public static String toJson(Object src, Type typeOfSrc) {
        return gson.toJson(src, typeOfSrc);
    }
}
