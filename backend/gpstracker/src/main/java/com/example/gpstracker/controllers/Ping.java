package com.example.gpstracker.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Ping {
    @GetMapping("/")
    public HttpStatus ping(){
        return HttpStatus.OK;
    }
}
