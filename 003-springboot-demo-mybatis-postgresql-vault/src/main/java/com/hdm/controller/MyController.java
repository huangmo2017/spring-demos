package com.hdm.controller;

import com.hdm.service.SecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @Value("${db.username}")
    private String dbUsername;

    @Value("${db.password}")
    private String dbPassword;

    @Autowired
    private SecretService secretService;

    @GetMapping("/show")
    public String show() {
        String secretPassword = secretService.getSecretPassword();
        System.out.println("Secret password is " + secretPassword);
        return "Database username is " + dbUsername + " and password is " + dbPassword;
    }
}
