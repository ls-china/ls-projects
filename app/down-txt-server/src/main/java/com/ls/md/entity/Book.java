package com.ls.md.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ls.core.entity.BasicEntity;

/**
 * Created by hx on 16-7-20.
 */
public class Book extends BasicEntity {
    private String title;
    private String tag;
    private String author;
    private String size;
    private String status;
    private String updateTime;
    private String url;
    private String image;
    private String describtion;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribtion() {
        return describtion;
    }

    @Override
    @JsonIgnore
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", tag='" + tag + '\'' +
                ", author='" + author + '\'' +
                ", size='" + size + '\'' +
                ", status='" + status + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", url='" + url + '\'' +
                ", image='" + image + '\'' +
                ", describtion='" + describtion + '\'' +
                '}';
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }
}
