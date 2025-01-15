package com.example.linebot;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Push {
    @GetMapping("test")
    public String hello(HttpServletRequest request) {
        return "Get from " + request.getRequestURL();
    }
}
