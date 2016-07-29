package com.ls.core.utils;

import java.io.ByteArrayOutputStream;

/**
 * Created by hx on 16-7-29.
 */
public abstract class CodecUtils {
    public static String decodeASCII(String bytes) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
        //将每2位16进制整数组装成一个字节
        String hexString = "0123456789ABCDEF";
        for (int i = 0; i < bytes.length(); i += 2) {
            baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1))));
        }
        return new String(baos.toByteArray());
    }

    public static String decodeASCII(byte[] bytes) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length / 2);
        //将每2位16进制整数组装成一个字节
        String hexString = "0123456789ABCDEF";
        for (int i = 0; i < bytes.length; i += 2) {
            baos.write((hexString.indexOf(bytes[i]) << 4 | hexString.indexOf(bytes[i])));
        }
        return new String(baos.toByteArray());
    }

    public static byte[] decodeASCIIToByteArray(String bytes) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
        //将每2位16进制整数组装成一个字节
        String hexString = "0123456789ABCDEF";
        for (int i = 0; i < bytes.length(); i += 2) {
            baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i))));
        }
        return baos.toByteArray();
    }
}
