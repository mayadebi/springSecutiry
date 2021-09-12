package com.fzy.springserctity.config;

import com.fzy.springserctity.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

// 授权码服务器
@Configuration
@EnableAuthorizationServer
public class SouQuanConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    @Qualifier("redisTokenStore")
    private TokenStore tokenStore;
    // 密码授权
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                // 自定义登录逻辑
                .userDetailsService(userDetailService)
                // 授权管理器
                .authenticationManager(authenticationManager)
                // 存入redis
                .tokenStore(tokenStore)
        ;
        ;
    }

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
                .authorizedGrantTypes("authorization_code","password");
    }
}
