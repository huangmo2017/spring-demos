package com.hdm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// ToolController.java
@Controller
public class ToolController {

    @GetMapping("/")
    public String home() {
        return "redirect:/tools/k8s/pod"; // 默认跳转到 Pod Explorer
    }


    // 列转逗号字符串工具页面
    @GetMapping("/tools/utils/column-to-csv")
    public String showColumnToCSVTool(Model model) {
        model.addAttribute("pageTitle", "列转逗号字符串工具");
        return "tools/utils/column-to-csv"; // 返回模板路径
    }
}
