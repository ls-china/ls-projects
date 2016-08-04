package com.ls.md.logic;

import com.ls.md.entity.Mp4ListEntity;
import com.ls.md.entity.YKEntity;
import com.ls.md.util.GsonUtils;
import com.ls.md.util.HttpUtils;
import org.apache.commons.lang.StringUtils;

import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;

public class YKDecoder {

    private static final String GET_JSON_URL = "http://play.youku.com/play/get.json?vid=%s&ct=12&time=%s";
    private static final String DEFAULT_TYPE = "mp4";
    public static final String TYPE_BQ = "3gp";
    public static final String TYPE_QX = DEFAULT_TYPE;
    public static final String TYPE_GQ = "flv";
    public static final String TYPE_HD = "hd2";
    public static final String TYPE_HD_SUPPER = "hd3";

    public static Mp4ListEntity deocde(String url) {
        return deocde(url, DEFAULT_TYPE);
    }

    public static Mp4ListEntity deocde(String url, String type) {
        Mp4ListEntity mp4ListEntity = new Mp4ListEntity();
        mp4ListEntity.mp4Urls = new ArrayList<String>(0);

        mp4ListEntity.vid = StringUtils.substringBetween(url, "id_", ".html");
        String json = HttpUtils.get(String.format(GET_JSON_URL, mp4ListEntity.vid, System.currentTimeMillis() / 1E3 + ""));
        mp4ListEntity.entity = GsonUtils.fromJson(json, YKEntity.class);
        return decodeMp4DownloadUrl(mp4ListEntity, type);
    }

    public static Mp4ListEntity decodeMp4DownloadUrl(Mp4ListEntity mp4ListEntity, String type) {
        mp4ListEntity.m3u8Url = m3u8src_v2(mp4ListEntity.vid, type, mp4ListEntity.entity.data.security.encrypt_string, mp4ListEntity.entity.data.id + "");
        String m3u8Content = HttpUtils.get(mp4ListEntity.m3u8Url);
        if (null == m3u8Content) {
            return mp4ListEntity;
        }
        StringReader sr = new StringReader(m3u8Content);
        BufferedReader br = new BufferedReader(sr);
        String line = null;
        try {
            do {
                line = br.readLine();
                if (null == line) {
                    break;
                }
                if (line.startsWith("http")) {
                    mp4ListEntity.mp4Urls.add(line);
                }
            } while (true);
        } catch (IOException e) {
        } finally {
            sr.close();
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mp4ListEntity;
    }

    /*static String m3u8src(String vid, String type, boolean isIPHONEOrIPOD) {
        String d = "http://v.youku.com/player/getM3U8/vid/" + vid + "/type/" + type + "/ts/" + (System.currentTimeMillis() / 1E3);
        if (isIPHONEOrIPOD) d += "/useKeyFrame/0";
        return d + "/v.m3u8";
    }*/

    private static String m3u8src_v2(String vid, String type, String encrypt_string, String oip) {
        String[] arr = getSidToken(encrypt_string);
        String sid = arr[0];
        String token = arr[1];
        String ts = System.currentTimeMillis() / 1E3 + "";
        return String.format(Locale.CHINESE, "http://pl.youku.com/playlist/m3u8?vid=%s&type=%s&ts=%s&sid=%s&token=%s&ctype=%s&ev=%s&oip=%s",
                vid, type, ts, sid, token, "12", "1", oip);
    }

    private static String[] getSidToken(String encrypt_string) {
        String g = N("becaf9be", Ea(encrypt_string));
//        String sid = arr[0];
//        String token = arr[1];
        return g.split("_");
    }

    static String getEp(String vid, String sid, String token) {
        return URLEncoder.encode(L(N("bf7e5f01", sid + "_" + vid + "_" + token)));
    }

    private static String Ea(String b) {
        if (null == b || b.length() <= 0) return "";
        char[] chars = b.toCharArray();
        int e = 0, c = 0, g = 0, d = chars.length;
        char[] h = new char[]{(char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, 62, (char) -1, (char) -1, (char) -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, (char) -1, (char) -1, (char) -1, (char) -1, (char) -1};
        StringBuilder sb = new StringBuilder();
        for (; g < d; ) {
            do {
                if (g < d) {
                    e = h[chars[g++] & 255];
                }
            } while (g < d && -1 == e);
            if (-1 == e) break;
            do {
                if (g < d) {
                    c = h[chars[g++] & 255];
                }
            } while (g < d && -1 == c);
            if (-1 == c) break;
            sb.append((char) (e << 2 | (c & 48) >> 4));
            do {
                if (g < d) {
                    e = chars[g++] & 255;
                    if (61 == e) {
                        return sb.toString();
                    }
                    e = h[e];
                }
            } while (g < d && -1 == e);
            if (-1 == e) break;
            sb.append((char) ((c & 15) << 4 | (e & 60) >> 2));
            do {
                if (g < d) {
                    c = chars[g++] & 255;
                    if (61 == c) {
                        return sb.toString();
                    }
                    c = h[c];
                }
            } while (g < d && -1 == c);
            if (-1 == c) break;
            sb.append((char) ((e & 3) << 6 | c));
        }

        return sb.toString();
    }

    private static String N(String b, String e) {
        StringBuilder sb = new StringBuilder();
        char[] c = new char[256];
        char[] chars = b.toCharArray();
        int g = 0;
        char h = 0, d = 0;
        for (; 256 > h; h++) {
            c[h] = h;
        }
        for (h = 0; 256 > h; h++) {
            g = (g + c[h] + chars[h % chars.length]) % 256;
            d = c[h];
            c[h] = c[g];
            c[g] = d;
        }
        g = 0;
        h = 0;
        for (int j = 0; j < e.length(); j++) {
            h = (char) ((h + 1) % 256);
            g = (g + c[h]) % 256;
            d = c[h];
            c[h] = c[g];
            c[g] = d;
            sb.append(new Character((char) (e.charAt(j) ^ c[(c[h] + c[g]) % 256])));
        }

        return sb.toString();
    }

    private static String L(String b) {
        if (null == b || b.length() <= 0) return "";
        int c = 0, q, f, h;
        StringBuilder sb = new StringBuilder();
        int d = b.length();
        for (; c < d; ) {
            q = b.charAt(c++) & 255;
            if (c == d) {
                sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(q >> 2));
                sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt((q & 3) << 4));
                sb.append("==");
                break;
            }
            f = b.charAt(c++);
            if (c == d) {
                sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(q >> 2));
                sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt((q & 3) << 4 | (f & 240) >> 4));
                sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt((f & 15) << 2));
                sb.append("=");
                break;
            }
            h = b.charAt(c++);
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(q >> 2));
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt((q & 3) << 4 | (f & 240) >> 4));
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(((f & 15) << 2 | (h & 192) >> 6)));
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(h & 63));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws FileNotFoundException, ScriptException {
//        System.out.println(new YKDecoder().m3u8src_v2("XMTU1NTYxODQ5Ng==", "mp4", "NwXWRwUbIrjS0fLH8uJxAIPwsRM+1wvMXRw=", "388904624", true));
        Mp4ListEntity str = YKDecoder.deocde("http://v.youku.com/v_show/id_XMTY0NDM0OTExMg==.html");
        System.out.println(str);
    }

}
