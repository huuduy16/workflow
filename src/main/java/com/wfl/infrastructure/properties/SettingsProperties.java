package com.wfl.infrastructure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "settings")
public class SettingsProperties {

    private String timeZone;
    private Domain domain;

    @Data
    public static class Domain {

        private String web;
        private String api;
    }
}