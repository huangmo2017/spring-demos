package com.hdm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.vault.annotation.VaultPropertySource;
import org.springframework.vault.config.EnvironmentVaultConfiguration;

@Configuration
@VaultPropertySource(value = {"secret/myapp"})
@PropertySource("vault.properties")
@Import(EnvironmentVaultConfiguration.class)
public class VaultConfig {
}
