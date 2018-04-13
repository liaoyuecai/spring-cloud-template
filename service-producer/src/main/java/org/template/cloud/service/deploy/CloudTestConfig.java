package org.template.cloud.service.deploy;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ConfigurationProperties(prefix = "test")
public class CloudTestConfig {
    String name;

    @Bean
    @RefreshScope
    public String name() {
        return name;
    }
}
