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
    private final ItemRepository itemRepository;
    private final ImgRepository imgRepository;
    private final ItemTicketRepository itemTicketRepository;
    private final ItemCourseRepository itemCourseRepository;
    private final CategoryRepository categoryRepository;
    private final AdminRepository adminRepository;
    private final AdvertisementRepository adminAdvertisementRepository;
    private final AlarmRepository alarmRepository;
    private final ItemTicketPriceRepository itemTicketPriceRepository;
    private final DisplayCategoryRepository displayCategoryRepository;
    private final AmazonS3 amazonS3Client;
    ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/ad/register")
    public ResponseTemplate<AdminPageResponse.advertisementRegister> reqAdvertisementRegister(@RequestBody AdminPageRequest.advertisementRegister adminAdvertisementRegisterDto){

        AdminAdvertisementService adminAdvertisementService = new AdminAdvertisementService(adminAdvertisementRepository);
        CategoryService categoryService = new CategoryService(categoryRepository,adminRepository);
        AdminService adminService = new AdminService(adminRepository);
        UtilMethod utilMethod = new UtilMethod(amazonS3Client);

        //TODO
        // 유저 데이터 선 처리


        AdminPageResponse.advertisementRegister  advertisement = adminAdvertisementService.processAdvertisementRegister(adminAdvertisementRegisterDto,categoryService,adminService,utilMethod);
        return new ResponseTemplate<>(advertisement);
    }

    @PostMapping("/ad/edit")
    public ResponseTemplate<AdminPageResponse.advertisementEdit> reqAdvertisementEdit(@RequestBody AdminPageRequest.advertisementEdit adminAdvertisementRegisterDbDto) {

        AdminAdvertisementService adminAdvertisementService = new AdminAdvertisementService(adminAdvertisementRepository);
        CategoryService categoryService = new CategoryService(categoryRepository,adminRepository);
        AdminService adminService = new AdminService(adminRepository);
        UtilMethod utilMethod = new UtilMethod(amazonS3Client);


        //TODO
        // 유저 데이터 선 처리


        AdminPageResponse.advertisementEdit advertisement = adminAdvertisementService.processAdvertisementEdit(adminAdvertisementRegisterDbDto,categoryService,adminService,utilMethod);

        return new ResponseTemplate<>(advertisement);
    }

    @GetMapping("/ad/getList")
    public ResponseTemplate<AdminPageResponse.advertisementResult> reqAdvertisementList(@RequestParam("offset") Long offset , @RequestParam("limit") int limit, @RequestParam("page") int page){
        AdminAdvertisementService adminAdvertisementService = new AdminAdvertisementService(adminAdvertisementRepository);

        //TODO
        // 유저 데이터 선 처리
        int sqlPage = page -1;

        AdminPageResponse.advertisementResult advertisementResult = new AdminPageResponse.advertisementResult(adminAdvertisementService.getPageCount(limit),page,adminAdvertisementService.processGetAllAdvertisements(offset,limit,sqlPage));

        return new ResponseTemplate<>(advertisementResult);
    }

    @GetMapping("/ad/{id}")
    public ResponseTemplate<AdminPageResponse.advertisementEdit> reqAdvertisementDetail(@PathVariable("id") Long id){
        AdminAdvertisementService adminAdvertisementService = new AdminAdvertisementService(adminAdvertisementRepository);

        //TODO
        // 유저 데이터 선 처리


        AdminPageResponse.advertisementEdit advertisement = adminAdvertisementService.processGetAdvertisementDetail(id);
        return new ResponseTemplate<>(advertisement);
    }

    @Transactional
    @PostMapping("/product/create")
    public ResponseTemplate<String> reqProductCreate(@RequestBody ProductRegisterDto productRegisterDto) throws ParseException {
        ItemService itemService = new ItemService(itemRepository,adminRepository,categoryRepository,imgRepository,itemTicketRepository,itemTicketPriceRepository,itemCourseRepository,objectMapper);
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
        AdminService adminService = new AdminService(adminRepository);
        AlarmService alarmService = new AlarmService(alarmRepository);

        //TODO
        // 유저 데이터 선 처리

        return new ResponseTemplate<>(alarmService.processRegisterNotice(noticeRegisterDto,adminService,alarmService));
    }

    @PostMapping("/notice/edit")
    public ResponseTemplate<AdminPageResponse.noticeEdit> noteiceEdit(@RequestBody AdminPageRequest.noticeEdit noticeEditDto){
        AdminService adminService = new AdminService(adminRepository);
        AlarmService alarmService = new AlarmService(alarmRepository);


        //TODO
        // 유저 데이터 선 처리

        return new ResponseTemplate<>(alarmService.processEditNotice(noticeEditDto,adminService));
    }



    @GetMapping("/notice/getList")
    public ResponseTemplate<AdminPageResponse.noticeResult> reqNoticeList(@RequestParam("offset") Long offset , @RequestParam("limit") int limit, @RequestParam("page") int page){
        AlarmService alarmService = new AlarmService(alarmRepository);

        //TODO
        // 유저 데이터 선 처리
        int sqlPage = page -1;

        AdminPageResponse.noticeResult noticeResult = new AdminPageResponse.noticeResult(alarmService.getPageCount(limit),page,alarmService.processGetAllNotices(offset,limit,sqlPage));

        return new ResponseTemplate<>(noticeResult);
    }
    @GetMapping("/notice/{id}")
    public ResponseTemplate<AdminPageResponse.noticeEdit> reqNoticeDetail(@PathVariable("id") Long id){
        AlarmService alarmService = new AlarmService(alarmRepository);

        //TODO
        // 유저 데이터 선 처리


        return new ResponseTemplate<>(alarmService.processGetNoticeDetail(id));
    }

    @PostMapping("/hashTag/display/register")
    public ResponseTemplate<List<AdminPageResponse.categoryDetail>> reqCategoryRegister(@RequestBody AdminPageRequest.categoryRegister  categoryRegisterDto){
        DisplayCategoryService displayCategoryService = new DisplayCategoryService(displayCategoryRepository);
        AdminService adminService = new AdminService(adminRepository);

        //TODO
        // categoryRegisterDto 데이터 선 처리

        return new ResponseTemplate<>(displayCategoryService.processRegisterCategory(categoryRegisterDto,adminService));
    }





}
