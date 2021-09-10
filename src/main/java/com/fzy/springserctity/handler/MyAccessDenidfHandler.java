package com.fzy.springserctity.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// 自定义403
@Component
public class MyAccessDenidfHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setHeader("Content-type","application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write("{\"status\":\"error\",\"msg\":\"权限不足\"}");
        writer.flush();
        writer.close();

    }
}
