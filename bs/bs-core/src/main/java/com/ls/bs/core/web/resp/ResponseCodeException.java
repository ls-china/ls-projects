package com.ls.bs.core.web.resp;

/**
 * Created by hx on 2016/3/29.
 */
public class ResponseCodeException extends RuntimeException {
    private ResponseCode responseCode;

    public ResponseCodeException(ResponseCode code) {
        super(code.toString());
        this.responseCode = code;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }
}
