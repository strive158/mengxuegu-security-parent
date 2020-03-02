package com.mengxuegu.security.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class AuthenticationProperties {
    @Value("/login/page")
    private String loginPage;
    @Value("/login/form")
    private String loginPrecessingUrl;
    @Value("name")
    private String usernameParameter;
    @Value("pwd")
    private String passwordParameter;
    @Value("/dist/**,/modules/**,/plugins/**")
    private String[] staticPath;

}
