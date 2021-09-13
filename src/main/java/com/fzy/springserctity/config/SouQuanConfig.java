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
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

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
    @Qualifier("jwtTokenStore")
    private TokenStore tokenStore;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    private JwtTokenEnhancer jwtTokenEnhancer;

//    @Autowired
//    @Qualifier("redisTokenStore")
//    private TokenStore tokenStore;
    // 密码授权
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 设置自定义的内容
        TokenEnhancerChain chain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(jwtTokenEnhancer);
        delegates.add(jwtAccessTokenConverter);
        chain.setTokenEnhancers(delegates);

        endpoints
                // 自定义登录逻辑
                .userDetailsService(userDetailService)
                // 授权管理器
                .authenticationManager(authenticationManager)
                // 存入redis
//                .tokenStore(tokenStore)
                // 整合jwt
                .tokenStore(tokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                .tokenEnhancer(chain)


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
                // 设置令牌时间
                .accessTokenValiditySeconds(60)
                // 设置刷新令牌时间
                .refreshTokenValiditySeconds(86400)
                // 自动授权
                .autoApprove(true)
                // 授权类型  authorization_code 授权码
                // password 密码模式
                // refresh_token 刷新令牌
                .authorizedGrantTypes("authorization_code","password","refresh_token");
    }
}
