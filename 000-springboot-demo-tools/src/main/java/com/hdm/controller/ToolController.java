package com.hdm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// ToolController.java
@Controller
public class ToolController {

    @GetMapping("/")
    public String home() {
        return "redirect:/tools/k8s/pod"; // 默认跳转到 Pod Explorer
    }
}
