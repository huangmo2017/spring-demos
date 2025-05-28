package com.hdm.controller;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.client.KubernetesClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tools/k8s")
@Slf4j
public class K8sController {

    private final KubernetesClient client;


    @Value("${kubernetes.namespace}")
    private String namespaceName;

    public K8sController(KubernetesClient client) {
        this.client = client;
    }

    // 显示 Pod Explorer 工具页面
    @GetMapping("/pod")
    public String showPodExplorer(@RequestParam(defaultValue = "default") String namespace,
                                  Model model) {

        List<Namespace> namespaces = new ArrayList<>();

        if ("all".equals(namespaceName)) {
            namespaces = client.namespaces().list().getItems();
        } else {
            Namespace myNamespace = client.namespaces()
                    .withName(namespaceName)
                    .get();
            namespaces.add(myNamespace);
        }


        PodList podList = client.pods().inNamespace(namespace).list();

        model.addAttribute("namespaces", namespaces);
        model.addAttribute("pods", podList.getItems());
        model.addAttribute("currentNamespace", namespace);

        model.addAttribute("toolContent", "tools/k8s/pod-explorer"); // 指定模板片段
        model.addAttribute("pageTitle", "Kubernetes Pod Explorer");

        return "index"; // 用于直接访问时显示完整页面
    }

    // 新增一个片段加载接口
    @GetMapping(path = "/pod", params = "fragment")
    public String loadPodFragment(@RequestParam(defaultValue = "default") String namespace,
                                  Model model) {
        List<Namespace> namespaces = new ArrayList<>();

        if ("all".equals(namespaceName)) {
            namespaces = client.namespaces().list().getItems();
        } else {
            Namespace myNamespace = client.namespaces()
                    .withName(namespaceName)
                    .get();
            namespaces.add(myNamespace);
        }
        PodList podList = client.pods().inNamespace(namespace).list();

        model.addAttribute("namespaces", namespaces);
        model.addAttribute("pods", podList.getItems());
        model.addAttribute("currentNamespace", namespace);

        return "tools/k8s/pod-explorer"; // 返回片段内容
    }

    // 查看 Pod 日志
    @GetMapping("/logs")
    public String getPodLogs(
            @RequestParam String namespace,
            @RequestParam String podName,
            @RequestParam(required = false) String containerName,
            Model model) {

        Pod pod = client.pods().inNamespace(namespace).withName(podName).get();

        if (pod == null) {
            model.addAttribute("error", "Pod not found");
            return "tools/k8s/logs";
        }

        if (containerName == null || containerName.isEmpty()) {
            if (!pod.getSpec().getContainers().isEmpty()) {
                containerName = pod.getSpec().getContainers().get(0).getName();
            }
        }

        String logs = client.pods()
                .inNamespace(namespace)
                .withName(podName)
                .inContainer(containerName)
                .getLog();

        model.addAttribute("podName", podName);
        model.addAttribute("namespace", namespace);
        model.addAttribute("containerName", containerName);
        model.addAttribute("containers", pod.getSpec().getContainers());
        model.addAttribute("logs", logs);

        return "tools/k8s/logs";
    }
}
