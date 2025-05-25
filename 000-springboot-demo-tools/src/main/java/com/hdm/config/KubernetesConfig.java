package com.hdm.config;

import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KubernetesConfig {

    @Bean
    public KubernetesClient kubernetesClient() {
        // 使用 KubernetesClientBuilder 来构建客户端实例
        return new KubernetesClientBuilder().build();
        // 如果需要指定特定的 kubeconfig 文件路径，可以使用如下方式：
        // return new DefaultKubernetesClient(Config.fromKubeconfig(new File("/path/to/kubeconfig").getAbsolutePath()));
    }
}
