package com.fzy.springserctity.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
// 自定义登录成功处理器
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 获取登录的用户
        User user = (User) authentication.getPrincipal();
        System.out.println(user.getUsername());
        // 安全考虑不会输出密码
        System.out.println(user.getPassword());
        System.out.println(user.getAuthorities());
        response.sendRedirect("main.html");
    }
}
