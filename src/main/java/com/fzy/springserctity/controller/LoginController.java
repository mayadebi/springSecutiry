package com.fzy.springserctity.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    // 设置注解权限  判断有没有这个角色
    @Secured("ROLE_fzy")
    @RequestMapping("/toMain")
    public String login(){
        return "redirect:main.html";
    }
    // 可以根据权限或者角色判断
    //hasAnyAuthority("admin","fzy")
    //hasAnyRole("fzy")
    @PreAuthorize("hasRole('test')")
    @RequestMapping("/toError")
    public String error(){
        return "redirect:error.html";
    }
}
