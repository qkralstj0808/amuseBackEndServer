package com.example.amusetravelproejct.controller;

import com.example.amusetravelproejct.dto.response.ResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {



    @GetMapping("/amusetest")
    public ResponseDto ctest(){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setTestText("develop 버젼");
        return responseDto;
    }

}
