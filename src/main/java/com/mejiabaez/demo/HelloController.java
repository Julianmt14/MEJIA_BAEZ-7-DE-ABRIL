package com.mejiabaez.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Hola desde MEJIA_BAEZ Spring Boot!";
    }
}
