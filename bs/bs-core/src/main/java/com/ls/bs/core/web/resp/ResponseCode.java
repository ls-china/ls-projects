package com.ls.bs.core.web.resp;

/**
 * Created by hx on 2016/3/29.
 */
public enum ResponseCode {
    SUCCESS("000000", "成功"),
    SYSTEM("000001", "系统异常"),
    DATABASE("000002", "数据库异常"),
    ILLEGAL_ARG("000003", "参数错误"),
    USER_NOT_FOUND("010000", "用户不存在"),
    USER_NOT_LOGIN("010001", "用户未登录"),
    USER_NOT_EXSIST("010002", "用户已存在"),
    USER_PARAM_ERROR_USER("010003", "用户名不存在"),
    USER_PARAM_ERROR_PASS("010005", "密码不正确"),
    USER_PASS_EQUAL_ORIEN("010006", "新密码不能与原密码相同"),
    ACCOUNT_NOT_FOUND("020000", "账目不存在"),
    ACCOUNT_MODIFY_NOT_OWNER("020001", "不能修改别人创建的账目"),
    RESOURCE_NOT_FOUND("030000", "资源不存在"),
    ;
    String code;
    String msg;
    ResponseCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "ResponseCode{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
