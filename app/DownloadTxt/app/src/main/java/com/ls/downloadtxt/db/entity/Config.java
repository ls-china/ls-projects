package com.ls.downloadtxt.db.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by hx on 16-7-22.
 */
@Table(name = "tbl_onfig")
public class Config implements Serializable {
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "use_mobile_net")
    private boolean useMobileNet;
    @Column(name = "download_path")
    private String downloadPath;

    public Config() {
    }

    public Config(int id, boolean useMobileNet, String downloadPath) {
        this.id = id;
        this.useMobileNet = useMobileNet;
        this.downloadPath = downloadPath;
    }

    public boolean isUseMobileNet() {
        return useMobileNet;
    }

    public void setUseMobileNet(boolean useMobileNet) {
        this.useMobileNet = useMobileNet;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
