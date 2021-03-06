package com.fzy.springserctity.config;

import com.fzy.springserctity.handler.MyAccessDenidfHandler;
import com.fzy.springserctity.handler.MyAuthenticationFailureHandler;
import com.fzy.springserctity.handler.MyAuthenticationSuccessHandler;
import com.fzy.springserctity.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 设置白名单
    public static final String[] whiteList = {
            "/login",
            "/logout",
            "/oauth/**"
//            "/error.html"
//            "/css/**",
//            "/**/*.css",
//            "/js/**",
//            "/**/*.js",
//            "/img/**",
//            "/**/*.png",
//            "/**/*.jpg",
    };
    // 设置正则表达式白名单
//    public static final String[] zzWhiteList = {
//            ".+[.]png"
//    };
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    @Autowired
    private MyAccessDenidfHandler myAccessDenidfHandler;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private PersistentTokenRepository persistentTokenRepository;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    // 这个类给密码授权用的
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                // 自定义登录参数
                .usernameParameter("username")
                .passwordParameter("password")
                // 自定义登录页面
                .loginPage("/login")
                // 必须和表单提交的接口一样 就会去执行自定义登录
//                .loginProcessingUrl("/login")
                // 登录成功后跳转的页面  post请求需要在controller配置
//                .successForwardUrl("/toMain")
                // 使用自定义登录处理器
                .successHandler(myAuthenticationSuccessHandler)
                // 登录失败跳转
//                .failureForwardUrl("/toError")
                // 使用自定义失败处理器
                .failureHandler(myAuthenticationFailureHandler)
                .and()
                // 授权
                .authorizeRequests()
                // 设置白名单
                .antMatchers(whiteList).permitAll()
                // 正则表达式  可以匹配方法类型  可以不写
//                .regexMatchers(HttpMethod.GET,zzWhiteList).permitAll()
                // 权限控制 有这个权限才能访问这个路径
//                .antMatchers("/main1.html").hasAnyAuthority("admin","fzy")
                // 使用角色控制
//                .antMatchers("/main2.html").hasAnyRole("fzy")
                // 使用IP地址放行
//                .antMatchers("/main3.html").hasIpAddress("127.0.0.1")
                // 所有的请求必须认证
                // 必须放在最后面
                .anyRequest().authenticated()
                .and()
                // 自定义403
                .exceptionHandling().accessDeniedHandler(myAccessDenidfHandler)
                .and()
                // 记住我
                .rememberMe()
                // 设置数据源
                .tokenRepository(persistentTokenRepository)
                // 超时时间默认俩周
                .tokenValiditySeconds(60)
                // 自定义登录逻辑
                .userDetailsService(userDetailService)
                .and()
                // 退出
                .logout()
                // 退出成功后跳转的页面
                .logoutSuccessUrl("/login")
                // 定义退出接口 莫如logout  可以不写
                .logoutUrl("/logout")
                // 关闭防火墙
//                .and().csrf().disable()
        ;
    }

    // 把PasswordEncoder注册到spring  用来加密解密密码
    @Bean
    public PasswordEncoder getPs() {
        return new BCryptPasswordEncoder();
    }

    // 记住我要注入的bean
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        // 设置数据源
        jdbcTokenRepository.setDataSource(dataSource);
        // 自动建表  第一次启动时开启 第二次启动注释掉
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }
}
