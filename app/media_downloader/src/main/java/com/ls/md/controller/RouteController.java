package com.ls.md.controller;

import com.ls.md.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api")
@RestController("RouteController")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @RequestMapping("")
    @ResponseBody
    public String def() {
        return "it works!";
    }

    @RequestMapping(value = "/tags", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getTags() {
        return null;
    }

    @RequestMapping(value = "/{tag}/{page}")
    @ResponseBody
    public String getPage(
            @PathVariable("tag") String tag,
            @PathVariable("page") String page
    ) {
        return null;
    }

}
