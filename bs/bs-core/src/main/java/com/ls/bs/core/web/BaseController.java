package com.ls.bs.core.web;

import com.ls.bs.core.web.resp.BaseResponse;
import com.ls.bs.core.web.resp.PageableResponse;
import com.ls.bs.core.web.resp.ResponseCode;
import com.ls.bs.core.web.resp.ResponseCodeException;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static com.ls.bs.core.web.resp.ResponseCode.ILLEGAL_ARG;

/**
 * Created by hx on 2016/3/29.
 */
public class BaseController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public BaseResponse getSuccessResponse() {
        return getSuccessResponse(null);
    }

    public BaseResponse getSuccessResponse(Object value) {
        return getResponse(ResponseCode.SUCCESS.getCode(), value);
    }

    public PageableResponse getSuccessPageableResponse(Page page) {
        PageableResponse response = getPageableResponse();
        response.setValue(page.getContent());
        response.setFirst(page.isFirst());
        response.setLast(page.isLast());
        response.setHasNext(page.hasNext());
        response.setHasPrevious(page.hasPrevious());
        response.setNumberOfElements(page.getNumberOfElements());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(response.getTotalPages());
        return response;
    }

    public BaseResponse getResponse(IllegalArgumentException e) {
        BaseResponse response = new BaseResponse();
        response.setCode(ILLEGAL_ARG.getCode());
        response.setMsg(ILLEGAL_ARG.getMsg() + " : " + e.getMessage());
        return response;
    }

    public BaseResponse getResponse(String code, Object value) {
        return new BaseResponse(code, value);
    }

    public BaseResponse getResponse() {
        return getResponse(ResponseCode.SUCCESS);
    }

    public BaseResponse getResponse(ResponseCodeException exception) {
        return getResponse(exception.getResponseCode());
    }

    public BaseResponse getResponse(ResponseCode code) {
        return new BaseResponse(code);
    }

    public PageableResponse getPageableResponse(IllegalArgumentException e) {
        PageableResponse response = new PageableResponse();
        response.setCode(ILLEGAL_ARG.getCode());
        response.setMsg(ILLEGAL_ARG.getMsg() + " : " + e.getMessage());
        return response;
    }

    public PageableResponse getPageableResponse(String code, Object value) {
        return new PageableResponse(code, value);
    }

    public PageableResponse getPageableResponse() {
        return getPageableResponse(ResponseCode.SUCCESS);
    }

    public PageableResponse getPageableResponse(ResponseCodeException exception) {
        return this.getPageableResponse(exception.getResponseCode());
    }

    public PageableResponse getPageableResponse(ResponseCode code) {
        return new PageableResponse(code);
    }

    public PageRequest getPageRequest(String page, String size) {
        int ipage = NumberUtils.toInt(page, 0);
        int isize = NumberUtils.toInt(size, 10);
        if ( ipage < 0 ) {
            ipage = 0;
        }
        if ( isize <= 0 ) {
            isize = 10;
        }
        return new PageRequest(ipage, isize);
    }
}
