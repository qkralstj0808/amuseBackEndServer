package com.example.amusetravelproejct.controller.admin.api;

import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.controller.admin.dto.AdminAdvertisementRegisterDto;
import com.example.amusetravelproejct.controller.admin.dto.AdminAdvertisementRegisterDbDto;
import com.example.amusetravelproejct.controller.admin.dto.ItemInfoDto;
import com.example.amusetravelproejct.controller.admin.dto.ProductRegisterDto;
import com.example.amusetravelproejct.controller.admin.dto.resp.AdvertisementPageResponse;
import com.example.amusetravelproejct.controller.admin.service.*;
import com.example.amusetravelproejct.domain.*;
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
    private final ItemInforRepository itemInforRepository;
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

    @PostMapping("/ad/register")
    public ResponseTemplate<AdvertisementPageResponse.advertisementRegister> reqAdvertisementRegister(@RequestBody AdminAdvertisementRegisterDto adminAdvertisementRegisterDto){

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
            case "컨시어지 여행":
                advertisement.setCategory(categoryService.getCategoryById(1L).get());
                break;
            case "아이돌봄 여행":
                advertisement.setCategory(categoryService.getCategoryById(2L).get());
                break;
            case "어르신돌봄 여행":
                advertisement.setCategory(categoryService.getCategoryById(3L).get());
                break;
            default:
                break;
        }
        AdminAdvertisement advertisementTemp = adminAdvertisementService.createAdvertisement(advertisement);
        return new ResponseTemplate<>(new AdvertisementPageResponse.advertisementRegister(advertisementTemp.getId(),
                advertisementTemp.getAdvertisementTitle(), advertisementTemp.getAdvertisementStartDate(),
                advertisementTemp.getAdvertisementEndDate(), advertisementTemp.getAdvertisementType().name(), advertisementTemp.getCategory().getCategoryName(),
                advertisementTemp.getAdvertisementContent(), advertisementTemp.getCreatedAdDate(), advertisementTemp.getAdmin().getEmail()));
    }

    @PostMapping("/ad/edit")
    public ResponseTemplate<AdvertisementPageResponse.advertisementEdit> reqAdvertisementEdit(@RequestBody AdminAdvertisementRegisterDbDto adminAdvertisementRegisterDbDto) {

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

        switch (adminAdvertisementRegisterDbDto.getAdCategory()){
            case "컨시어지 여행":
                advertisement.setCategory(categoryService.getCategoryById(1L).get());
                break;
            case "아이돌봄 여행":
                advertisement.setCategory(categoryService.getCategoryById(2L).get());
                break;
            case "어르신돌봄 여행":
                advertisement.setCategory(categoryService.getCategoryById(3L).get());
                break;
            default:
                break;
        }

        AdminAdvertisement advertisementTemp = adminAdvertisementService.updateAdvertisement(advertisement);
        return new ResponseTemplate<>(new AdvertisementPageResponse.advertisementEdit(advertisementTemp.getId(),
                advertisementTemp.getAdvertisementTitle(), advertisementTemp.getAdvertisementStartDate(),
                advertisementTemp.getAdvertisementEndDate(), advertisementTemp.getAdvertisementType().name(), advertisementTemp.getCategory().getCategoryName(),
                advertisementTemp.getAdvertisementContent(), advertisementTemp.getModifiedDate(), advertisementTemp.getUpdateAdmin().getEmail()));
    }

    @GetMapping("/ad/getList")
    public ResponseTemplate<List<AdvertisementPageResponse.advertisementList>> reqAdvertisementList(){
        AdminAdvertisementService adminAdvertisementService = new AdminAdvertisementService(adminAdvertisementRepository);

        List<AdminAdvertisement> advertisementList = adminAdvertisementService.getAllAdvertisements();
        List<AdvertisementPageResponse.advertisementList> advertisementListResponse = new ArrayList<>();

        for (int i = 0; i < advertisementList.size(); i++){
            advertisementListResponse.add(new AdvertisementPageResponse.advertisementList(advertisementList.get(i).getId(),
                    advertisementList.get(i).getAdvertisementTitle(), advertisementList.get(i).getAdvertisementStartDate(),
                    advertisementList.get(i).getAdvertisementEndDate(), advertisementList.get(i).getAdvertisementType().name(), advertisementList.get(i).getCategory().getCategoryName(),
                    advertisementList.get(i).getAdvertisementContent(), advertisementList.get(i).getCreatedAdDate(), advertisementList.get(i).getAdmin().getEmail(),advertisementList.get(i).getModifiedDate(),
                    advertisementList.get(i).getUpdateAdmin() == null ? "NULL" : advertisementList.get(i).getUpdateAdmin().getEmail()));
        }

        return new ResponseTemplate<>(advertisementListResponse);
    }

    @PostMapping("/product/create")
    public ResponseTemplate<String> reqProductCreate(@RequestBody ProductRegisterDto productRegisterDto){
        CategoryService categoryService = new CategoryService(categoryRepository);
        ProductService productService = new ProductService(itemInforRepository);
        ItemImgService itemImgService = new ItemImgService(imgRepository);
        PaymentTicketService paymentTicketService = new PaymentTicketService(paymentTicketRepository);
        ItemTicketService itemTicketService = new ItemTicketService(itemTicketRepository);
        ItemCourseService itemCourseService = new ItemCourseService(itemCourseRepository);

        log.info(productRegisterDto.toString());


        Item item = new Item();
        item = productService.saveItem(item);

        item.setItemCode(productRegisterDto.getProductId());
        item.setCategory(categoryRepository.findByCategoryName(productRegisterDto.getCategory()).get());

        item.setCountry(productRegisterDto.getLocation().getCountry());
        item.setCity(productRegisterDto.getLocation().getCity());
        item.setItemIntroduce(productRegisterDto.getProductInfo());

        item.setAddInfo(productRegisterDto.getExtraInfo());


        List<String> imgUrls = new ArrayList<>();
        for (int i = 0; i < productRegisterDto.getMainImg().size(); i++){
            imgUrls.add(productRegisterDto.getMainImg().get(i).getImageURL());
        }
        itemImgService.saveItemImgs(imgUrls, item);
//
//
//        for (int i = 0; i < productRegisterDto.getTicket().size(); i++) {
//            ItemTicket itemTicket = new ItemTicket();
//            itemTicket.setItem(item);
//            for (int j = 0; j < productRegisterDto.getTicket().get(i).getPriceList().size(); j++) {
//               PaymentTicket paymentTicket = new PaymentTicket();
//               paymentTicket.setItemTicket(itemTicket);
//               paymentTicket.setPrice(Long.getLong(productRegisterDto.getTicket().get(i).getPriceList().get(j).getPrice()));
//               paymentTicket.setStartDate(productRegisterDto.getTicket().get(i).getPriceList().get(j).getStartDate());
//               paymentTicket.setEndDate(productRegisterDto.getTicket().get(i).getPriceList().get(j).getEndDate());
//               paymentTicketService.savePaymentTicket(paymentTicket);
//            }
//        }




//        List<ItemTicket> itemTickets = new ArrayList<>();
//
//        for (int i = 0; i < productRegisterDto.getTicket().size(); i++) {
//
//            ItemTicket itemTicket = new ItemTicket();
//            List<PaymentTicket> paymentTickets = new ArrayList<>();
//
//            itemTicket.setItem(item);
//            itemTicket.setTitle(productRegisterDto.getTicket().get(i).getTitle());
//            itemTicket.setContent(productRegisterDto.getTicket().get(i).getContent());
//
//            for (int j = 0; j < productRegisterDto.getTicket().get(i).getPriceList().size(); j++) {
//               PaymentTicket paymentTicket = new PaymentTicket();
//                paymentTicket.setItemTicket(itemTicket);
//                paymentTicket.setPrice(Long.getLong(productRegisterDto.getTicket().get(i).getPriceList().get(j).getPrice()));
//                paymentTicket.setStartDate(productRegisterDto.getTicket().get(i).getPriceList().get(j).getStartDate());
//                paymentTicket.setEndDate(productRegisterDto.getTicket().get(i).getPriceList().get(j).getEndDate());
//                paymentTickets.add(paymentTicketService.savePaymentTicket(paymentTicket));
//            }
//
//            itemTicket.setPaymentTickets(paymentTickets);
//            itemTickets.add(itemTicketService.saveItemTicket(itemTicket));
//        }
//
//
//        item.setItemTickets(itemTickets);
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

        return new ResponseTemplate<>("상품 등록 완료");
    }
}
