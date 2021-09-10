package com.fzy.springserctity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
// 开启security注解
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SpringSerctityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSerctityApplication.class, args);
    }

}
