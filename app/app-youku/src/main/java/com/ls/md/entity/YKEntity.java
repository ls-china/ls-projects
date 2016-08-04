package com.ls.md.entity;


import java.util.List;

public class YKEntity {

    public E e;

    public Data data;

    public double cost;

    public static class E {
        public String desc;
        public String provider;
        public int code;
    }

    public static class Data {
        public int id;

        public Preview preview;

        public Dvd dvd;
        /**
         * certification : true
         * fan_count : 933780
         * reason : 优酷光合计划合作伙伴，DOTA职业玩家，被广大玩家和业内人士尊称为“9神”
         * show_brand : 1
         * avatar : {"big":"http://g1.ykimg.com/0130391F484CF4FDE0385104B921BF01310A93-BED8-1325-7DA6-6B5C0DD32DBA","small":"http://g1.ykimg.com/0130391F484CF4FDE0385104B921BF01310A93-BED8-1325-7DA6-6B5C0DD32DBA","middle":"http://g1.ykimg.com/0130391F484CF4FDE0385104B921BF01310A93-BED8-1325-7DA6-6B5C0DD32DBA","large":"http://g1.ykimg.com/0130391F484CF4FDE0385104B921BF01310A93-BED8-1325-7DA6-6B5C0DD32DBA"}
         * homepage : http://i.youku.com/u/UMzE2OTY2NjUy
         */

        public Uploader uploader;
        /**
         * html5_disable : false
         * continuous : false
         * video_capture : true
         * like_disabled : false
         * stream_mode : 2
         * app_disable : false
         * download_disable : false
         * share_disable : false
         * circle : false
         * play_mode : 1
         */

        public Controller controller;
        /**
         * encrypt_string : MQXWRwUbI7zY1/nE8eJxU9Oguhc11wTKWB4=
         * ip : 3662811506
         */

        public Security security;
        /**
         * uid :
         */

        public User user;
        /**
         * dma_code : 4812
         * area_code : 310000
         */

        public Network network;
        /**
         * tags : ["09dota"]
         * logo : http://r2.ykimg.com/05410408572746166A0A4E04D3BA684D
         * published_time : 2016-05-02 18:16:41
         * share_type : ["ecommerce"]
         * userid : 79241663
         * privacy : anybody
         * category_id : 99
         * type : ["dvd","share"]
         * upload : normal
         * restrict : 0
         * username : 伍声2009
         * share : true
         * title : 【09DOTA高分局】劣势路的强心戟
         * source : 61131
         * seconds : 3476.00
         * encodeid : XMTU1NTYxODQ5Ng==
         * category_letter_id : g
         * subcategories : [{"id":"2221","name":"网络游戏"}]
         * channel : {"tail":["XMTU4NTAyMzMyNA=="]}
         */

        public Video video;
        /**
         * logo : youku
         * media_type : standard
         * audio_lang : default
         * transfer_mode_org : http
         * subtitle_lang : default
         * segs : [{"total_milliseconds_audio":"3475981","fileid":"030020010057272783A01D04B921BFBA6CB8C1-3116-796C-61FE-8F7DF7F67165","total_milliseconds_video":"3475733","key":"a200eb29eff7e011261f267a","size":"101782498"}]
         * stream_type : 3gphd
         * width : 480
         * transfer_mode : http
         * size : 101782498
         * height : 360
         * milliseconds_video : 3475733
         * drm_type : default
         * milliseconds_audio : 3475981
         * stream_fileid : 030020010057272783A01D04B921BFBA6CB8C1-3116-796C-61FE-8F7DF7F67165
         */

        public List<Stream> stream;

        public static class Preview {
            public String timespan;
            public List<String> thumb;
        }

        public static class Dvd {
            public String notsharing;
        }

        public static class Uploader {
            public boolean certification;
            public int fan_count;
            public String reason;
            public int show_brand;
            /**
             * big : http://g1.ykimg.com/0130391F484CF4FDE0385104B921BF01310A93-BED8-1325-7DA6-6B5C0DD32DBA
             * small : http://g1.ykimg.com/0130391F484CF4FDE0385104B921BF01310A93-BED8-1325-7DA6-6B5C0DD32DBA
             * middle : http://g1.ykimg.com/0130391F484CF4FDE0385104B921BF01310A93-BED8-1325-7DA6-6B5C0DD32DBA
             * large : http://g1.ykimg.com/0130391F484CF4FDE0385104B921BF01310A93-BED8-1325-7DA6-6B5C0DD32DBA
             */

            public Avatar avatar;
            public String homepage;

            public static class Avatar {
                public String big;
                public String small;
                public String middle;
                public String large;
            }
        }

        public static class Controller {
            public boolean html5_disable;
            public boolean continuous;
            public boolean video_capture;
            public boolean like_disabled;
            public int stream_mode;
            public boolean app_disable;
            public boolean download_disable;
            public boolean share_disable;
            public boolean circle;
            public int play_mode;
        }

        public static class Security {
            public String encrypt_string;
            public long ip;
        }

        public static class User {
            public String uid;
        }

        public static class Network {
            public String dma_code;
            public String area_code;
        }

        public static class Video {
            public String logo;
            public String published_time;
            public int userid;
            public String privacy;
            public int category_id;
            public String upload;
            public int restrict;
            public String username;
            public boolean share;
            public String title;
            public int source;
            public String seconds;
            public String encodeid;
            public String category_letter_id;
            public Channel channel;
            public List<String> tags;
            public List<String> share_type;
            public List<String> type;
            /**
             * id : 2221
             * name : 网络游戏
             */

            public List<Subcategories> subcategories;

            public static class Channel {
                public List<String> tail;
            }

            public static class Subcategories {
                public String id;
                public String name;
            }
        }

        public static class Stream {
            public String logo;
            public String media_type;
            public String audio_lang;
            public String transfer_mode_org;
            public String subtitle_lang;
            public String stream_type;
            public int width;
            public String transfer_mode;
            public int size;
            public int height;
            public int milliseconds_video;
            public String drm_type;
            public int milliseconds_audio;
            public String stream_fileid;
            /**
             * total_milliseconds_audio : 3475981
             * fileid : 030020010057272783A01D04B921BFBA6CB8C1-3116-796C-61FE-8F7DF7F67165
             * total_milliseconds_video : 3475733
             * key : a200eb29eff7e011261f267a
             * size : 101782498
             */

            public List<Segs> segs;

            public static class Segs {
                public String total_milliseconds_audio;
                public String fileid;
                public String total_milliseconds_video;
                public String key;
                public String size;
            }
        }
    }
}
