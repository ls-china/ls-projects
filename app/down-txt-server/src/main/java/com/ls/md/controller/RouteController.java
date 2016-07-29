package com.ls.md.controller;

import com.ls.md.entity.Pager;
import com.ls.md.service.RouteService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return routeService.getTags();
    }

    @RequestMapping(value = "/{tag}/{page}")
    @ResponseBody
    public Pager getPage(
            @PathVariable("tag") String tag,
            @PathVariable("page") String page
    ) {
        return routeService.getPage(tag, NumberUtils.toInt(page));
    }

}
