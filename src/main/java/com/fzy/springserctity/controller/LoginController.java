package com.fzy.springserctity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @RequestMapping("/toMain")
    public String login(){
        return "redirect:main.html";
    }
    @RequestMapping("/toError")
    public String error(){
        return "redirect:error.html";
    }
}
