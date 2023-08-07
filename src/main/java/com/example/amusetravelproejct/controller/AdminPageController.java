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
import com.example.amusetravelproejct.oauth.entity.UserPrincipal;
import com.example.amusetravelproejct.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final PageComponentService pageComponentService;
    private final UserService userService;
    private final PageService pageService;
    private final AmazonS3 amazonS3Client;
    ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/ad/register")
    public ResponseTemplate<AdminPageResponse.advertisementRegister> reqAdvertisementRegister(@RequestBody AdminPageRequest.advertisementRegister adminAdvertisementRegisterDto,@AuthenticationPrincipal UserPrincipal userPrincipal){
        UtilMethod utilMethod = new UtilMethod(amazonS3Client);

        Admin findAdmin = adminService.getAdminPrincipal(userPrincipal);
        adminAdvertisementRegisterDto.setCreatedBy(findAdmin.getAdminId());
        //TODO
        // 유저 데이터 선 처리


        AdminPageResponse.advertisementRegister  advertisement = advertisementService.processAdvertisementRegister(adminAdvertisementRegisterDto,utilMethod);
        return new ResponseTemplate<>(advertisement);
    }

    @PostMapping("/ad/edit")
    public ResponseTemplate<AdminPageResponse.advertisementEdit> reqAdvertisementEdit(@RequestBody AdminPageRequest.advertisementEdit adminAdvertisementRegisterDbDto,@AuthenticationPrincipal UserPrincipal userPrincipal) {
        UtilMethod utilMethod = new UtilMethod(amazonS3Client);
        Admin findAdmin = adminService.getAdminPrincipal(userPrincipal);

        adminAdvertisementRegisterDbDto.setUpdatedBy(findAdmin.getAdminId());
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
    @PostMapping("/product/insert")
    public ResponseTemplate<String> reqProductCreate(@RequestBody ProductRegisterDto productRegisterDto,
                                                     @AuthenticationPrincipal UserPrincipal userPrincipal) throws ParseException {
        UtilMethod utilMethod = new UtilMethod(amazonS3Client);
        Admin findAdmin = adminService.getAdminPrincipal(userPrincipal);
        //TODO
        // productRegisterDto 데이터 선 처리

        if(productRegisterDto.getOption().equals("create")){
            productRegisterDto.setAdmin(findAdmin.getAdminId());
        }
        productRegisterDto.setUpdateAdmin(findAdmin.getAdminId());

        log.info(productRegisterDto.toString());
        Item item = productRegisterDto.getOption().equals("create") ? itemService.processCreate(productRegisterDto) : itemService.processUpdate(productRegisterDto);

        itemService.processItemTicket(productRegisterDto,item);
        itemService.processItemImg(productRegisterDto,utilMethod,item);

        if(productRegisterDto.getCourse() != null && productRegisterDto.getCourse().size() != 0){
            itemService.processItemCourse(productRegisterDto,utilMethod,item);
        }

        if (productRegisterDto.getOption().equals("create")){
            return new ResponseTemplate<>("상품 생성 완료");
        } else{
            return new ResponseTemplate<>("상품 수정 완료");
        }

    }
    @GetMapping("/product/{item_db_id}")
    public ResponseTemplate<ProductRegisterDto> reqProductDetail(@PathVariable("item_db_id") Long item_db_id){
        //TODO
        // productRegisterDto 데이터 선 처리

        return new ResponseTemplate<>(itemService.processGetItemDetail(item_db_id));
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
        if (searchDto.getOption() == "orphanage"){
            return new ResponseTemplate<>(itemService.processSearchOrphanage(searchDto));
        } else if(searchDto.getOption() == "Category"){
            return new ResponseTemplate<>(itemService.processSearchItem(searchDto));
        } else {
            return new ResponseTemplate<>(itemService.processSearchItemAll(searchDto));
        }
    }

    @GetMapping("/product/getList/byDisplay")
    public ResponseTemplate<AdminPageResponse.getItemByDisplayStatus> reqDisplayProductList(@RequestParam("limit") int limit, @RequestParam("page") int page, @RequestParam("displayStatus") String displayStatus){
        //TODO
        // 유저 데이터 선 처리

        int sqlPage = page -1;

        return new ResponseTemplate<>(itemService.processGetAllDisplayItems(limit,sqlPage,displayStatus));
    }
    @PostMapping("/change/item/{itemCode}/displayStatus")
    public ResponseTemplate<String> changeDisplayStatus(@RequestBody AdminPageRequest.changeDisplayStatus request ,@PathVariable("itemCode") String itemCode){
        itemService.changeItemStatus(request,itemCode);

        return new ResponseTemplate<>("상품 상태가 변경되었습니다.");
    }

    @PostMapping("/notice/register")
    public ResponseTemplate<AdminPageResponse.noticeRegister> noticeRegister(@RequestBody AdminPageRequest.noticeRegister noticeRegisterDto,@AuthenticationPrincipal UserPrincipal userPrincipal){
        Admin findAdmin = adminService.getAdminPrincipal(userPrincipal);
        noticeRegisterDto.setCreatedBy(findAdmin.getAdminId());
        //TODO
        // 유저 데이터 선 처리
        return new ResponseTemplate<>(alarmService.processRegisterNotice(noticeRegisterDto,adminService,alarmService));
    }

    @PostMapping("/notice/edit")
    public ResponseTemplate<AdminPageResponse.noticeEdit> noteiceEdit(@RequestBody AdminPageRequest.noticeEdit noticeEditDto,@AuthenticationPrincipal UserPrincipal userPrincipal){
        Admin findAdmin = adminService.getAdminPrincipal(userPrincipal);
        noticeEditDto.setUpdatedBy(findAdmin.getAdminId());
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
    public ResponseTemplate<AdminPageResponse.categoryRegister> reqCategoryRegister(@RequestBody AdminPageRequest.categoryRegister  categoryRegisterDto,@AuthenticationPrincipal UserPrincipal userPrincipal){
        UtilMethod utilMethod = new UtilMethod(amazonS3Client);
        Admin findAdmin = adminService.getAdminPrincipal(userPrincipal);
        categoryRegisterDto.setCreatedBy(findAdmin.getAdminId());
        //TODO
        // categoryRegisterDto 데이터 선 처리


        return new ResponseTemplate<>(categoryService.processRegisterCategory(categoryRegisterDto,adminService,utilMethod));
    }

    @PostMapping("/category/edit")
    public ResponseTemplate<AdminPageResponse.categoryEdit> reqCategoryEdit(@RequestBody AdminPageRequest.categoryEdit categoryEditDto,@AuthenticationPrincipal UserPrincipal userPrincipal){
        UtilMethod utilMethod = new UtilMethod(amazonS3Client);
        Admin findAdmin = adminService.getAdminPrincipal(userPrincipal);
        categoryEditDto.setUpdatedBy(findAdmin.getAdminId());
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
    @PostMapping("/component/register/list")
    public ResponseTemplate<?> reqComponentRegisterList(@RequestBody AdminPageRequest.registerListComponent registerListComponentDto,@AuthenticationPrincipal UserPrincipal userPrincipal){
        UtilMethod utilMethod =  new UtilMethod(amazonS3Client);

        Admin findAdmin = adminService.getAdminPrincipal(userPrincipal);
        //TODO
        // 유저 데이터 선 처리
        log.info(registerListComponentDto.toString());
        return new ResponseTemplate<>(pageComponentService.registerListComponent(registerListComponentDto,findAdmin));

    }
    @Transactional
    @PostMapping("/component/edit/list")
    public ResponseTemplate<?> reqComponentEditList(@RequestBody AdminPageRequest.registerListComponent registerListComponentDto,@AuthenticationPrincipal UserPrincipal userPrincipal){
        UtilMethod utilMethod =  new UtilMethod(amazonS3Client);
        Admin findAdmin = adminService.getAdminPrincipal(userPrincipal);
        //TODO
        // 유저 데이터 선 처리
        log.info(registerListComponentDto.toString());
        return new ResponseTemplate<>(pageComponentService.registerListComponent(registerListComponentDto,findAdmin));

    }

    @Transactional
    @PostMapping("/component/register/banner")
    public ResponseTemplate<?> reqComponentRegisterBanner(@RequestBody AdminPageRequest.registerBannerComponent registerBannerComponentDto,@AuthenticationPrincipal UserPrincipal userPrincipal){
        UtilMethod utilMethod = new UtilMethod(amazonS3Client);
        Admin findAdmin = adminService.getAdminPrincipal(userPrincipal);
        //TODO
        // 유저 데이터 선 처리
        log.info(registerBannerComponentDto.toString());

        return new ResponseTemplate<>(pageComponentService.registerBannerComponent(registerBannerComponentDto,utilMethod,findAdmin));
    }

    @Transactional
    @PostMapping("/component/edit/banner")
    public ResponseTemplate<?> reqComponentEditBanner(@RequestBody AdminPageRequest.registerBannerComponent registerBannerComponentDto,@AuthenticationPrincipal UserPrincipal userPrincipal){
        UtilMethod utilMethod = new UtilMethod(amazonS3Client);
        Admin findAdmin = adminService.getAdminPrincipal(userPrincipal);
        //TODO
        // 유저 데이터 선 처리
        log.info(registerBannerComponentDto.toString());

        return new ResponseTemplate<>(pageComponentService.registerBannerComponent(registerBannerComponentDto,utilMethod,findAdmin));
    }

    @Transactional
    @PostMapping("/component/register/tile")
    public ResponseTemplate<?> reqComponentRegisterTile(@RequestBody AdminPageRequest.registerTileComponent registerTileComponentDto,@AuthenticationPrincipal UserPrincipal userPrincipal){
        UtilMethod utilMethod = new UtilMethod(amazonS3Client);
        Admin findAdmin = adminService.getAdminPrincipal(userPrincipal);

        //TODO
        // 유저 데이터 선 처리
        log.info(registerTileComponentDto.toString());

        return new ResponseTemplate<>(pageComponentService.registerTileComponent(registerTileComponentDto,utilMethod,findAdmin));
    }

    @Transactional
    @PostMapping("/component/edit/tile")
    public ResponseTemplate<?> reqComponentEditTile(@RequestBody AdminPageRequest.registerTileComponent registerTileComponentDto,@AuthenticationPrincipal UserPrincipal userPrincipal){
        UtilMethod utilMethod = new UtilMethod(amazonS3Client);
        Admin findAdmin = adminService.getAdminPrincipal(userPrincipal);

        //TODO
        // 유저 데이터 선 처리
        log.info(registerTileComponentDto.toString());

        return new ResponseTemplate<>(pageComponentService.registerTileComponent(registerTileComponentDto,utilMethod,findAdmin));
    }


    @GetMapping("/component")
    public ResponseTemplate<?> reqComponentList(){
        return new ResponseTemplate<>(pageComponentService.getComponentList());
    }

    @GetMapping("/component/{id}")
    public ResponseTemplate<?> reqComponentDetail(@PathVariable("id") Long id){
        return new ResponseTemplate<>(pageComponentService.getComponentDetail(id));
    }

    @GetMapping("/component/delete/{id}")
    public ResponseTemplate<?> reqMainPageDelete(@PathVariable("id") Long id){

        //TODO
        // 유저 데이터 선 처리

        pageComponentService.processDeleteMainPageComponent(id);
        return new ResponseTemplate<>("삭제 완료");
    }

    @PostMapping("/create/guide")
    public ResponseTemplate<AdminResponse.GuideInfo> createGuide(@RequestBody AdminRequest.guide request){
        UtilMethod utilMethod = new UtilMethod(amazonS3Client);

        return  new ResponseTemplate<>(userService.createGuide(request,utilMethod));
    }

    @GetMapping("/read/guide/{guide_db_id}")
    public ResponseTemplate<AdminResponse.GuideInfo> readGuide(@PathVariable("guide_db_id") Long guide_db_id){

        return new ResponseTemplate<>(userService.readGuide(guide_db_id));
    }

    @PostMapping("/update/guide/{guide_db_id}")
    public ResponseTemplate<AdminResponse.GuideInfo> updateGuide(@PathVariable("guide_db_id") Long guide_db_id,@RequestBody AdminRequest.guide request){
        UtilMethod utilMethod = new UtilMethod(amazonS3Client);

        return  new ResponseTemplate<>(userService.updateGuide(request,guide_db_id,utilMethod));
    }

    @GetMapping("/delete/guide/{guide_db_id}")
    public ResponseTemplate<String> deleteGuide(@PathVariable("guide_db_id") Long guide_db_id){

        userService.deleteGuide(guide_db_id);
        return new ResponseTemplate<>("가이드 삭제가 완료 되었습니다.");
    }

    @GetMapping("/list/guide")
    public ResponseTemplate<AdminResponse.ListGuide> listGuide(@RequestParam("page") Long page, @RequestParam("limit") Long limit){

        return new ResponseTemplate<>(userService.listGuide(page,limit));
    }

    @GetMapping("/icon/list")
    public ResponseTemplate<?> listIcon(){
        return new ResponseTemplate<>(itemService.getIconList());
    }

    @PostMapping("page/register")
    public ResponseTemplate<String> createPage(
            @RequestBody AdminPageRequest.createPage request,
            @AuthenticationPrincipal UserPrincipal userPrincipal){
        UtilMethod utilMethod = new UtilMethod(amazonS3Client);
        Admin findAdmin = adminService.getAdminPrincipal(userPrincipal);
//        User findUser = userService.getUserByPrincipal(userPrincipal);

        return pageService.createPage(request,utilMethod,findAdmin);
    }

    @PutMapping("page/edit/{page-id}")
    public ResponseTemplate<AdminPageResponse.updatePage> updatePage(
            @PathVariable("page-id") Long page_id,
            @RequestBody AdminPageRequest.updatePage request,
            @AuthenticationPrincipal UserPrincipal userPrincipal){
        UtilMethod utilMethod = new UtilMethod(amazonS3Client);

        Admin findAdmin = adminService.getAdminPrincipal(userPrincipal);

        return pageService.updatePage(page_id,request,utilMethod,findAdmin);
    }

    @GetMapping("/page/{page-id}")
    public ResponseTemplate<AdminPageResponse.getPage> getPage(
            @PathVariable("page-id") Long page_id){

        return pageService.getPage(page_id);
    }

    @GetMapping("/page/all")
    public ResponseTemplate<AdminPageResponse.getAllPage> getAllPage(
            @RequestParam(value = "disable",required = false) Boolean disable){
        return pageService.getAllPage(disable);
    }

    @DeleteMapping("/delete/page/{page-id}")
    public ResponseTemplate<String> deletePage(
            @PathVariable("page-id") Long page_id){
        return pageService.deletePage(page_id);
    }




}
