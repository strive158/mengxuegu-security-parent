package com.mengxuegu.security.config;

import com.mengxuegu.security.properties.SecurityProperities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@EnableWebSecurity //开启springsecurity过滤器链
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SecurityProperities securityProperities;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    /**
     * 认证管理器
     * 1.认证信息（用户名，密码）
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //数据库或者内存存储的密码必须加密的，不然会There is no PasswordEncoder mapped for the id "null"报错。
        //加密方式：铭文+随机盐值
        PasswordEncoder passwordEncoder = passwordEncoder();
        String passwordd = passwordEncoder.encode("1234");
        log.info("加密之后的密码-------"+passwordd);
        auth.inMemoryAuthentication().withUser("mengxuegu")
                .password(passwordd)
                .authorities("ADMIN");//给用户授权，随意写一个授权标识
    }

    /**
     * 资源权限配置：
     * 1.被拦截的资源
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.httpBasic()//采用httpbasic认证方式
        http.formLogin()//采用表单认证方式
                .loginPage(securityProperities.getAuthentication().getLoginPage())
                .loginProcessingUrl(securityProperities.getAuthentication().getLoginPrecessingUrl())
                .passwordParameter(securityProperities.getAuthentication().getPasswordParameter())
                .usernameParameter(securityProperities.getAuthentication().getUsernameParameter())
        .and() //连接符
        .authorizeRequests()//认证请求
                .antMatchers(securityProperities.getAuthentication().getLoginPage()).permitAll()//放行/login/page页面所有的权限
        .anyRequest().authenticated()//所有访问该应用的http请求都要通过身份认证才可以访问
        ;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(securityProperities.getAuthentication().getStaticPath());
    }
}
