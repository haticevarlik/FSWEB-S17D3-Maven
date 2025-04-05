package com.workintech.zoo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class demoController {
    @GetMapping("/hi")
    public String hi(){
        return "Hi!";
    }
}
