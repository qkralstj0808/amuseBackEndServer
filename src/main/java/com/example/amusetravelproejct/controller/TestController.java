package com.example.amusetravelproejct.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {



    @GetMapping("/amusetest")
    public String ctest(){
        return "어뮤즈트레블 테스트 66";
    }

}
