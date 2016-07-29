package com.ls.md.behavier;

import com.ls.core.utils.GsonUtils;
import com.ls.core.utils.HttpUtils;
import com.ls.md.entity.EpEntity;
import com.ls.md.entity.YKEntity;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.net.URLEncoder;
import java.util.ArrayList;

public class YouKuBehavier implements IBehavier {
    //    private static final String playUrl = "http://play-dxk.youku.com/play/get.json?vid=XMTY2MjQyMjUwOA==&ct=10&aid=27762330&pt=0&ob=1&time=1469777720408";
    private static final String getFlvPathUrl = "http://k.youku.com/player/getFlvPath/" +
            "sid/646977790896710600617_00/" +
            "st/mp4/" +
            "fileid/0300080300579AD6F005AF08C7A8451773DF98-0B70-6612-B740-3BF1B437EFA3" +
            "?start=0" +
            "&K=41b3ab0222166bec282b9bad&hd=1" +
            "&myp=0" +
            "&ts=407" +
            "&ymovie=1" +
            "&ypp=0" +
            "&ctype=10" +
            "&ev=1&token=2939" +
            "&oip=3030917886" +
            "&ep=4g3tgkfVfPXHfkOGGxQ%2BwHY2XixsgzL9%2FHN%2Fj0AX8I9Z%2B0mpJBnGK4xCp%2B2wfxy7jciF3oNS%2FXooxSiDg12yyLLcuYIpbEzSSgkeDAbTUncFVQYDvsQs9gugvwlG6voGbSM10xR0%2ByQ%3D" +
            "&yxon=1&special=true";
    private static final String playUrl = "http://play.youku.com/play/get.json?vid=%s&ct=12";
    private static final String playUrl_local = "http://localhost/ykplay";
    private static final String u3u8Url = "http://pl.youku.com/playlist/m3u8?ctype=12" +
            "&ep=%s&ev=1&keyframe=1&oip=%d" +
            "&sid=%s&token=%s&type=%s&vid=%s";

    @Override
    public String[] getAbsoluteDownloadUrls(String playurl) {
        if (StringUtils.isEmpty(playurl)) {
            return null;
        }
        String vid = StringUtils.substringBetween(playurl, "id_", ".html");
        //TODO UnComment this line
//        String json = HttpUtils.get(String.format(playUrl, vid));
        String json = HttpUtils.get(String.format(playUrl_local, vid));
        YKEntity ykEntity = GsonUtils.fromJson(json, YKEntity.class);

        String ep = ykEntity.data.security.encrypt_string;
        long oip = ykEntity.data.security.ip;

        EpEntity epEntity = GetParameters(vid, ep);
        System.out.println(epEntity);
        return new String[0];
    }

    public static void main(String[] args) {
        new YouKuBehavier().getAbsoluteDownloadUrls("http://v.youku.com/v_show/id_XMTY2MjU1NDQ2NA==.html?f=27763076");
//        System.out.println(URLDecoder.decode("4g3tgkfVfPXHfkOGGxQ%2BwHY2XixsgzL9%2FHN%2Fj0AX8I9Z%2B0mpJBnGK4xCp%2B2wfxy7jciF3oNS%2FXooxSiDg12yyLLcuYIpbEzSSgkeDAbTUncFVQYDvsQs9gugvwlG6voGbSM10xR0%2ByQ%3D"));
//        System.out.println(Arrays.toString(
//                Base64.decodeBase64("4g3tgkfVfPXHfkOGGxQ+wHY2XixsgzL9/HN/j0AX8I9Z+0mpJBnGK4xCp+2wfxy7jciF3oNS/XooxSiDg12yyLLcuYIpbEzSSgkeDAbTUncFVQYDvsQs9gugvwlG6voGbSM10xR0+yQ=")
//        ) );
//        System.out.println(
//                CodecUtils.decodeASCII(
//                        Base64.decodeBase64("4g3tgkfVfPXHfkOGGxQ+wHY2XixsgzL9/HN/j0AX8I9Z+0mpJBnGK4xCp+2wfxy7jciF3oNS/XooxSiDg12yyLLcuYIpbEzSSgkeDAbTUncFVQYDvsQs9gugvwlG6voGbSM10xR0+yQ=")
//                )
//        );
//        System.out.println(
//                CodecUtils.decodeASCII(Base64.decodeBase64("hC5OW5ax5v8u7K01Y1+StgCXlQGXuSAWL2BveTtcows="))
//        );
    }


    /// 计算sid, token和ep
    /// </Summary>
    public static EpEntity GetParameters(String vid, String encryptString) {
        String keyA = "becaf9be";
        byte[] decodeEp = Decode64(encryptString); // 对encryptString作base64解码
        String temp = Rc4(keyA, decodeEp, false); // 用秘钥keyA之作Rc4加密，不做base64编码
        String[] part = temp.split("_");

        String sid, token, ep;
        sid = part[0];
        token = part[1];
        String keyB = "bf7e5f01";

        byte[] whole = String.format("%s_%s_%s", sid, vid, token).getBytes(); // 组合字符串的ASCII字节数组
        ep = URLEncoder.encode(Rc4(keyB, whole, true)); // 用秘钥keyB与之作Rc4加密，且结果进行base64编码，之后再做url encode
        return new EpEntity(sid, token, ep);
    }

    private static byte[] Decode64(String a) {
        if (null == a || a.length() <= 0) {
            return null;
        }
        int f;
        int g;
        String h;
        ArrayList<Byte> l = new ArrayList<Byte>();
        int[] i =
                {
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
                        52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                        11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29,
                        30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1,
                        -1, -1
                };
        for (g = a.length(), f = 0, h = ""; g > f; ) {
            int b;
            do {
                b = i[255 & a.charAt(f++)];
            } while (g > f && -1 == b);

            if (-1 == b) break;

            int c;
            do {
                c = i[255 & a.charAt(f++)];
            } while (g > f && -1 == c);

            if (-1 == c) break;

            byte[] bytes0 = {(byte) (b << 2 | (48 & c) >> 4)};
            h += new String(bytes0);
            l.add(bytes0[0]);
            int d;
            do {
                d = 255 & a.charAt(f++);
                if (61 == d) {
                    byte[] buffer = new byte[l.size()];
                    for (int bi = 0; bi < l.size(); bi++) {
                        buffer[bi] = l.get(bi);
                    }
                    return buffer;
                }
                d = i[d];
            } while (g > f && -1 == d);

            if (-1 == d) break;
            byte[] bytes1 = {(byte) ((15 & c) << 4 | (60 & d) >> 2)};
            h += new String(bytes1);
            l.add(bytes1[0]);
            int e;
            do {
                e = 255 & a.charAt(f++);
                if (61 == e) {
                    byte[] buffer = new byte[l.size()];
                    for (int bi = 0; bi < l.size(); bi++) {
                        buffer[bi] = l.get(bi);
                    }
                    return buffer;
                }
                e = i[e];
            } while (g > f && -1 == e);

            if (-1 == e) break;
            byte[] bytes2 = {(byte) ((3 & d) << 6 | e)};
            h += new String(bytes2);
            l.add(bytes2[0]);
        }
        byte[] buffer = new byte[l.size()];
        for (int bi = 0; bi < l.size(); bi++) {
            buffer[bi] = l.get(bi);
        }
        return buffer;
    }

    private static String Rc4(String a, byte[] c, boolean isToBase64) {
        // rc4加密算法
        int f = 0, h = 0, q;
        int[] b = new int[256];
        for (int i = 0; i < 256; i++) {
            b[i] = i;
        }
        while (h < 256) {
            f = (f + b[h] + a.charAt(h % a.length())) % 256;
            int temp = b[h];
            b[h] = b[f];
            b[f] = temp;
            h++;
        }

        f = 0;
        h = 0;
        q = 0;
        String result = "";
        ArrayList<Byte> bytesR = new ArrayList<Byte>();
        while (q < c.length) {
            h = (h + 1) % 256;
            f = (f + b[h]) % 256;
            int temp = b[h];
            b[h] = b[f];
            b[f] = temp;
            byte[] bytes = {(byte) (c[q] ^ b[(b[h] + b[f]) % 256])};
            bytesR.add(bytes[0]);
            result += new String(bytes);
            q++;
        }

        if (isToBase64) {
            byte[] buffer = new byte[bytesR.size()];
            for (int bi = 0; bi < bytesR.size(); bi++) {
                buffer[bi] = bytesR.get(bi);
            }
            result = Base64.encodeBase64String(buffer);
        }
        return result;
    }

}
