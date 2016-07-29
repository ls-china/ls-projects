package com.ls.test.controller;

import com.ls.test.entity.TestResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hx on 16-7-18.
 */
@RestController("TestController")
public class TestController {

    @RequestMapping("/cros")
    @ResponseBody
    public TestResponse test() {
        return new TestResponse("Yo Yo ,Test , See this, not cros!");
    }

}
