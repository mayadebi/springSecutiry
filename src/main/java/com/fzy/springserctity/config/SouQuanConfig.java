package com.fzy.springserctity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
// 授权码服务器
@Configuration
@EnableAuthorizationServer
public class SouQuanConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 设置客户端ID
                .withClient("admin")
                // 设置密码
                .secret(passwordEncoder.encode("123456"))
                // 重定向地址获取授权码
                .redirectUris("http://www.baibu.com")
                // 授权范围
                .scopes("all")
                // 授权类型
                .authorizedGrantTypes("authorization_code");
    }
}
