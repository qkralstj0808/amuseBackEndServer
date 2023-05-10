package com.example.amusetravelproejct.controller.admin.api;

import com.amazonaws.services.s3.AmazonS3;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.controller.admin.dto.AdminAdvertisementRegisterDto;
import com.example.amusetravelproejct.controller.admin.dto.AdminAdvertisementRegisterDbDto;
import com.example.amusetravelproejct.controller.admin.dto.ProductRegisterDto;
import com.example.amusetravelproejct.controller.admin.dto.req.AdminPageRequest;
import com.example.amusetravelproejct.controller.admin.dto.resp.AdminPageResponse;
import com.example.amusetravelproejct.controller.admin.service.*;
import com.example.amusetravelproejct.domain.*;
import com.example.amusetravelproejct.domain.person_enum.Adver;
import com.example.amusetravelproejct.repository.*;
import com.example.amusetravelproejct.repository.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@RestController
@RequestMapping("/test/api")
@Slf4j
public class RestFullApi {
    private final ItemRepository itemRepository;
    private final ImgRepository imgRepository;
    private final ItemTicketRepository itemTicketRepository;
    private final ItemCourseRepository itemCourseRepository;
    private final ItemIconRepository itemIconRepository;
    private final ItemPriceRepository itemPriceRepository;
    private final ItemContentRepository itemContentRepository;
    private final PaymentTicketRepository paymentTicketRepository;
    private final CategoryRepository categoryRepository;
    private final AdminRepository adminRepository;
    private final AdminAdvertisementRepository adminAdvertisementRepository;
    private final ItemCourseRepository courseRepository;
    private final ItemOtherContentRepository itemOtherContentRepository;
    private final AmazonS3 amazonS3Client;






    @PostMapping("/ad/register")
    public ResponseTemplate<AdminPageResponse.advertisementRegister> reqAdvertisementRegister(@RequestBody AdminAdvertisementRegisterDto adminAdvertisementRegisterDto){

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
        advertisement.setCategory(categoryService.getCategoryByName(adminAdvertisementRegisterDto.getAdCategory()).get());


        AdminAdvertisement advertisementTemp = adminAdvertisementService.createAdvertisement(advertisement);
        return new ResponseTemplate<>(new AdminPageResponse.advertisementRegister(advertisementTemp.getId(),
                advertisementTemp.getAdvertisementTitle(), advertisementTemp.getAdvertisementStartDate(),
                advertisementTemp.getAdvertisementEndDate(), advertisementTemp.getAdvertisementType().name(), advertisementTemp.getCategory().getCategoryName(),
                advertisementTemp.getAdvertisementContent(), advertisementTemp.getCreatedAdDate(), advertisementTemp.getAdmin().getEmail()));
    }

    @PostMapping("/ad/edit")
    public ResponseTemplate<AdminPageResponse.advertisementEdit> reqAdvertisementEdit(@RequestBody AdminAdvertisementRegisterDbDto adminAdvertisementRegisterDbDto) {

        AdminAdvertisementService adminAdvertisementService = new AdminAdvertisementService(adminAdvertisementRepository);
        CategoryService categoryService = new CategoryService(categoryRepository);
        AdminService adminService = new AdminService(adminRepository);

        Optional<AdminAdvertisement> optionalAdvertisement = adminAdvertisementService.getAdvertisementById(adminAdvertisementRegisterDbDto.getId());
        AdminAdvertisement advertisement = optionalAdvertisement.get();

        advertisement.setAdvertisementTitle(adminAdvertisementRegisterDbDto.getTitle());
        advertisement.setAdvertisementContent(adminAdvertisementRegisterDbDto.getAdContent());
        advertisement.setAdvertisementStartDate(adminAdvertisementRegisterDbDto.getStartDate());
        advertisement.setAdvertisementEndDate(adminAdvertisementRegisterDbDto.getEndDate());
        advertisement.setUpdateAdmin(adminService.getAdminByEmail(adminAdvertisementRegisterDbDto.getUpdatedAd()).get());

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
        advertisement.setCategory(categoryService.getCategoryByName(adminAdvertisementRegisterDbDto.getAdCategory()).get());

        AdminAdvertisement advertisementTemp = adminAdvertisementService.updateAdvertisement(advertisement);
        return new ResponseTemplate<>(new AdminPageResponse.advertisementEdit(advertisementTemp.getId(),
                advertisementTemp.getAdvertisementTitle(), advertisementTemp.getAdvertisementStartDate(),
                advertisementTemp.getAdvertisementEndDate(), advertisementTemp.getAdvertisementType().name(), advertisementTemp.getCategory().getCategoryName(),
                advertisementTemp.getAdvertisementContent(), advertisementTemp.getModifiedDate(), advertisementTemp.getUpdateAdmin().getEmail()));
    }

    @GetMapping("/ad/getList")
    public ResponseTemplate<List<AdminPageResponse.advertisementList>> reqAdvertisementList(){
        AdminAdvertisementService adminAdvertisementService = new AdminAdvertisementService(adminAdvertisementRepository);

        List<AdminAdvertisement> advertisementList = adminAdvertisementService.getAllAdvertisements();
        List<AdminPageResponse.advertisementList> advertisementListResponse = new ArrayList<>();

        for (int i = 0; i < advertisementList.size(); i++){
            advertisementListResponse.add(new AdminPageResponse.advertisementList(advertisementList.get(i).getId(),
                    advertisementList.get(i).getAdvertisementTitle(), advertisementList.get(i).getAdvertisementStartDate(),
                    advertisementList.get(i).getAdvertisementEndDate(), advertisementList.get(i).getAdvertisementType().name(), advertisementList.get(i).getCategory().getCategoryName(),
                    advertisementList.get(i).getAdvertisementContent(), advertisementList.get(i).getCreatedAdDate(), advertisementList.get(i).getAdmin().getEmail(),advertisementList.get(i).getModifiedDate(),
                    advertisementList.get(i).getUpdateAdmin() == null ? "NULL" : advertisementList.get(i).getUpdateAdmin().getEmail()));
        }

        return new ResponseTemplate<>(advertisementListResponse);
    }

    @PostMapping("/product/create")
    public ResponseTemplate<List<String>> reqProductCreate(@RequestBody ProductRegisterDto productRegisterDto){
        CategoryService categoryService = new CategoryService(categoryRepository);
        ProductService productService = new ProductService(itemRepository);
        ItemImgService itemImgService = new ItemImgService(imgRepository,amazonS3Client);
        PaymentTicketService paymentTicketService = new PaymentTicketService(paymentTicketRepository);
        ItemTicketService itemTicketService = new ItemTicketService(itemTicketRepository);
        ItemCourseService itemCourseService = new ItemCourseService(itemCourseRepository);

        log.info(productRegisterDto.toString());


        Item item = new Item();
        item = productService.saveItem(item);
        List<String> imgUrl = new ArrayList<>();

        item.setItemCode(productRegisterDto.getProductId());
        item.setCategory(categoryService.getCategoryByName(productRegisterDto.getCategory()).get());

        item.setCountry(productRegisterDto.getLocation().getCountry());
        item.setCity(productRegisterDto.getLocation().getCity());
        item.setContent_1(productRegisterDto.getProductInfo());

        item.setContent_2(productRegisterDto.getExtraInfo());


        List<String> imgUrls = new ArrayList<>();
        List<String> keys = new ArrayList<>();
        for (int i = 0; i < productRegisterDto.getMainImg().size(); i++){
            imgUrls.add(productRegisterDto.getMainImg().get(i).getBase64Data());
            keys.add(productRegisterDto.getMainImg().get(i).getFileName());
        }

        imgUrl = itemImgService.saveItemImgs(imgUrls, keys,item);
        productService.saveItem(item);

        List<ItemCourse> itemCourses = new ArrayList<>();
        for(int i = 0; i < productRegisterDto.getCourse().size(); i++){
            ItemCourse itemCourse = new ItemCourse();
            itemCourse.setItem(item);
            itemCourse.setTitle(productRegisterDto.getCourse().get(i).getTitle());
            itemCourse.setContent(productRegisterDto.getCourse().get(i).getContent());
            itemCourse.setSequenceId(Long.valueOf(i));
            itemCourse.setImageUrl(productRegisterDto.getCourse().get(i).getImageURL());
            itemCourse.setTimeCost(productRegisterDto.getCourse().get(i).getTimeCost());

            itemCourseService.saveItemCourse(itemCourse);
            itemCourses.add(itemCourse);
        }

        item.setItemCourses(itemCourses);

        return new ResponseTemplate<>(imgUrl);
    }

    @PostMapping("/category/register")
    public ResponseTemplate<AdminPageResponse.categoryRegister> reqCategoryRegister(@RequestBody AdminPageRequest.categoryRegister  categoryRegisterDto){
        CategoryService categoryService = new CategoryService(categoryRepository);
        AdminService adminService = new AdminService(adminRepository);

        System.out.println(categoryRegisterDto.toString());

        Category category = new Category();
        category.setCategoryName(categoryRegisterDto.getCategoryName());
        category.setAdmin(adminService.getAdminByEmail(categoryRegisterDto.getCreatedAd()).get());
        Category categoryTemp = categoryService.createCategory(category);

        return new ResponseTemplate<>(new AdminPageResponse.categoryRegister(categoryTemp.getId(), categoryTemp.getCategoryName(), categoryTemp.getCreatedAdDate() , categoryTemp.getAdmin().getEmail()));
    }


    @PostMapping("/category/detail")
    public ResponseTemplate<AdminPageResponse.categoryDetailList> categoryDetail(@RequestBody AdminPageRequest.categoryDetail categoryDetailDto){
        CategoryService categoryService = new CategoryService(categoryRepository);

        Category category = categoryService.getCategoryById(categoryDetailDto.getId()).get();
        int count = Math.toIntExact(categoryDetailDto.getLimit() > category.getItems().size() ? category.getItems().size() : categoryDetailDto.getLimit());
        List<Item> items = category.getItems().subList(0, count);

        List<AdminPageResponse.categoryDetail> categoryDetails = new ArrayList<>();
        for(int i = 0; i < items.size(); i++){
            Item item = items.get(i);
            categoryDetails.add(new AdminPageResponse.categoryDetail(item.getId(),item.getItemCode(),item.getTitle(),item.getCreatedDate() ,item.getCreated_by(),item.getModifiedDate(),item.getUpdated_ad()));
        }


        return new ResponseTemplate<>(new AdminPageResponse.categoryDetailList(category.getCategoryName(),categoryDetails));
    }

    @GetMapping("/category")
    public ResponseTemplate<List<AdminPageResponse.category>> category(){
        CategoryService categoryService = new CategoryService(categoryRepository);
        List<Category> categories = categoryService.getAllCategories();

        List<AdminPageResponse.category> categoryList = new ArrayList<>();

        for(int i = 0; i < categories.size(); i++){
            Category category = categories.get(i);
            System.out.println(category);
            categoryList.add(new AdminPageResponse.category(category.getId(),category.getCategoryName(),category.getCreatedAdDate(),category.getAdmin().getEmail(), (long) category.getItems().size()));
        }

        return new ResponseTemplate<>(categoryList);
    }

}
