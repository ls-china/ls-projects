package com.ls.md.entity;

/**
 * Created by hx on 16-7-29.
 */
public class EpEntity {
    public String sid;
    public String token;
    public String ep;

    public EpEntity() {
    }

    public EpEntity(String sid, String token, String ep) {
        this.sid = sid;
        this.token = token;
        this.ep = ep;
    }

    @Override
    public String toString() {
        return "EpEntity{" +
                "sid='" + sid + '\'' +
                ", token='" + token + '\'' +
                ", ep='" + ep + '\'' +
                '}';
    }
}
