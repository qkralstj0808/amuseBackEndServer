package com.example.amusetravelproejct.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.config.util.UtilMethod;
import com.example.amusetravelproejct.domain.*;
import com.example.amusetravelproejct.dto.request.AdminPageRequest;
import com.example.amusetravelproejct.dto.request.AdminRequest;
import com.example.amusetravelproejct.dto.request.ProductRegisterDto;
import com.example.amusetravelproejct.dto.response.AdminPageResponse;
import com.example.amusetravelproejct.dto.response.AdminResponse;
import com.example.amusetravelproejct.repository.*;
import com.example.amusetravelproejct.repository.ItemRepository;
import com.example.amusetravelproejct.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
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
    private final MainPageComponentService mainPageComponentService;
    private final UserService userService;
    private final AmazonS3 amazonS3Client;
    ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/ad/register")
    public ResponseTemplate<AdminPageResponse.advertisementRegister> reqAdvertisementRegister(@RequestBody AdminPageRequest.advertisementRegister adminAdvertisementRegisterDto){
        UtilMethod utilMethod = new UtilMethod(amazonS3Client);

        //TODO
        // 유저 데이터 선 처리


        AdminPageResponse.advertisementRegister  advertisement = advertisementService.processAdvertisementRegister(adminAdvertisementRegisterDto,utilMethod);
        return new ResponseTemplate<>(advertisement);
    }

    @PostMapping("/ad/edit")
    public ResponseTemplate<AdminPageResponse.advertisementEdit> reqAdvertisementEdit(@RequestBody AdminPageRequest.advertisementEdit adminAdvertisementRegisterDbDto) {
        UtilMethod utilMethod = new UtilMethod(amazonS3Client);


        //TODO
        // 유저 데이터 선 처리


        AdminPageResponse.advertisementEdit advertisement = advertisementService.processAdvertisementEdit(adminAdvertisementRegisterDbDto,utilMethod);
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
        log.info(productRegisterDto.toString());

        Item item = itemService.processCreateOrUpdate(productRegisterDto);
        itemService.processItemTicket(productRegisterDto,item);
        itemService.processItemImg(productRegisterDto,utilMethod,item);
        itemService.processItemCourse(productRegisterDto,utilMethod,item);

        return new ResponseTemplate<>("상품 생성 완료");
    }

    @Transactional
    @PostMapping("/product/update")
    public ResponseTemplate<String> reqProductEdit(@RequestBody ProductRegisterDto productRegisterDto) throws ParseException {
        UtilMethod utilMethod = new UtilMethod(amazonS3Client);
        //TODO
        // productRegisterDto 데이터 선 처리

        Item item = itemService.processCreateOrUpdate(productRegisterDto);
        itemService.processItemTicket(productRegisterDto,item);
        itemService.processItemImg(productRegisterDto,utilMethod,item);
        itemService.processItemCourse(productRegisterDto,utilMethod,item);

        return new ResponseTemplate<>("상품 수정 완료");
    }

    @GetMapping("/product/{itemCode}")
    public ResponseTemplate<ProductRegisterDto> reqProductDetail(@PathVariable("itemCode") String itemCode){
        //TODO
        // productRegisterDto 데이터 선 처리

        ProductRegisterDto productRegisterDto = itemService.processGetItemDetail(itemCode);

        return new ResponseTemplate<>(productRegisterDto);
    }
    @GetMapping("/product/delete")
    public ResponseTemplate<String> reqProductDelete(@RequestParam("itemCode") String itemCode){
        //TODO
        // productRegisterDto 데이터 선 처리

        itemService.processDeleteItem(itemCode);
        return new ResponseTemplate<>("상품 삭제 완료");
    }


    @PostMapping("/product/search")
    public ResponseTemplate<AdminPageResponse.getItemByCategory> reqProductSearch(@RequestBody AdminPageRequest.getItemByCategory searchDto){
        searchDto.setPage(searchDto.getPage()-1);
        if (searchDto.getOption() == 0){
            return new ResponseTemplate<>(itemService.processSearchOrphanage(searchDto));
        } else if(searchDto.getOption() == 1){
            return new ResponseTemplate<>(itemService.processSearchItem(searchDto));
        } else {
            return new ResponseTemplate<>(itemService.processSearchItemAll(searchDto));
        }
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

    @GetMapping("/category/list")
    public ResponseTemplate<List<String>> reqCategoryList(){
        return new ResponseTemplate<>(categoryService.processGetCategoryList());
    }
    @GetMapping("/category/sequence")
    public ResponseTemplate<List<AdminPageResponse.categorySequence>> reqCategorySequence(){

        return new ResponseTemplate<>(categoryService.processGetCategorySequence());
    }
    @GetMapping("/category/{id}")
    public ResponseTemplate<AdminPageResponse.categoryEdit> reqCategoryDetail(@PathVariable("id") Long id){
        return new ResponseTemplate<>(categoryService.processGetCategoryDetail(id));
    }


    @Transactional
    @PostMapping("/mainPage/create")
    public ResponseTemplate<String> reqMainPageCreate(@RequestBody AdminPageRequest.createMainPage createMainPageDto ){
        UtilMethod utilMethod = new UtilMethod(amazonS3Client);

        //TODO
        // 유저 데이터 선 처리
        log.info(createMainPageDto.toString());

        mainPageComponentService.createMainPageComponent(createMainPageDto, utilMethod);

        return new ResponseTemplate<>("컴포넌트가 추가되었습니다.");
    }

    @GetMapping("/mainPage/list")
    public ResponseTemplate<List<AdminPageResponse.getMainPageItem>> reqMainPageList(){
        return new ResponseTemplate<>(mainPageComponentService.processGetMainPageList());
    }

    @GetMapping("/mainPage/{id}")
    public ResponseTemplate<?> reqMainPageGet(@PathVariable("id") Long id){

        //TODO
        // 유저 데이터 선 처리

//        return  null;
        return mainPageComponentService.processGetMainPageComponent(id);
    }

    @GetMapping("/mainPage/delete/{id}")
    public ResponseTemplate<?> reqMainPageDelete(@PathVariable("id") Long id){

        //TODO
        // 유저 데이터 선 처리

        mainPageComponentService.processDeleteMainPageComponent(id);
        return new ResponseTemplate<>("삭제 완료");
    }


    @PostMapping("/crate/guide")
    public ResponseTemplate<AdminResponse.GuideInfo> createGuide(@RequestBody AdminRequest.guide request){
        UtilMethod utilMethod = new UtilMethod(amazonS3Client);

        return  new ResponseTemplate<>(userService.createGuide(request,utilMethod));
    }

    @GetMapping("/read/guide/{code}")
    public ResponseTemplate<AdminResponse.GuideInfo> readGuide(@PathVariable("code") String code){

        return new ResponseTemplate<>(userService.readGuide(code));
    }

    @PostMapping("/update/guide/{code}")
    public ResponseTemplate<AdminResponse.GuideInfo> updateGuide(@PathVariable("code") String code,@RequestBody AdminRequest.guide request){
        UtilMethod utilMethod = new UtilMethod(amazonS3Client);

        return  new ResponseTemplate<>(userService.updateGuide(request,code,utilMethod));
    }

    @GetMapping("/delete/guide/{code}")
    public ResponseTemplate<String> deleteGuide(@PathVariable("code") String code){

        userService.deleteGuide(code);
        return new ResponseTemplate<>("가이드 삭제가 완료 되었습니다.");
    }


    @GetMapping("/list/guide")
    public ResponseTemplate<List<AdminResponse.GuideInfo>> listGuide(@RequestParam("page") Long page, @RequestParam("limit") Long limit){



        return new ResponseTemplate<>(userService.listGuide(page,limit));
    }


    @GetMapping("/change/displayStatus")
    public ResponseTemplate<String> changeDisplayStatus(@RequestParam("status") String displayStatus, @RequestParam("itemCode") String itemCode){
        itemService.changeItemStatus(displayStatus,itemCode);

        return new ResponseTemplate<>("상품 상태가 변경되었습니다.");
    }
}
