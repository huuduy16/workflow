package com.wfl.infrastructure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String tokenKey;
    private String tokenSecret;
    private int tokenExpirationSec;
}
