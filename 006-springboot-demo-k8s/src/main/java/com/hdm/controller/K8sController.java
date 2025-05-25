// src/main/java/com/hdm/controller/K8sController.java

package com.hdm.controller;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.client.KubernetesClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
@Slf4j
public class K8sController {

    private final KubernetesClient client;

    public K8sController(KubernetesClient client) {
        this.client = client;
    }

    // 主页：展示命名空间下拉框 + Pod 列表
    @GetMapping
    public String listPods(@RequestParam(defaultValue = "default") String namespace, Model model) {
        List<Namespace> namespaces = client.namespaces().list().getItems();
        PodList podList = client.pods().inNamespace(namespace).list();

        model.addAttribute("namespaces", namespaces);
        model.addAttribute("pods", podList.getItems());
        model.addAttribute("currentNamespace", namespace);

        return "index";
    }

    // 查看 Pod 日志（支持容器选择）
    @GetMapping("/logs")
    public String getPodLogs(
            @RequestParam String namespace,
            @RequestParam String podName,
            @RequestParam(required = false) String containerName, // 可选参数
            Model model) {

        Pod pod = client.pods().inNamespace(namespace).withName(podName).get();

        log.info("access log method");

        if (pod == null) {
            model.addAttribute("error", "Pod not found");
            return "logs";
        }

        if (containerName == null || containerName.isEmpty()) {
            // 如果未指定容器，默认取第一个
            if (!pod.getSpec().getContainers().isEmpty()) {
                containerName = pod.getSpec().getContainers().get(0).getName();
            }
        }

        String logs = client.pods()
                .inNamespace(namespace)
                .withName(podName)
                .inContainer(containerName)
                .getLog(); // 获取指定容器的日志

        model.addAttribute("podName", podName);
        model.addAttribute("namespace", namespace);
        model.addAttribute("containerName", containerName);
        model.addAttribute("containers", pod.getSpec().getContainers()); // 所有容器列表
        model.addAttribute("logs", logs);

        return "logs"; // 返回显示日志的页面
    }
}
