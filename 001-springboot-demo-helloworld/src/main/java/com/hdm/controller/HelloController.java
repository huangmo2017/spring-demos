package com.hdm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author HDM
 * @Date 2025-05-15 下午10:44
 */
@RestController
public class HelloController {

    @GetMapping("hello")
    public String hello() {
        return "Hello World2";
    }
}
