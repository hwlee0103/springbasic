package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/hello")
@Controller
public class HelloServletController {
    // view 페이지를 Return해 주지 않으므로 localhost로 띄웠을 때 404 에러 발생할 것
    @GetMapping("")
    public String hello(String name) {
        return "Hello World : " + name;
    }
}
