package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.config.util.UtilMethod;
import com.example.amusetravelproejct.domain.*;

import com.example.amusetravelproejct.domain.person_enum.DisplayStatus;
import com.example.amusetravelproejct.domain.person_enum.Option;
import com.example.amusetravelproejct.dto.request.AdminPageRequest;
import com.example.amusetravelproejct.dto.request.ProductRegisterDto;
import com.example.amusetravelproejct.dto.response.AdminPageResponse;

import com.example.amusetravelproejct.dto.response.ItemResponse;
import com.example.amusetravelproejct.dto.response.MainPageResponse;
import com.example.amusetravelproejct.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.BooleanBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.tomcat.jni.Time;
import org.springframework.data.domain.*;
import org.springframework.data.querydsl.binding.QuerydslPredicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;
    private final AdminRepository adminRepository;
    private final ItemHashTagRepository itemHashTagRepository;
    private final ImgRepository imgRepository;
    private final ItemTicketRepository itemTicketRepository;
    private final ItemTicketPriceRepository itemTicketPriceRepository;
    private final ItemCourseRepository itemCourseRepository;
    private final TempHashTagRepository tempHashTagRepository;
    private final ItemTicketPriceRecodeRepository itemTicketPriceRecodeRepository;
    private final UserRepository userRepository;
    private final TargetUserRepository targetUserRepository;



    private final CategoryRepository categoryRepository;
    ObjectMapper objectMapper = new ObjectMapper();

    static String bucketName = "amuse-img";

    //Admin
    public Optional<Admin> getAdminByEmail(String email) {
        return Optional.ofNullable(adminRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND)));
    }

    //Item
    @Transactional
    public Item processCreateOrUpdate(ProductRegisterDto productRegisterDto) throws ParseException {
        Item item;
        if (productRegisterDto.getOption().equals("create")){
            item = new Item();
            itemRepository.save(item);
        } else{
            item = itemRepository.findById(productRegisterDto.getId()).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));
        }

        item.setItemCode(productRegisterDto.getProductId());
        item.setTitle(productRegisterDto.getTitle());
        List<String> hashTags = productRegisterDto.getCategory();
        hashTags.forEach(data -> {
            TempHashTag tempHashTag = new TempHashTag();

            if (tempHashTagRepository.findByHashTag(data).isEmpty()) {
                tempHashTag.setHashTag(data);
                tempHashTagRepository.save(tempHashTag);
            } else {
                tempHashTag = tempHashTagRepository.findByHashTag(data).get();
            }

            ItemHashTag itemHashTag = new ItemHashTag();

            itemHashTag.setItem(item);

            itemHashTag.setHashTag(data);
            itemHashTag.setTempHashTag(tempHashTag);
            itemHashTagRepository.save(itemHashTag);
        });

        item.setCountry(productRegisterDto.getLocation().getCountry());
        item.setCity(productRegisterDto.getLocation().getCity());
        item.setContent_1(productRegisterDto.getMainInfo());
        item.setContent_2(productRegisterDto.getExtraInfo());
        if (productRegisterDto.getAdmin() == null){
            item.setUpdateAdmin(getAdminByEmail(productRegisterDto.getUpdateAdmin()).get());
        }else{
            item.setAdmin(getAdminByEmail(productRegisterDto.getAdmin()).get());
        }
        item.setStartPrice(productRegisterDto.getStartPrice());

        Long duration = 0L;

        if (productRegisterDto.getDuration().length() < 4){
            duration = Long.valueOf(productRegisterDto.getDuration());
        } else{
            duration = Long.parseLong(productRegisterDto.getDuration().split("박")[1].split("일")[0]);
        }

        List<String> users = productRegisterDto.getAccessAuthority().getAccessibleUserList();

        if (!users.isEmpty()) {
            item.setGrade((long) Arrays.asList(UtilMethod.outGrad).indexOf(productRegisterDto.getAccessAuthority().getAccessibleTier()));
            users.forEach(email -> {
                User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
                TargetUser targetUser = new TargetUser();

                targetUser.setItem(item);
                targetUser.setUser(user);
                targetUserRepository.save(targetUser);
            });
        }
        item.setDuration(Math.toIntExact(duration));
        item.setStartDate(UtilMethod.date.parse(productRegisterDto.getStartDate()));
        item.setEndDate(UtilMethod.date.parse(productRegisterDto.getEndDate()));
        return item;
    }

    //ItemImg
    public void processItemImg(ProductRegisterDto productRegisterDto, UtilMethod utilMethod, Item item) {

        for (int i = 0; i < productRegisterDto.getMainImg().size(); i++) {
            if(productRegisterDto.getMainImg().get(i).getId() == null){
                ItemImg itemImg = new ItemImg();
                String url = utilMethod.getImgUrl(productRegisterDto.getMainImg().get(i).getBase64Data(),
                        productRegisterDto.getMainImg().get(i).getFileName());

                itemImg.setImgUrl(url);
                itemImg.setItem(item);
                imgRepository.save(itemImg);
            }else{
                if (productRegisterDto.getMainImg().get(i).getBase64Data() !=null){
                    ItemImg itemImg = imgRepository.findById(productRegisterDto.getMainImg().get(i).getId()).get();
                    String url = utilMethod.getImgUrl(productRegisterDto.getMainImg().get(i).getBase64Data(),
                            productRegisterDto.getMainImg().get(i).getFileName());

                    itemImg.setImgUrl(url);
                    itemImg.setItem(item);
                    imgRepository.save(itemImg);
                }
            }
        }
    }
    //ItemTicket
    public void processItemTicket(ProductRegisterDto productRegisterDto, Item item) throws ParseException {

        /*
            1.데이터 입력 (start, end, price(지정값), weekdayPrices(요일값)
            2.우선순위 생성
                1. end - start 의 값이 가장 작은 값이 우선
            3.DB 입력
                0. startData를 특정 set에 넣어서 중복으로 입력되지 않도록 함
                1. 가격 입력
                    1. 요일별 입력 (0이면 입력 X)
         */

        if (productRegisterDto.getOption().equals("create")){
            for (int i = 0; i < productRegisterDto.getTicket().size(); i++) {
                ItemTicket itemTicket = new ItemTicket();
                itemTicket.setItem(item);
                itemTicket.setTitle(productRegisterDto.getTicket().get(i).getTitle());
                itemTicket.setContent(productRegisterDto.getTicket().get(i).getContent());
                itemTicket.setCount(productRegisterDto.getTicket().get(i).getCount());

                List<Date> startDate = new ArrayList<>();
                List<Date> endDate = new ArrayList<>();
                List<Long> datePoint = new ArrayList<>();

                List<ProductRegisterDto.TicketDto.PriceListDto.WeekdayPrices> weekdayPrices = new ArrayList<>();
                Set<Long> dateSet = new HashSet<>();

                //가격 관련 데이터 받기
                productRegisterDto.getTicket().get(i).getPriceList().forEach(prices -> {
                    weekdayPrices.add(prices.getWeekdayPrices());
                    ItemTicketPriceRecode itemTicketPriceRecode = new ItemTicketPriceRecode();
                    Date startDateTemp;
                    Date endDateTemp;

                    itemTicketPriceRecode.setStartDate(prices.getStartDate());
                    itemTicketPriceRecode.setEndDate(prices.getEndDate());
                    itemTicketPriceRecode.setItemTicket(itemTicket);
                    itemTicketPriceRecode.setMon(prices.getWeekdayPrices().getMon());
                    itemTicketPriceRecode.setTue(prices.getWeekdayPrices().getTue());
                    itemTicketPriceRecode.setWed(prices.getWeekdayPrices().getWed());
                    itemTicketPriceRecode.setThu(prices.getWeekdayPrices().getThu());
                    itemTicketPriceRecode.setFri(prices.getWeekdayPrices().getFri());
                    itemTicketPriceRecode.setSat(prices.getWeekdayPrices().getSat());
                    itemTicketPriceRecode.setSun(prices.getWeekdayPrices().getSun());
                    itemTicketPriceRecodeRepository.save(itemTicketPriceRecode);

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


                    datePoint.add(endDateTemp.getTime() - startDateTemp.getTime());

                });
                int count = datePoint.size();
                while (count-- != 0) {
                    int index = datePoint.indexOf(datePoint.stream().min(Long::compareTo).get());
                    HashMap<String, String> weekDayPrices = (HashMap<String, String>) objectMapper.convertValue(weekdayPrices.get(index), Map.class);
                    Date startDateTemp = new Date(startDate.get(index).getTime());
                    while (startDateTemp.getTime() <= endDate.get(index).getTime()) {
                        if (dateSet.add(startDateTemp.getTime())) {
                            ItemTicketPrice itemTicketPrice = new ItemTicketPrice();
                            itemTicketPrice.setPrice(Long.valueOf(weekDayPrices.get(UtilMethod.day[startDateTemp.getDay()])));
                            itemTicketPrice.setStartDate(startDateTemp.toString());
                            itemTicketPrice.setItemTicket(itemTicket);
                            itemTicketPriceRepository.save(itemTicketPrice);
                        }
                        startDateTemp.setTime(startDateTemp.getTime() + (1000 * 60 * 60 * 24));     //하루 더하기
                    }
                    datePoint.set(index, Long.MAX_VALUE);
                }
                itemTicketRepository.save(itemTicket);
            }
        } else {

            List<Long> inputTicketId = new ArrayList<>();
            List<Long> inputTicketPriceId = new ArrayList<>();
            productRegisterDto.getTicket().forEach(ticketDto -> {
                inputTicketId.add(ticketDto.getId());
            });

            inputTicketId.forEach(data->{
                itemTicketRepository.deleteById(data);
            });

            for (int i = 0; i < productRegisterDto.getTicket().size(); i++) {
                ItemTicket itemTicket = itemTicketRepository.findById(productRegisterDto.getTicket().get(i).getId()).isEmpty() ? new ItemTicket() : itemTicketRepository.findById(productRegisterDto.getTicket().get(i).getId()).get();
                itemTicket.setTitle(productRegisterDto.getTicket().get(i).getTitle());
                itemTicket.setContent(productRegisterDto.getTicket().get(i).getContent());
                itemTicket.setItem(item);

                List<Date> startDate = new ArrayList<>();
                List<Date> endDate = new ArrayList<>();
                List<Long> datePoint = new ArrayList<>();

                List<ProductRegisterDto.TicketDto.PriceListDto.WeekdayPrices> weekdayPrices = new ArrayList<>();
                Set<Long> dateSet = new HashSet<>();

                //가격 관련 데이터 받기
                productRegisterDto.getTicket().get(i).getPriceList().forEach(prices -> {
                    weekdayPrices.add(prices.getWeekdayPrices());

                    ItemTicketPriceRecode itemTicketPriceRecode = itemTicketPriceRecodeRepository.findById(prices.getId()).isEmpty() ? new ItemTicketPriceRecode() : itemTicketPriceRecodeRepository.findById(prices.getId()).get();
                    Date startDateTemp;
                    Date endDateTemp;

                    itemTicketPriceRecode.setStartDate(prices.getStartDate());
                    itemTicketPriceRecode.setEndDate(prices.getEndDate());
                    itemTicketPriceRecode.setItemTicket(itemTicket);
                    itemTicketPriceRecode.setMon(prices.getWeekdayPrices().getMon());
                    itemTicketPriceRecode.setTue(prices.getWeekdayPrices().getTue());
                    itemTicketPriceRecode.setWed(prices.getWeekdayPrices().getWed());
                    itemTicketPriceRecode.setThu(prices.getWeekdayPrices().getThu());
                    itemTicketPriceRecode.setFri(prices.getWeekdayPrices().getFri());
                    itemTicketPriceRecode.setSat(prices.getWeekdayPrices().getSat());
                    itemTicketPriceRecode.setSun(prices.getWeekdayPrices().getSun());
                    itemTicketPriceRecodeRepository.save(itemTicketPriceRecode);

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


                    datePoint.add(endDateTemp.getTime() - startDateTemp.getTime());

                });
                int count = datePoint.size();
                while (count-- != 0) {
                    int index = datePoint.indexOf(datePoint.stream().min(Long::compareTo).get());
                    HashMap<String, String> weekDayPrices = (HashMap<String, String>) objectMapper.convertValue(weekdayPrices.get(index), Map.class);
                    Date startDateTemp = new Date(startDate.get(index).getTime());
                    while (startDateTemp.getTime() <= endDate.get(index).getTime()) {
                        if (dateSet.add(startDateTemp.getTime())) {
                            ItemTicketPrice itemTicketPrice = new ItemTicketPrice();
                            itemTicketPrice.setPrice(Long.valueOf(weekDayPrices.get(UtilMethod.day[startDateTemp.getDay()])));
                            itemTicketPrice.setStartDate(startDateTemp.toString());
                            itemTicketPrice.setItemTicket(itemTicket);
                            itemTicketPriceRepository.save(itemTicketPrice);
                        }
                        startDateTemp.setTime(startDateTemp.getTime() + (1000 * 60 * 60 * 24));     //하루 더하기
                    }
                    datePoint.set(index, Long.MAX_VALUE);
                }
                itemTicketRepository.save(itemTicket);
            }
        }
    }
    //ItemCourse
    public void processItemCourse(ProductRegisterDto productRegisterDto, UtilMethod utilMethod, Item item) {
        for (int i = 0; i < productRegisterDto.getCourse().size(); i++) {
            ItemCourse itemCourse = null;

            if (productRegisterDto.getOption() == "update"){
                List<Long> inputCourseId = new ArrayList<>();
                productRegisterDto.getCourse().forEach(course -> {
                    inputCourseId.add(course.getId());
                });

                for (int j = 0; j < item.getItemCourses().size(); i++){
                    if (!inputCourseId.contains(item.getItemCourses().get(i).getId())){
                        itemCourseRepository.deleteById(item.getItemCourses().get(i).getId());
                    }
                }
            }

            if(productRegisterDto.getCourse().get(i).getId() == null){
                itemCourse = new ItemCourse();
            }else{
                itemCourse = itemCourseRepository.findById(productRegisterDto.getCourse().get(i).getId()).get();
            }

            String url = "";
            if (productRegisterDto.getCourse().get(i).getImage().getBase64Data() != null){
                url = utilMethod.getImgUrl(productRegisterDto.getCourse().get(i).getImage().getBase64Data(),
                        productRegisterDto.getCourse().get(i).getImage().getFileName());
                itemCourse.setImageUrl(url);
            }
            itemCourse.setItem(item);
            itemCourse.setTitle(productRegisterDto.getCourse().get(i).getTitle());
            itemCourse.setTimeCost(productRegisterDto.getCourse().get(i).getTimeCost());
            itemCourse.setContent(productRegisterDto.getCourse().get(i).getContent());
            itemCourse.setSequenceId(productRegisterDto.getCourse().get(i).getSequenceId());
            itemCourse.setTimeCost(productRegisterDto.getCourse().get(i).getTimeCost());
            itemCourse.setDay(productRegisterDto.getCourse().get(i).getDay());
            itemCourse.setLatitude(productRegisterDto.getCourse().get(i).getLatitude());
            itemCourse.setLongitude(productRegisterDto.getCourse().get(i).getLongitude());
            itemCourseRepository.save(itemCourse);
        }
    }

    public AdminPageResponse.getItemByCategory processSearchItem(AdminPageRequest.getItemByCategory findItemsDto){
        BooleanBuilder predicateFirstQ = new BooleanBuilder();
        BooleanBuilder predicateSendQ = new BooleanBuilder();
        List<String> categoryData = findItemsDto.getCategoryNames();

        categoryData.forEach(data -> {
            TempHashTag tempHashTag = tempHashTagRepository.findByHashTag(data).orElseThrow(() -> new CustomException(ErrorCode.HASH_TAG_NOT_FOUND));
            predicateFirstQ.or(QItemHashTag.itemHashTag.tempHashTag.id.eq(tempHashTag.getId()));
        });

        Pageable pageable = PageRequest.of(Math.toIntExact(findItemsDto.getPage()), Math.toIntExact(findItemsDto.getLimit()), Sort.by("id").ascending());
        List<ItemHashTag> itemHashTag = (List<ItemHashTag>) itemHashTagRepository.findAll(predicateFirstQ);

        itemHashTag.forEach(itemHashTag1 -> {
            predicateSendQ.or(QItem.item.id.eq(itemHashTag1.getItem().getId()));
        });

        Page<Item> items = itemRepository.findAll(predicateSendQ, pageable);

        AdminPageResponse.getItemByCategory getItemByCategory = new AdminPageResponse.getItemByCategory();
        getItemByCategory.setPageCount((long) items.getTotalPages());
        List<AdminPageResponse.findItemByCategory> findItemByCategories = new ArrayList<>();

        items.forEach(data ->{
            List<String> categoryNames = new ArrayList<>();
            data.getItemHashTags().forEach(hashTag -> {
                categoryNames.add(hashTag.getHashTag());
            });
            findItemByCategories.add(new AdminPageResponse.findItemByCategory(data.getItemCode(),data.getTitle(),categoryNames,data.getAdmin().getEmail(),data.getCreatedDate(),
                    data.getUpdateAdmin() == null ?null : data.getUpdateAdmin().getEmail(),data.getUpdateAdmin() == null ? null : data.getModifiedDate()));
        });

        getItemByCategory.setData(findItemByCategories);
        return getItemByCategory;
    }

    public AdminPageResponse.getItemByCategory processSearchItemAll(AdminPageRequest.getItemByCategory findItemsDto) {
        BooleanBuilder predicateSendQ = new BooleanBuilder();
        Pageable pageable = PageRequest.of(Math.toIntExact(findItemsDto.getPage()), Math.toIntExact(findItemsDto.getLimit()), Sort.by("id").ascending());
        Page<Item> items = itemRepository.findAll(predicateSendQ, pageable);

        AdminPageResponse.getItemByCategory getItemByCategory = new AdminPageResponse.getItemByCategory();
        getItemByCategory.setPageCount((long) items.getTotalPages());
        List<AdminPageResponse.findItemByCategory> findItemByCategories = new ArrayList<>();

        items.forEach(data ->{
            List<String> categoryNames = new ArrayList<>();
            data.getItemHashTags().forEach(hashTag -> {
                categoryNames.add(hashTag.getHashTag());
            });
            findItemByCategories.add(new AdminPageResponse.findItemByCategory(data.getItemCode(),data.getTitle(),categoryNames,data.getAdmin().getEmail(),data.getCreatedDate(),
                    data.getUpdateAdmin() == null ?null : data.getUpdateAdmin().getEmail(),data.getUpdateAdmin() == null ? null : data.getModifiedDate()));
        });

        getItemByCategory.setData(findItemByCategories);
        return getItemByCategory;


    }


    public AdminPageResponse.getItemByCategory processSearchOrphanage(AdminPageRequest.getItemByCategory findOrphanageDto) {
        BooleanBuilder predicateFirstQ = new BooleanBuilder();
        BooleanBuilder predicateSendQ = new BooleanBuilder();
        List<String> categoryData = findOrphanageDto.getCategoryNames();

        categoryData.forEach(data -> {
            TempHashTag tempHashTag = tempHashTagRepository.findByHashTag(data).orElseThrow(() -> new CustomException(ErrorCode.HASH_TAG_NOT_FOUND));
            predicateFirstQ.or(QItemHashTag.itemHashTag.tempHashTag.id.eq(tempHashTag.getId()));

        });

        Pageable pageable = PageRequest.of(Math.toIntExact(findOrphanageDto.getPage()), Math.toIntExact(findOrphanageDto.getLimit()), Sort.by("id").ascending());
        List<ItemHashTag> itemHashTag = (List<ItemHashTag>) itemHashTagRepository.findAll(predicateFirstQ);


        itemHashTag.forEach(data ->{
            predicateSendQ.andNot(QItem.item.id.eq(data.getItem().getId()));
        });



        Page<Item> items = itemRepository.findAll(predicateSendQ, pageable);

        AdminPageResponse.getItemByCategory getItemByCategory = new AdminPageResponse.getItemByCategory();
        getItemByCategory.setPageCount((long) items.getTotalPages());
        List<AdminPageResponse.findItemByCategory> findItemByCategories = new ArrayList<>();

        items.forEach(data ->{
            List<String> categoryNames = new ArrayList<>();
            data.getItemHashTags().forEach(hashTag -> {
                categoryNames.add(hashTag.getHashTag());
            });
            findItemByCategories.add(new AdminPageResponse.findItemByCategory(data.getItemCode(),data.getTitle(),categoryNames,data.getAdmin().getEmail(),data.getCreatedDate(),
                    data.getUpdateAdmin() == null ?null : data.getUpdateAdmin().getEmail(),data.getUpdateAdmin() == null ? null : data.getModifiedDate()));
        });

        getItemByCategory.setData(findItemByCategories);
        return getItemByCategory;
    }

    public void processDeleteItem(String itemCode) {
        Item item = itemRepository.findByItemCode(itemCode).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));
        itemRepository.delete(item);
    }

    public ProductRegisterDto processGetItemDetail(String itemCode) {
        Item item = itemRepository.findByItemCode(itemCode).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

        ProductRegisterDto productRegisterDto = new ProductRegisterDto();
        productRegisterDto.setId(item.getId());
        productRegisterDto.setProductId(item.getItemCode());

        List<String> category = new ArrayList<>();
        item.getItemHashTags().forEach(itemHashTag -> {
            category.add(itemHashTag.getHashTag());
        });

        productRegisterDto.setCategory(category);
        productRegisterDto.setTitle(item.getTitle());

        ProductRegisterDto.Location location = new ProductRegisterDto.Location();
        location.setCity(item.getCity());
        location.setCountry(item.getCountry());
        productRegisterDto.setLocation(location);

        List<ProductRegisterDto.MainImageDto> mainImageDtos = new ArrayList<>();

        item.getItemImg_list().forEach(itemImg -> {
            ProductRegisterDto.MainImageDto mainImageDto = new ProductRegisterDto.MainImageDto();
            mainImageDto.setId(itemImg.getId());
            mainImageDto.setImgUrl(itemImg.getImgUrl());
            mainImageDtos.add(mainImageDto);
        });
        productRegisterDto.setMainImg(mainImageDtos);

        List<ProductRegisterDto.TicketDto> ticketDtos = new ArrayList<>();

        item.getItemTickets().forEach(itemTicket -> {
            ProductRegisterDto.TicketDto ticketDto = new ProductRegisterDto.TicketDto();
            ticketDto.setId(itemTicket.getId());
            ticketDto.setTitle(itemTicket.getTitle());
            ticketDto.setContent(itemTicket.getContent());
            ticketDto.setCount(itemTicket.getCount());
            List<ProductRegisterDto.TicketDto.PriceListDto> priceListDtos = new ArrayList<>();
            itemTicket.getItemTicketPriceRecodes().forEach(itemTicketPriceRecode -> {
                ProductRegisterDto.TicketDto.PriceListDto priceList = new ProductRegisterDto.TicketDto.PriceListDto();
                priceList.setId(itemTicketPriceRecode.getId());
                priceList.setStartDate(itemTicketPriceRecode.getStartDate());
                priceList.setEndDate(itemTicketPriceRecode.getEndDate());
                ProductRegisterDto.TicketDto.PriceListDto.WeekdayPrices weekdayPrices = new ProductRegisterDto.TicketDto.PriceListDto.WeekdayPrices();
                weekdayPrices.setMon(itemTicketPriceRecode.getMon());
                weekdayPrices.setTue(itemTicketPriceRecode.getTue());
                weekdayPrices.setWed(itemTicketPriceRecode.getWed());
                weekdayPrices.setThu(itemTicketPriceRecode.getThu());
                weekdayPrices.setFri(itemTicketPriceRecode.getFri());
                weekdayPrices.setSat(itemTicketPriceRecode.getSat());
                weekdayPrices.setSun(itemTicketPriceRecode.getSun());
                priceList.setWeekdayPrices(weekdayPrices);
                priceListDtos.add(priceList);
            });
            ticketDto.setPriceList(priceListDtos);
            ticketDtos.add(ticketDto);
        });
        productRegisterDto.setTicket(ticketDtos);
        productRegisterDto.setMainInfo(item.getContent_1());


        List<ProductRegisterDto.CourseDto> courseDtos = new ArrayList<>();
        item.getItemCourses().forEach(itemCourse -> {
            ProductRegisterDto.CourseDto courseDto = new ProductRegisterDto.CourseDto();
            courseDto.setId(itemCourse.getId());
            courseDto.setTitle(itemCourse.getTitle());
            courseDto.setContent(itemCourse.getContent());
            courseDto.setTimeCost(itemCourse.getTimeCost());
            courseDto.setSequenceId(itemCourse.getSequenceId());
            courseDto.setLatitude(itemCourse.getLatitude());
            courseDto.setLongitude(itemCourse.getLongitude());


            ProductRegisterDto.CourseDto.CourseImageDto courseImageDto = new ProductRegisterDto.CourseDto.CourseImageDto();
            courseImageDto.setImgUrl(itemCourse.getImageUrl());

            courseDto.setImage(courseImageDto);
            courseDtos.add(courseDto);
        });

        productRegisterDto.setCourse(courseDtos);
        productRegisterDto.setExtraInfo(item.getContent_2());
        productRegisterDto.setAdmin(item.getAdmin().getEmail());
        productRegisterDto.setUpdateAdmin(item.getUpdateAdmin() == null ? null : item.getUpdateAdmin().getEmail());
        productRegisterDto.setStartDate(String.valueOf(item.getStartDate()));
        productRegisterDto.setEndDate(String.valueOf(item.getEndDate()));
        productRegisterDto.setDuration(String.valueOf(item.getDuration()));

        ProductRegisterDto.accessData accessAuthority = new ProductRegisterDto.accessData();
        List<TargetUser> users = item.getTargetUsers();

        if (!users.isEmpty()){
            accessAuthority.setAccessibleTier(UtilMethod.outGrad[Math.toIntExact(item.getGrade())]);
            List<String> targetUsers = new ArrayList<>();
            users.forEach(targetUser -> {
                targetUsers.add(targetUser.getUser().getEmail());
            });

            accessAuthority.setAccessibleUserList(targetUsers);

        }
        productRegisterDto.setAccessAuthority(accessAuthority);
        productRegisterDto.setStartPrice(item.getStartPrice());
        productRegisterDto.setOption("update"); //create
        return productRegisterDto;
    }

    public ResponseTemplate<MainPageResponse.getItemPage> searchItemByWordAndConditionSort(int current_page, PageRequest pageRequest,
                                                                                           String[] contain_words) {
        Page<Item> searchItemByWordInTitle = itemRepository.searchItemByWordAndSort(contain_words,pageRequest);

        int total_page = searchItemByWordInTitle.getTotalPages();

        if(current_page == 1 && searchItemByWordInTitle.getContent().size() < 1){
            throw new CustomException(ErrorCode.ITEM_NOT_FOUND_IN_PAGE);
        }

        if(current_page-1 > total_page-1){
            throw new CustomException(ErrorCode.OUT_BOUND_PAGE);
        }

        return returnItemPage(searchItemByWordInTitle,total_page,current_page);

    }

    private ResponseTemplate<MainPageResponse.getItemPage> returnItemPage(Page<Item> categoryAllItemPage, int total_page, int current_page) {
        List<MainPageResponse.ItemInfo> itemInfo = new ArrayList<>();

        System.out.println("categoryAllItemPage.getContent().size() = " + categoryAllItemPage.getContent().size());
        System.out.println();
        if(categoryAllItemPage.getContent().size() != 0){
            for(int i = 0 ; i < categoryAllItemPage.getContent().size() ; i++){
                Item item = categoryAllItemPage.getContent().get(i);
                String categoryName = null;
                String itemImg = null;

                // img가 하나라도 있다면
                if(item.getItemImg_list().size() != 0){
                    itemImg = item.getItemImg_list().get(0).getImgUrl();
                }

                itemInfo.add(new MainPageResponse.ItemInfo(item.getId(),item.getItemCode(),item.getItemHashTags().stream().map(
                        itemHashTag -> new MainPageResponse.HashTag(itemHashTag.getHashTag())
                ).collect(Collectors.toList()),
                        itemImg,item.getTitle(),item.getCountry(),item.getCity(),item.getDuration(),
                        item.getLike_num(),item.getStartPrice()));
            }
        }
        return new ResponseTemplate<>(new MainPageResponse.getItemPage(itemInfo,total_page,current_page));
    }
    public ResponseTemplate<ItemResponse.getAllItemId> getAllItemId() {
        List<Long> allItemId = itemRepository.findAllItemId();
        return new ResponseTemplate(new ItemResponse.getAllItemId(allItemId.stream().collect(Collectors.toList())));
    }


    public void changeItemStatus(String displayStatus,String itemCode){
        Item item = itemRepository.findByItemCode(itemCode).orElseThrow(
                () -> new CustomException(ErrorCode.ITEM_NOT_FOUND)
        );
        if (DisplayStatus.DISPLAY.name().equals(displayStatus)){
            item.setDisplayStatus(DisplayStatus.DISPLAY);
        } else{
            item.setDisplayStatus(DisplayStatus.HIDDEN);
        }
    }



}
