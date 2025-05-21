package com.hdm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class HelloWorldApplication {

    public static void main(String[] args) {
        System.setProperty("javax.net.ssl.trustStore", "D:\\IdeaProjects\\Spring_Single\\src\\main\\resources\\truststore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
        SpringApplication.run(HelloWorldApplication.class, args);
    }

}
