package com.liangrui.hadoop_disk.hadoop_disk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
public class helloTest {
    @RequestMapping("/index")
    public String index()
    {
        return "hello";
    }

}
