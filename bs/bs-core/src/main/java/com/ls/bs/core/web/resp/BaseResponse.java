package com.ls.bs.core.web.resp;

import java.io.Serializable;

/**
 * Created by hx on 2016/3/29.
 */
public class BaseResponse implements Serializable {
    protected String code;
    protected String msg;
    protected Object value;

    public BaseResponse() {
    }

    public BaseResponse(ResponseCode code) {
        this.code = code.getCode();
        this.msg = code.getMsg();
    }

    public BaseResponse(String code, Object value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "value=" + value +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
