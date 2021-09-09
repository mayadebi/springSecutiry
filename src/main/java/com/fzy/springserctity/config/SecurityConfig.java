package com.fzy.springserctity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {
    // 把PasswordEncoder注册到spring  用来加密解密密码
    @Bean
    public PasswordEncoder getPs(){
        return new BCryptPasswordEncoder();
    }
}
