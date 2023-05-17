package com.example.amusetravelproejct.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.config.util.UtilMethod;
import com.example.amusetravelproejct.domain.*;
import com.example.amusetravelproejct.dto.request.AdminPageRequest;
import com.example.amusetravelproejct.dto.request.ProductRegisterDto;
import com.example.amusetravelproejct.dto.response.AdminPageResponse;
import com.example.amusetravelproejct.repository.*;
import com.example.amusetravelproejct.repository.ItemRepository;
import com.example.amusetravelproejct.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

@AllArgsConstructor
@RestController
@RequestMapping("/test/api")
@Slf4j
public class AdminPageController {

    private final CategoryService categoryService;
    private final ItemService itemService;
    private final AdminService adminService;
    private final AlarmService alarmService;
    private final AdvertisementService advertisementService;
    private final ItemRepository itemRepository;

    private final ImgRepository imgRepository;
    private final ItemTicketRepository itemTicketRepository;
    private final ItemCourseRepository itemCourseRepository;
    private final ItemHashTagRepository itemHashTagRepository;
    private final AdminRepository adminRepository;
    private final AdvertisementRepository adminAdvertisementRepository;
    private final AlarmRepository alarmRepository;
    private final ItemTicketPriceRepository itemTicketPriceRepository;
    private final CategoryRepository categoryRepository;

    private final AmazonS3 amazonS3Client;
    ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/ad/register")
    public ResponseTemplate<AdminPageResponse.advertisementRegister> reqAdvertisementRegister(@RequestBody AdminPageRequest.advertisementRegister adminAdvertisementRegisterDto){
        UtilMethod utilMethod = new UtilMethod(amazonS3Client);

        //TODO
        // 유저 데이터 선 처리


        AdminPageResponse.advertisementRegister  advertisement = advertisementService.processAdvertisementRegister(adminAdvertisementRegisterDto,adminService,utilMethod);
        return new ResponseTemplate<>(advertisement);
    }

    @PostMapping("/ad/edit")
    public ResponseTemplate<AdminPageResponse.advertisementEdit> reqAdvertisementEdit(@RequestBody AdminPageRequest.advertisementEdit adminAdvertisementRegisterDbDto) {
        UtilMethod utilMethod = new UtilMethod(amazonS3Client);


        //TODO
        // 유저 데이터 선 처리


        AdminPageResponse.advertisementEdit advertisement = advertisementService.processAdvertisementEdit(adminAdvertisementRegisterDbDto,adminService,utilMethod);

        return new ResponseTemplate<>(advertisement);
    }

    @GetMapping("/ad/getList")
    public ResponseTemplate<AdminPageResponse.advertisementResult> reqAdvertisementList(@RequestParam("offset") Long offset , @RequestParam("limit") int limit, @RequestParam("page") int page){
        //TODO
        // 유저 데이터 선 처리
        int sqlPage = page -1;

        AdminPageResponse.advertisementResult advertisementResult = new AdminPageResponse.advertisementResult(advertisementService.getPageCount(limit),page, advertisementService.processGetAllAdvertisements(offset,limit,sqlPage));
        return new ResponseTemplate<>(advertisementResult);
    }

    @GetMapping("/ad/{id}")
    public ResponseTemplate<AdminPageResponse.advertisementEdit> reqAdvertisementDetail(@PathVariable("id") Long id){

        //TODO
        // 유저 데이터 선 처리


        AdminPageResponse.advertisementEdit advertisement = advertisementService.processGetAdvertisementDetail(id);
        return new ResponseTemplate<>(advertisement);
    }

    @Transactional
    @PostMapping("/product/create")
    public ResponseTemplate<String> reqProductCreate(@RequestBody ProductRegisterDto productRegisterDto) throws ParseException {
        UtilMethod utilMethod = new UtilMethod(amazonS3Client);

        //TODO
        // productRegisterDto 데이터 선 처리

        Item item = itemService.processItem(productRegisterDto);
        itemService.processItemTicket(productRegisterDto,item);
        itemService.processItemImg(productRegisterDto,utilMethod,item);
        itemService.processItemCourse(productRegisterDto,utilMethod,item);
        return new ResponseTemplate<>("상품 생성 완료");
    }

    @PostMapping("/notice/register")
    public ResponseTemplate<AdminPageResponse.noticeRegister> noticeRegister(@RequestBody AdminPageRequest.noticeRegister noticeRegisterDto){

        //TODO
        // 유저 데이터 선 처리

        return new ResponseTemplate<>(alarmService.processRegisterNotice(noticeRegisterDto,adminService,alarmService));
    }

    @PostMapping("/notice/edit")
    public ResponseTemplate<AdminPageResponse.noticeEdit> noteiceEdit(@RequestBody AdminPageRequest.noticeEdit noticeEditDto){

        //TODO
        // 유저 데이터 선 처리

        return new ResponseTemplate<>(alarmService.processEditNotice(noticeEditDto,adminService));
    }

    @GetMapping("/notice/getList")
    public ResponseTemplate<AdminPageResponse.noticeResult> reqNoticeList(@RequestParam("offset") Long offset , @RequestParam("limit") int limit, @RequestParam("page") int page){

        //TODO
        // 유저 데이터 선 처리
        int sqlPage = page -1;

        AdminPageResponse.noticeResult noticeResult = new AdminPageResponse.noticeResult(alarmService.getPageCount(limit),page,alarmService.processGetAllNotices(offset,limit,sqlPage));

        return new ResponseTemplate<>(noticeResult);
    }
    @GetMapping("/notice/{id}")
    public ResponseTemplate<AdminPageResponse.noticeEdit> reqNoticeDetail(@PathVariable("id") Long id){

        //TODO
        // 유저 데이터 선 처리

        return new ResponseTemplate<>(alarmService.processGetNoticeDetail(id));
    }


    @PostMapping("/category/register")
    public ResponseTemplate<AdminPageResponse.categoryRegister> reqCategoryRegister(@RequestBody AdminPageRequest.categoryRegister  categoryRegisterDto){
        UtilMethod utilMethod = new UtilMethod(amazonS3Client);

        //TODO
        // categoryRegisterDto 데이터 선 처리


        return new ResponseTemplate<>(categoryService.processRegisterCategory(categoryRegisterDto,adminService,utilMethod));
    }

    @PostMapping("/category/edit")
    public ResponseTemplate<AdminPageResponse.categoryEdit> reqCategoryEdit(@RequestBody AdminPageRequest.categoryEdit categoryEditDto){
        UtilMethod utilMethod = new UtilMethod(amazonS3Client);

        return new ResponseTemplate<>(categoryService.processEditCategory(categoryEditDto,adminService,utilMethod));
    }

    @GetMapping("/category/sequence")
    public ResponseTemplate<List<AdminPageResponse.categorySequence>> reqCategorySequence(){

        return new ResponseTemplate<>(categoryService.processGetCategorySequence());
    }
    @GetMapping("/category/{id}")
    public ResponseTemplate<AdminPageResponse.categoryEdit> reqCategoryDetail(@PathVariable("id") Long id){


        return new ResponseTemplate<>(categoryService.processGetCategoryDetail(id));
    }



////    @PostMapping("/hashTag/find/item")
////    public ResponseTemplate<List<AdminPageResponse.findItemByCategory>> reqFindItemByCategory(  )
////    @PostMapping("/hashTag/display/register")
////    public ResponseTemplate<List<AdminPageResponse.categoryDetail>> reqCategoryRegister(@RequestBody AdminPageRequest.categoryRegister  categoryRegisterDto){
////        DisplayCategoryService displayCategoryService = new DisplayCategoryService(categoryRepository,hashTagRepository);
////        AdminService adminService = new AdminService(adminRepository);
////
////        //TODO
////        // categoryRegisterDto 데이터 선 처리
////
////        return new ResponseTemplate<>(displayCategoryService.processRegisterCategory(categoryRegisterDto,adminService));
////    }







}
