package com.example.amusetravelproejct.controller.admin.api;

import com.amazonaws.services.s3.AmazonS3;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.controller.admin.UtilMethod;
import com.example.amusetravelproejct.controller.admin.dto.AdminAdvertisementRegisterDto;
import com.example.amusetravelproejct.controller.admin.dto.AdminAdvertisementRegisterDbDto;
import com.example.amusetravelproejct.controller.admin.dto.req.ProductRegisterDto;
import com.example.amusetravelproejct.controller.admin.dto.req.AdminPageRequest;
import com.example.amusetravelproejct.controller.admin.dto.resp.AdminPageResponse;
import com.example.amusetravelproejct.controller.admin.service.*;
import com.example.amusetravelproejct.domain.*;
import com.example.amusetravelproejct.domain.person_enum.Adver;
import com.example.amusetravelproejct.domain.person_enum.WEEKDAY;
import com.example.amusetravelproejct.repository.*;
import com.example.amusetravelproejct.repository.ItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;

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
    private final AlarmRepository alarmRepository;
    private final ItemTicketPriceRepository itemTicketPriceRepository;
    private final AmazonS3 amazonS3Client;
    ObjectMapper objectMapper = new ObjectMapper();





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

    @Transactional
    @PostMapping("/product/create")
    public ResponseTemplate<String> reqProductCreate(@RequestBody ProductRegisterDto productRegisterDto) throws ParseException {
        CategoryService categoryService = new CategoryService(categoryRepository);
        ProductService productService = new ProductService(itemRepository);
        ItemImgService itemImgService = new ItemImgService(imgRepository);
        ItemTicketService itemTicketService = new ItemTicketService(itemTicketRepository);
        ItemCourseService itemCourseService = new ItemCourseService(itemCourseRepository,amazonS3Client);
        AdminService adminService = new AdminService(adminRepository);
        ItemTicketPriceService itemTicketPriceService = new ItemTicketPriceService(itemTicketPriceRepository);
        UtilMethod utilMethod = new UtilMethod(amazonS3Client);

        String[] day = {"sun","mon","tue","wed","thu","fri","sat"};

        log.info(productRegisterDto.toString());


        Item item = new Item();
        item = productService.saveItem(item);

        item.setItemCode(productRegisterDto.getProductId());
        item.setTitle(productRegisterDto.getTitle());
        item.setCategory(categoryService.getCategoryByName(productRegisterDto.getCategory()).get());
        item.setCountry(productRegisterDto.getLocation().getCountry());
        item.setCity(productRegisterDto.getLocation().getCity());
        item.setContent_1(productRegisterDto.getMainInfo());
        item.setContent_2(productRegisterDto.getExtraInfo());
        item.setAdmin(adminService.getAdminByEmail(productRegisterDto.getAdmin()).get());
        item.setStartPrice(productRegisterDto.getStartPrice());
        item.setDuration(productRegisterDto.getDuration().intValue());
        item.setStartDate(UtilMethod.date.parse(productRegisterDto.getStartDate()));
        item.setEndDate(UtilMethod.date.parse(productRegisterDto.getEndDate()));


        //ticket

        /*
            1.데이터 입력 (start, end, price(지정값), weekdayPrices(요일값)
            2.우선순위 생성
                1. end - start 의 값이 가장 작은 값이 우선
            3.DB 입력
                0. startData를 특정 set에 넣어서 중복으로 입력되지 않도록 함
                1. 지정값 확인
                2. 지정값이 0인 것을 우선으로 입력
                3. 지정값이 0이 아닌 것을 입력
                    1. 요일별 입력 (0이면 입력 X)
                    2. 지정값 입력
         */

        for (int i = 0; i < productRegisterDto.getTicket().size(); i++){
            ItemTicket itemTicket = new ItemTicket();
            itemTicket.setItem(item);
            itemTicket.setTitle(productRegisterDto.getTicket().get(i).getTitle());
            itemTicket.setContent(productRegisterDto.getTicket().get(i).getContent());

            List<Date> startDate = new ArrayList<>();
            List<Date> endDate = new ArrayList<>();
            List<Long> datePoint = new ArrayList<>();
            List<Long> price = new ArrayList<>();
            List<ProductRegisterDto.TicketDto.PriceListDto.WeekdayPrices> weekdayPrices = new ArrayList<>();
            List<ItemTicketPrice> itemTicketPrices = new ArrayList<>();
            Set<Long> dateSet = new HashSet<>();

            //가격 관련 데이터 받기
            productRegisterDto.getTicket().get(i).getPriceList().forEach(prices -> {
                weekdayPrices.add(prices.getWeekdayPrices());
                Date startDateTemp;
                Date endDateTemp;


                try {
                    startDateTemp = (Date) UtilMethod.date.parse(prices.getStartDate());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                try {
                    endDateTemp = (Date) UtilMethod.date.parse(prices.getEndDate());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                startDate.add(startDateTemp);
                endDate.add(endDateTemp);

                price.add(Long.valueOf(prices.getPrice()));
                datePoint.add(endDateTemp.getTime() - startDateTemp.getTime());

            });
            int count = datePoint.size();
            while(count-- != 0){
                int index = datePoint.indexOf(datePoint.stream().min(Long::compareTo).get());
                HashMap<String, String> weekDayPrices = (HashMap<String, String>) objectMapper.convertValue(weekdayPrices.get(index), Map.class);
                if (price.get(index) == 0) {
                    Date startDateTemp = new Date(startDate.get(index).getTime());
                    while (startDateTemp.getTime() <= endDate.get(index).getTime()) {
                        if (dateSet.add(startDateTemp.getTime())) {
                            ItemTicketPrice itemTicketPrice = new ItemTicketPrice();
                            itemTicketPrice.setPrice(Long.valueOf(weekDayPrices.get(day[startDateTemp.getDay()])));
                            itemTicketPrice.setStartDate(startDateTemp.toString());
                            itemTicketPrice.setItemTicket(itemTicket);
                            itemTicketPriceService.createItemTicketPrice(itemTicketPrice);
                        }
                        startDateTemp.setTime(startDateTemp.getTime() + (1000 * 60 * 60 * 24));     //하루 더하기
                    }
                }else{
                    Date startDateTemp = new Date(startDate.get(index).getTime());
                    while(startDateTemp.getTime() <= endDate.get(index).getTime()){
                        if (!(Long.valueOf(weekDayPrices.get(day[startDateTemp.getDay()])) == 0) && dateSet.add(startDateTemp.getTime())){
                            ItemTicketPrice itemTicketPrice = new ItemTicketPrice();
                            itemTicketPrice.setPrice(Long.valueOf(weekDayPrices.get(day[startDateTemp.getDay()])));
                            itemTicketPrice.setStartDate(startDateTemp.toString());
                            itemTicketPrice.setItemTicket(itemTicket);
                            itemTicketPrices.add(itemTicketPrice);
                            itemTicketPriceService.createItemTicketPrice(itemTicketPrice);
                        }
                        startDateTemp.setTime(startDateTemp.getTime() + (1000 * 60 * 60 * 24));     //하루 더하기
                    }

                    startDateTemp = startDate.get(index);
                    while(startDateTemp.getTime() <= endDate.get(index).getTime()){
                        if (dateSet.add(startDateTemp.getTime())) {
                            ItemTicketPrice itemTicketPrice = new ItemTicketPrice();
                            itemTicketPrice.setStartDate(startDateTemp.toString());
                            itemTicketPrice.setPrice(price.get(index));
                            itemTicketPrice.setItemTicket(itemTicket);
                            itemTicketPrices.add(itemTicketPrice);
                            itemTicketPriceService.createItemTicketPrice(itemTicketPrice);
                        }
                        startDateTemp.setTime(startDateTemp.getTime() + (1000 * 60 * 60 * 24));     //하루 더하기
                    }
                }
                datePoint.set(index,Long.MAX_VALUE);
            }
            itemTicketService.saveItemTicket(itemTicket);
        }

        //course
        for (int i = 0; i < productRegisterDto.getMainImg().size(); i++){
            ItemImg itemImg = new ItemImg();
            String url = utilMethod.getCourseImgUrl(productRegisterDto.getMainImg().get(i).getBase64Data(),
                    productRegisterDto.getMainImg().get(i).getFileName());

            itemImg.setImgUrl(url);
            itemImg.setItem(item);
            itemImgService.create(itemImg);
        }

        List<ItemCourse> itemCourses = new ArrayList<>();
        for(int i = 0; i < productRegisterDto.getCourse().size(); i++){
            ItemCourse itemCourse = new ItemCourse();
            itemCourse.setItem(item);
            itemCourse.setTitle(productRegisterDto.getCourse().get(i).getTitle());
            itemCourse.setTimeCost(productRegisterDto.getCourse().get(i).getTimeCost());
            itemCourse.setContent(productRegisterDto.getCourse().get(i).getContent());
            itemCourse.setSequenceId(Long.valueOf(i));

            String url = utilMethod.getCourseImgUrl(productRegisterDto.getCourse().get(i).getImage().getBase64Data(),
                    productRegisterDto.getCourse().get(i).getImage().getFileName());

            itemCourse.setImageUrl(url);
            itemCourse.setTimeCost(productRegisterDto.getCourse().get(i).getTimeCost());
            itemCourseService.saveItemCourse(itemCourse);
            itemCourses.add(itemCourse);
        }

        productService.saveItem(item);

        return new ResponseTemplate<>("상품 생성 완료");
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
            AdminPageResponse.categoryDetail categoryDetail = new AdminPageResponse.categoryDetail();
            categoryDetail.setId(item.getId());
            categoryDetail.setCode(item.getItemCode());
            categoryDetail.setTitle(item.getTitle());
            categoryDetail.setCreatedAt(item.getCreatedDate());
            categoryDetail.setCreatedBy(item.getAdmin().getEmail());

            if(item.getUpdateAdmin() == null) {
                categoryDetail.setUpdatedAt(null);
            } else{
                categoryDetail.setUpdatedAt(item.getModifiedDate());
            }
            categoryDetails.add(categoryDetail);
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
