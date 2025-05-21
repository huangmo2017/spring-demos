package com.hdm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Description
 * @Author HDM
 * @Date 2025-05-15 下午10:44
 */
@RestController
public class HelloController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("hello")
    public String hello() {
        String url = "https://localhost:8091/hello";
        return restTemplate.getForObject(url, String.class);
    }
}
