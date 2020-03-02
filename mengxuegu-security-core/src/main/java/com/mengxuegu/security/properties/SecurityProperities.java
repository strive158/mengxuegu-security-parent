package com.mengxuegu.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="mengxuegu.security")
public class SecurityProperities {
    private AuthenticationProperties authentication;

}
