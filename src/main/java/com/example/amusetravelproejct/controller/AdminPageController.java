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
    private final ItemIconRepository itemIconRepository;
    private final PaymentTicketRepository paymentTicketRepository;
    private final CategoryRepository categoryRepository;
    private final AdminRepository adminRepository;
    private final AdvertisementRepository adminAdvertisementRepository;
    private final ItemCourseRepository courseRepository;
    private final AlarmRepository alarmRepository;
    private final ItemTicketPriceRepository itemTicketPriceRepository;
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

    @PostMapping("/category/register")
    public ResponseTemplate<AdminPageResponse.categoryRegister> reqCategoryRegister(@RequestBody AdminPageRequest.categoryRegister  categoryRegisterDto){
        CategoryService categoryService = new CategoryService(categoryRepository,adminRepository);

        //TODO
        // categoryRegisterDto 데이터 선 처리
//        Category category = categoryService.processCategoryRegister(categoryRegisterDto);
//        return new ResponseTemplate<>(new AdminPageResponse.categoryRegister(category.getId(), category.getCategoryName(), category.getCreatedAdDate() , category.getAdmin().getEmail()));
        return null;
    }

    @GetMapping("/category/detail")
    public ResponseTemplate<AdminPageResponse.categoryDetailList> categoryDetail(@RequestParam("id") Long id, @RequestParam("offset") Long offset , @RequestParam("limit") Long limit){
        CategoryService categoryService = new CategoryService(categoryRepository,adminRepository);

        //TODO
        // categoryDetailDto 데이터 선 처리


//        Category category = categoryService.findCategoryById(id);
//        List<AdminPageResponse.categoryDetail> categoryDetails = categoryService.processCategoryItemList(category, offset, limit);
//
//        return new ResponseTemplate<>(new AdminPageResponse.categoryDetailList(category.getCategoryName(),categoryDetails));
        return null;
    }

    @GetMapping("/category/getAll")
    public ResponseTemplate<List<AdminPageResponse.category>> category(){
//        CategoryService categoryService = new CategoryService(categoryRepository,adminRepository);
//        List<AdminPageResponse.category> categoryList = categoryService.processFindAllCategory();
//        return new ResponseTemplate<>(categoryList);
         return null;
    }





    @PostMapping("/notice/register")
    public ResponseTemplate<AdminPageResponse.noticeRegister> noticeRegister(@RequestBody AdminPageRequest.noticeRegister noticeRegisterDto){
        AdminService adminService = new AdminService(adminRepository);
        AlarmService alarmService = new AlarmService(alarmRepository);

        Alarm alarm = new Alarm();

        alarm.setTitle(noticeRegisterDto.getTitle());
        alarm.setContent(noticeRegisterDto.getContent());
        alarm.setAdmin(adminService.getAdminByEmail(noticeRegisterDto.getCreatedBy()).get());
        alarm.setContent(noticeRegisterDto.getContent());

        alarm = alarmService.saveAlarm(alarm).get();

        return new ResponseTemplate<>(new AdminPageResponse.noticeRegister(alarm.getId(),alarm.getTitle(),alarm.getContent(),alarm.getCreatedAdDate(),alarm.getAdmin().getEmail()));
    }

    @PostMapping("/notice/edit")
    public ResponseTemplate<AdminPageResponse.noticeEdit> noteiceEdit(@RequestBody AdminPageRequest.noticeEdit noticeEditDto){
        AdminService adminService = new AdminService(adminRepository);
        AlarmService alarmService = new AlarmService(alarmRepository);

        log.info(noticeEditDto.toString());

        Alarm alarm = alarmService.findAlarmById(noticeEditDto.getId()).get();

        alarm.setTitle(noticeEditDto.getTitle());
        alarm.setContent(noticeEditDto.getContent());
        alarm.setUpdateAdmin(adminService.getAdminByEmail(noticeEditDto.getUpdatedBy()).get());
        alarm.setContent(noticeEditDto.getContent());

        alarm = alarmService.saveAlarm(alarm).get();

        return new ResponseTemplate<>(new AdminPageResponse.noticeEdit(alarm.getId(),alarm.getTitle(),alarm.getContent(),alarm.getCreatedAdDate(),alarm.getAdmin().getEmail(),alarm.getModifiedDate(),alarm.getUpdateAdmin().getEmail()));
    }

}
