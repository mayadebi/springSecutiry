package com.fzy.springserctity.config;

import com.fzy.springserctity.handler.MyAuthenticationFailureHandler;
import com.fzy.springserctity.handler.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                // 自定义登录参数
                .usernameParameter("username")
                .passwordParameter("password")
                // 自定义登录页面
                .loginPage("/login.html")
                // 必须和表单提交的接口一样 就会去执行自定义登录
                .loginProcessingUrl("/login")
                // 登录成功后跳转的页面  post请求需要在controller配置
//                .successForwardUrl("/toMain")
                // 使用自定义登录处理器
                .successHandler(myAuthenticationSuccessHandler)
                // 登录失败跳转
//                .failureForwardUrl("/toError")
                // 使用自定义失败处理器
                .failureHandler(myAuthenticationFailureHandler)
        ;
        // 授权
        http.authorizeRequests()
                // 设置白名单
                .antMatchers("/login.html","/error.html").permitAll()
                // 所有的请求必须认证
                .anyRequest().authenticated();
        // 关闭防火墙
        http.csrf().disable();
    }

    // 把PasswordEncoder注册到spring  用来加密解密密码
    @Bean
    public PasswordEncoder getPs() {
        return new BCryptPasswordEncoder();
    }
}
