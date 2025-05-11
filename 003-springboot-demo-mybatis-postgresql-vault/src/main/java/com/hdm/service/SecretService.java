package com.hdm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.support.VaultResponse;

import java.util.Map;

@Service
public class SecretService {

    private final VaultOperations vaultOperations;

    @Autowired
    public SecretService(VaultOperations vaultOperations) {
        this.vaultOperations = vaultOperations;
    }

    public String getSecretPassword() {
        VaultResponse read = vaultOperations.read("secret/data/helloworld");
        Map<String, Object> data = read.getData();
        return vaultOperations.read("secret/data/helloworld").getData().get("data").toString();
    }
}
