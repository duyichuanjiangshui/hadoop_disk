package com.liangrui.hadoop_disk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class indexController {
    @RequestMapping("/mainindex")
    public String mainIndex()
    {
        return "iframe";
    }
    @RequestMapping("/test")
    public String test()
    {
        return "hello";
    }

}
