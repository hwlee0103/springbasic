package com.example.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/hello")
@Controller
public class HelloServletController {

    @GetMapping("")
    public String hello(String name) {
        return "Hello World : " + name;
    }
}
