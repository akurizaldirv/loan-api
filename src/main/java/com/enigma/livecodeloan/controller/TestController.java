package com.enigma.livecodeloan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/api")
    public String hello() {
        return "Hello";
    }
}
