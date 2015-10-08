package com.edgar.springboot.start;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Value("${spring.application.name}")
    private String applicationName;

    @RequestMapping("/")
    String home() {
        System.out.println(applicationName);
        return "Hello World!";
    }
}