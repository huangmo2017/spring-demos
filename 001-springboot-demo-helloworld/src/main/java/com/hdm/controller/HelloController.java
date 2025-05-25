package com.hdm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author HDM
 * @Date 2025-05-15 下午10:44
 */
@RestController
@Slf4j
public class HelloController {

    @GetMapping("hello")
    public String hello() {
        log.info("access hello method");
        return "Hello World2";
    }
}
