package com.example.amusetravelproejct.controller;

import com.example.amusetravelproejct.config.resTemplate.ResponseException;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.dto.response.DetailPageResponse;
import com.example.amusetravelproejct.service.DetailPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
//@Api(tags = {"Forum Api"})
@RequestMapping("/detail/{id}")
public class DetailPageController {

    private final DetailPageService detailPageService;

    @GetMapping("/title")
    public ResponseTemplate<DetailPageResponse.getTitle> getTitle(@PathVariable("id")  Long product_id) throws IOException, ResponseException {
//        Long user_id = jwtService.getmemberId();
        ResponseTemplate<DetailPageResponse.getTitle> title = null;
        try{
            title = detailPageService.getTitle(product_id);
        }catch (Exception e){
            e.printStackTrace();
        }

        return title;
    }

    @GetMapping("/icon-info")
    public ResponseTemplate<DetailPageResponse.getIcon> getIcon(@PathVariable("id")  Long product_id) throws IOException, ResponseException {
//        Long user_id = jwtService.getmemberId();
        return detailPageService.getIcon(product_id);
    }

    @GetMapping("/picture")
    public ResponseTemplate<DetailPageResponse.getPicture> getPicture(@PathVariable("id")  Long product_id) throws IOException, ResponseException {
//        Long user_id = jwtService.getmemberId();
        return detailPageService.getPicture(product_id);
    }

    @GetMapping("/ticket-select")
    public ResponseTemplate<DetailPageResponse.getTicket> getTicket(@PathVariable("id")  Long product_id) throws IOException, ResponseException {
//        Long user_id = jwtService.getmemberId();
        return detailPageService.getTicket(product_id);
    }

    @GetMapping("/product-intro")
    public ResponseTemplate<DetailPageResponse.getContent> getContent(@PathVariable("id")  Long product_id) throws IOException, ResponseException {
//        Long user_id = jwtService.getmemberId();
        return detailPageService.getContent(product_id);
    }

    @GetMapping("/course-intro")
    public ResponseTemplate<DetailPageResponse.getCourseContent> getCourseContent(@PathVariable("id")  Long product_id) throws IOException, ResponseException {
//        Long user_id = jwtService.getmemberId();
        return detailPageService.getCourseContent(product_id);
    }

    @GetMapping("/other-info")
    public ResponseTemplate<DetailPageResponse.getOtherContent> getOtherContent(@PathVariable("id")  Long product_id) throws IOException, ResponseException {
//        Long user_id = jwtService.getmemberId();
        return detailPageService.getOtherContent(product_id);
    }
}
