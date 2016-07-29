package com.ls.bs.core.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

/**
 * Created by hx on 2016/3/31.
 */
@Component
public class ContextWrapper {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ServletContext servletContext;


    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public String getWebRealPath(String name) {
        return servletContext.getRealPath(name);
    }
}
