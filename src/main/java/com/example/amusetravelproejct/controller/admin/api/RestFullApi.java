package com.example.amusetravelproejct.controller.admin.api;

import com.example.amusetravelproejct.controller.admin.dto.AdminAdvertisementRegisterDto;
import com.example.amusetravelproejct.controller.admin.dto.AdminAdvertisementRegisterDbDto;
import com.example.amusetravelproejct.controller.admin.dto.ItemInfoDto;
import com.example.amusetravelproejct.controller.admin.service.AdminAdvertisementService;
import com.example.amusetravelproejct.controller.admin.service.AdminService;
import com.example.amusetravelproejct.controller.admin.service.CategoryService;
import com.example.amusetravelproejct.domain.AdminAdvertisement;
import com.example.amusetravelproejct.domain.Category;
import com.example.amusetravelproejct.domain.person_enum.Adver;
import com.example.amusetravelproejct.domain.person_enum.DateOption;
import com.example.amusetravelproejct.domain.person_enum.ImgRole;
import com.example.amusetravelproejct.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@RestController
@RequestMapping("/test/api")
@Slf4j
public class RestFullApi {

    private final CategoryRepository categoryRepository;
    private final AdminRepository adminRepository;
    private final AdminAdvertisementRepository adminAdvertisementRepository;

    @PostMapping("/ad/register")
    public AdminAdvertisementRegisterDbDto reqAdvertisementRegister(@RequestBody AdminAdvertisementRegisterDto adminAdvertisementRegisterDto){

        AdminAdvertisementService adminAdvertisementService = new AdminAdvertisementService(adminAdvertisementRepository);
        CategoryService categoryService = new CategoryService(categoryRepository);
        AdminService adminService = new AdminService(adminRepository);


        //TODO
        // 유저 데이터 선 처리

        AdminAdvertisement advertisement = new AdminAdvertisement();
        advertisement.setAdvertisementTitle(adminAdvertisementRegisterDto.getTitle());
        advertisement.setAdvertisementContent(adminAdvertisementRegisterDto.getAdContent());
        advertisement.setAdvertisementStartDate(adminAdvertisementRegisterDto.getStartDate());
        advertisement.setAdvertisementEndDate(adminAdvertisementRegisterDto.getEndDate());
        advertisement.setAdmin(adminService.getAdminByEmail(adminAdvertisementRegisterDto.getCreatedAd()).get());

        switch (adminAdvertisementRegisterDto.getAdType()){
            case "ad1":
                advertisement.setAdvertisementType(Adver.ad1);
                break;
            case "ab2":
                advertisement.setAdvertisementType(Adver.ad2);
                break;
            case "ab3":
                advertisement.setAdvertisementType(Adver.ad3);
                break;
            default:
                break;
        }

        switch (adminAdvertisementRegisterDto.getAdCategory()){
            case "컨시어지":
                advertisement.setCategory(categoryService.getCategoryById(1L).get());
                break;
            case "아이돌봄":
                advertisement.setCategory(categoryService.getCategoryById(2L).get());
                break;
            case "어르신돌봄":
                advertisement.setCategory(categoryService.getCategoryById(3L).get());
                break;
            default:
                break;
        }

        advertisement = adminAdvertisementService.createAdvertisement(advertisement);
        return advertiseToDTO(advertisement, DateOption.CREATE);
    }

    @PostMapping("/ad/edit")
    public AdminAdvertisementRegisterDbDto reqAdvertisementEdit(@RequestBody AdminAdvertisementRegisterDbDto adminAdvertisementRegisterDbDto) {

        AdminAdvertisementService adminAdvertisementService = new AdminAdvertisementService(adminAdvertisementRepository);
        CategoryService categoryService = new CategoryService(categoryRepository);
        AdminService adminService = new AdminService(adminRepository);

        Optional<AdminAdvertisement> optionalAdvertisement = adminAdvertisementService.getAdvertisementById(adminAdvertisementRegisterDbDto.getId());
        AdminAdvertisement advertisement = optionalAdvertisement.get();

        advertisement.setAdvertisementTitle(adminAdvertisementRegisterDbDto.getTitle());
        advertisement.setAdvertisementContent(adminAdvertisementRegisterDbDto.getAdContent());
        advertisement.setAdvertisementStartDate(adminAdvertisementRegisterDbDto.getStartDate());
        advertisement.setAdvertisementEndDate(adminAdvertisementRegisterDbDto.getEndDate());
        advertisement.setAdmin(adminService.getAdminByEmail(adminAdvertisementRegisterDbDto.getCreatedAd()).get());

        switch (adminAdvertisementRegisterDbDto.getAdType()){
            case "ad1":
                advertisement.setAdvertisementType(Adver.ad1);
                break;
            case "ab2":
                advertisement.setAdvertisementType(Adver.ad2);
                break;
            case "ab3":
                advertisement.setAdvertisementType(Adver.ad3);
                break;
            default:
                break;
        }

        switch (adminAdvertisementRegisterDbDto.getAdCategory()){
            case "컨시지어":
                advertisement.setCategory(categoryService.getCategoryById(1L).get());
                break;
            case "아이돌봄":
                advertisement.setCategory(categoryService.getCategoryById(1L).get());
                break;
            case "어르신돌봄":
                advertisement.setCategory(categoryService.getCategoryById(1L).get());
                break;
            default:
                break;
        }


        advertisement = adminAdvertisementService.updateAdvertisement(advertisement);
        return advertiseToDTO(advertisement,DateOption.UPDATE);
    }

    @GetMapping("/ad/getList")
    public List<AdminAdvertisementRegisterDbDto> reqAdvertisementList(){
        AdminAdvertisementService adminAdvertisementService = new AdminAdvertisementService(adminAdvertisementRepository);
        List<AdminAdvertisement> advertisementList = adminAdvertisementService.getAllAdvertisements();
        List<AdminAdvertisementRegisterDbDto> advertisementRegisterDbDtoList = new ArrayList<>();

        for (int i = 0; i < advertisementList.size(); i++) {
            advertisementRegisterDbDtoList.add(advertiseToDTO(advertisementList.get(i),DateOption.UPDATE));
        }

        return advertisementRegisterDbDtoList;
    }
//    @GetMapping("/getItem")
//    public ItemInfoDto getItem(@Param("id") String id){
//
//        //get item info from DB
//        Iteminfo iteminfo = iteminfoRepository.findByItemCode(id);
//
//        //response
//        ItemInfoDto itemInfoDto = new ItemInfoDto();
//        itemInfoDto.setItemCode(iteminfo.getItemCode());
//        itemInfoDto.setCategory(iteminfo.getCategory().getId());
//        itemInfoDto.setItemTitle(iteminfo.getItemTitle());
//
//
//        for(Img img : iteminfo.getImgs()){
//            if (img.getImgRole().equals(ImgRole.THUMBNAIL)){
//                itemInfoDto.setThumbnailImgUrl(img.getImgUrl());
//            }
//        }
//
//        itemInfoDto.setLocation(iteminfo.getCityLocation());
//        itemInfoDto.setStartingPrice(iteminfo.getStartingPrice());
//
//        return itemInfoDto;
//    }
//
//    @PostMapping("/createItem")
//    public ResponseEntity<String> createItem(@RequestBody ItemInfoDto itemInfoDto){
//        Iteminfo iteminfo = new Iteminfo();
//        Img img = new Img();
//
//        iteminfo.setCategory(categoryRepository.findById(itemInfoDto.getCategory()).get());
//
//        iteminfo.setCityLocation(itemInfoDto.getLocation());
//
//        if (iteminfo.getCityLocation().equals("강원도")){
//            iteminfo.setItemCode("033-1");
//        }
//
//        iteminfo.setItemTitle(itemInfoDto.getItemTitle());
//        iteminfo.setStartingPrice(itemInfoDto.getStartingPrice());
//        iteminfoRepository.save(iteminfo);
//
//
//        // 1. Img 파일(base64, multipart)로 정보를 받는다
//        // 2. Img 정보를 S3에 보내고 S3에서 받은 url을 DB에 저장한다.
//
//        img.setImgUrl("https://s3.ap-northeast-2.amazonaws.com/amuse-travel-project/IMG_20200831_162000.jpg");
//        img.setImgRole(ImgRole.THUMBNAIL);
//        img.setIteminfo(iteminfo);
//
//        imgRepository.save(img);
//
//
//
//        return ResponseEntity.ok("Item created successfully!");
//    }
//
//    @PostMapping("/updateItem")
//
//    public ResponseEntity<String> updateItem(@RequestBody ItemInfoDto itemInfoDto){
//        Iteminfo iteminfo = iteminfoRepository.findByItemCode(itemInfoDto.getItemCode());
//        List<Img> imgs = iteminfo.getImgs();
//
//        iteminfo.setCategory(categoryRepository.findById(itemInfoDto.getCategory()).get());
//        iteminfo.setCityLocation(itemInfoDto.getLocation());
//        iteminfo.setItemTitle(itemInfoDto.getItemTitle());
//        iteminfo.setStartingPrice(itemInfoDto.getStartingPrice());
//        iteminfoRepository.save(iteminfo);
//
//        for (Img img : imgs){
//           if (!img.getImgUrl().equals(itemInfoDto.getThumbnailImgUrl())){
//               imgRepository.delete(img);
//
//               // S3 Img 삭제 필요
//
//
//               // 1. Img 파일(base64, multipart)로 정보를 받는다
//               // 2. Img 정보를 S3에 보내고 S3에서 받은 url을 DB에 저장한다.
//
//               img.setImgUrl("https://s3.ap-northeast-2.amazonaws.com/amuse-travel-project/JPG_123213_12322.jpg");
//               img.setImgRole(ImgRole.THUMBNAIL);
//               img.setIteminfo(iteminfo);
//
//               imgRepository.save(img);
//           }
//        }
//
//
//
//        return ResponseEntity.ok("Item updated successfully!");
//    }
//
//    @GetMapping("/deleteItem")
//    public ResponseEntity<String> deleteItem(@Param("itemCode") String itemCode){
//        Iteminfo iteminfo = iteminfoRepository.findByItemCode(itemCode);
//        System.out.println(iteminfo.toString());
//        iteminfoRepository.delete(iteminfo);
//        return ResponseEntity.ok("Item deleted successfully!");
//    }
//
//
//    @GetMapping("/readCategory")
//    public ResponseEntity<String> readCategory(){
//
//        List<Category> category = categoryRepository.findAll();
//        String result = "";
//        for (Category temp : category){
//            result += temp.getCategoryName() + " ";
//        }
//        return ResponseEntity.ok(result);
//    }
//
//
//    @GetMapping("/createCategory")
//    public ResponseEntity<String> createCategory(@Param("categoryName") String categoryName){
//
//        Category category1 = new Category();
//        category1.setCategoryName(categoryName);
//        categoryRepository.save(category1);
//        return ResponseEntity.ok("create category success!!");
//    }
//
//    @GetMapping("/updateCategory")
//    public ResponseEntity<String> updateCategory(@Param("id") Long id, @Param("categoryName") String categoryName){
//
//        Category category1 = categoryRepository.findById(id).get();
//        category1.setCategoryName(categoryName);
//        categoryRepository.save(category1);
//        return ResponseEntity.ok("update category success!!");
//    }
//
//    @GetMapping("/deleteCategory")
//    public ResponseEntity<String> deleteCategory(@Param("id") Long id){
//
//        Category category1 = categoryRepository.findById(id).get();
//        categoryRepository.delete(category1);
//        return ResponseEntity.ok("delete category success!!");
//    }
//
//
    public AdminAdvertisementRegisterDbDto advertiseToDTO(AdminAdvertisement advertisement, DateOption dateOption){
        AdminAdvertisementRegisterDbDto adminAdvertisementRegisterDbDto = new AdminAdvertisementRegisterDbDto();

        adminAdvertisementRegisterDbDto.setTitle(advertisement.getAdvertisementTitle());
        adminAdvertisementRegisterDbDto.setId(advertisement.getId());
        adminAdvertisementRegisterDbDto.setAdContent(advertisement.getAdvertisementContent());
        adminAdvertisementRegisterDbDto.setAdCategory(advertisement.getCategory().getCategoryName());
        adminAdvertisementRegisterDbDto.setCreatedAd(advertisement.getAdmin().getEmail());
        adminAdvertisementRegisterDbDto.setStartDate(advertisement.getAdvertisementStartDate());
        adminAdvertisementRegisterDbDto.setEndDate(advertisement.getAdvertisementEndDate());
        if (dateOption.equals(DateOption.CREATE)){
            adminAdvertisementRegisterDbDto.setAdDate(advertisement.getCreatedAdDate());
        } else {
            adminAdvertisementRegisterDbDto.setAdDate(advertisement.getModifiedDate());
        }
        switch (advertisement.getAdvertisementType().ordinal()){
            case 0:
                adminAdvertisementRegisterDbDto.setAdType("ad1");
                break;
            case 1:
                adminAdvertisementRegisterDbDto.setAdType("ad2");
                break;
            case 2:
                adminAdvertisementRegisterDbDto.setAdType("ad3");
                break;
            default:
                break;
        }
        return adminAdvertisementRegisterDbDto;
    }
}
