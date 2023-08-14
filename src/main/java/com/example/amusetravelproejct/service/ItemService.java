package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.config.util.UtilMethod;
import com.example.amusetravelproejct.domain.*;

import com.example.amusetravelproejct.domain.person_enum.Grade;
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

import org.springframework.data.domain.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemHashTagRepository itemHashTagRepository;
    private final ImgRepository imgRepository;
    private final ItemTicketRepository itemTicketRepository;
    private final ItemTicketPriceRepository itemTicketPriceRepository;
    private final ItemCourseRepository itemCourseRepository;
    private final TempHashTagRepository tempHashTagRepository;
    private final ItemTicketPriceRecodeRepository itemTicketPriceRecodeRepository;
    private final UserRepository userRepository;
    private final TargetUserRepository targetUserRepository;
    private final IconRepository iconRepository;
    private final LikeItemRepository likeItemRepository;

    private final GuideRepository guideRepository;
    ObjectMapper objectMapper;

//    static String bucketName = "amuse-img";

    //Admin
//    public Optional<Admin> getAdminByAdminId(String admin_id) {
//        return Optional.ofNullable(adminRepository.findByAdminId(admin_id).orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND)));
//    }

    public List<Icon> getIconList() {
        return iconRepository.findAll();
    }

    //Item
    @Transactional
    public Item processCreate(ProductRegisterDto productRegisterDto,Admin admin) throws ParseException {
        Item item;
        itemRepository.findByItemCode(productRegisterDto.getProductId()).ifPresent(data -> {
            throw new CustomException(ErrorCode.ITEM_ALREADY_EXIST);
        });
        item = new Item();
        itemRepository.save(item);


        // 프론트 미구현
//        productRegisterDto.getItemIcon().forEach(data -> {
//            ItemIcon itemIcon = new ItemIcon();
//            itemIcon.setItem(item);
//            itemIcon.setText(data.getText());
//            itemIcon.setIcon(iconRepository.findById(data.getIconId()).get());
//            itemIconRepository.save(itemIcon);
//        });



        item.setItemCode(productRegisterDto.getProductId());
        item.setTitle(productRegisterDto.getTitle());
        List<String> hashTags = productRegisterDto.getCategory();

        if(hashTags != null){
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
        }

        if(productRegisterDto.getLocation() != null){
            item.setCountry(productRegisterDto.getLocation().getCountry());
            item.setCity(productRegisterDto.getLocation().getCity());
        }else{
            item.setCountry(null);
            item.setCity(null);
        }

        item.setContent_1(productRegisterDto.getMainInfo());
        item.setContent_2(productRegisterDto.getExtraInfo());
        item.setAdmin(admin);
        item.setStartPrice(productRegisterDto.getStartPrice());
        item.setStartPoint(productRegisterDto.getStartPoint());
        item.setRunningTime(productRegisterDto.getRunningTime());
        item.setActivityIntensity(productRegisterDto.getActivityIntensity());
        item.setLanguage(productRegisterDto.getLanguage());
        item.setLike_num(0);
        item.setReview_count(0);
        item.setViewCount(0);
        item.setRated(0.);

        log.info("productRegisterDto.getGuide_code() : " + productRegisterDto.getGuide_code());
        if(productRegisterDto.getGuide_code() != null && !productRegisterDto.getGuide_code().equals("")){
            Guide guide = guideRepository.findByCode(productRegisterDto.getGuide_code()).orElseThrow(
                    () -> new CustomException(ErrorCode.NOT_FOUND_GUIDE)
            );
//        Guide guide1 = guideRepository.findById(productRegisterDto.getGuide_db_id()).orElseThrow(
//                () -> new CustomException(ErrorCode.NOT_FOUND_GUIDE)
//        );

            item.setGuide(guide);
            item.setGuide_comment(productRegisterDto.getGuide_comment());
        }else{
            item.setGuide(null);
            item.setGuide_comment(null);
        }
        // 가이드 추가


        long duration ;

        if(productRegisterDto.getDuration() != null && !productRegisterDto.getDuration().equals("")){
            if (productRegisterDto.getDuration().length() < 4){
                duration = Long.parseLong(productRegisterDto.getDuration());
            } else{
                duration = Long.parseLong(productRegisterDto.getDuration().split(" ")[1].split("일")[0]);
            }
            item.setDuration(Math.toIntExact(duration));
        }else{
            item.setDuration(null);
        }

        if(productRegisterDto.getAccessAuthority() != null){
            if (productRegisterDto.getAccessAuthority().getAccessibleTier().equals("")){
                item.setGrade(Grade.Bronze);
            }else{
                item.setGrade(Grade.valueOf(productRegisterDto.getAccessAuthority().getAccessibleTier()));
            }

            if (productRegisterDto.getAccessAuthority().getAccessibleUserList() !=  null && !productRegisterDto.getAccessAuthority().getAccessibleUserList().isEmpty()) {
                List<String> users = productRegisterDto.getAccessAuthority().getAccessibleUserList();
                users.forEach(email -> {
                    List<User> userList = userRepository.findByEmail(email);
                    if(userList.size() != 0){
                        userList.forEach(user -> {
                            TargetUser targetUser = new TargetUser();
                            targetUser.setItem(item);
                            targetUser.setUser(user);
                            targetUserRepository.save(targetUser);
                        });
                    }
                });
            }
        }else{
            item.setGrade(Grade.Bronze);
        }

        if(productRegisterDto.getStartDate() != null && !productRegisterDto.getStartDate().equals("")){
            item.setStartDate(UtilMethod.date.parse(productRegisterDto.getStartDate()));
        }else{
            item.setStartDate(null);
        }

        if(productRegisterDto.getEndDate() != null && !productRegisterDto.getEndDate().equals("")){
            item.setEndDate(UtilMethod.date.parse(productRegisterDto.getEndDate()));
        }else{
            item.setEndDate(null);
        }

        item.setDisplay(true);
        return item;
    }

    @Transactional
    public Item processUpdate(ProductRegisterDto productRegisterDto,Admin admin) throws ParseException {
        Item item = itemRepository.findById(productRegisterDto.getId()).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));
        item.setItemCode(productRegisterDto.getProductId());
        item.setTitle(productRegisterDto.getTitle());
        List<String> hashTags = productRegisterDto.getCategory();

        // 프론트 미구현
//        item.getItemIcons().clear();
//        productRegisterDto.getItemIcon().forEach(data -> {
//            ItemIcon itemIcon = new ItemIcon();
//            itemIcon.setItem(item);
//            itemIcon.setText(data.getText());
//            itemIcon.setIcon(iconRepository.findById(data.getIconId()).get());
//            itemIconRepository.save(itemIcon);
//        });


        item.getItemHashTags().clear();
        if(hashTags != null){
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
        }

        if(productRegisterDto.getLocation() != null){
            item.setCountry(productRegisterDto.getLocation().getCountry());
            item.setCity(productRegisterDto.getLocation().getCity());
        }else{
            item.setCountry(null);
            item.setCity(null);
        }

        item.setContent_1(productRegisterDto.getMainInfo());
        item.setContent_2(productRegisterDto.getExtraInfo());
        item.setUpdateAdmin(admin);
        item.setStartPrice(productRegisterDto.getStartPrice());
        item.setStartPoint(productRegisterDto.getStartPoint());
        item.setRunningTime(productRegisterDto.getRunningTime());
        item.setActivityIntensity(productRegisterDto.getActivityIntensity());
        item.setLanguage(productRegisterDto.getLanguage());

//        if(productRegisterDto.getStartPoint() != null){
//
//        }
//
//        if(productRegisterDto.getRunningTime() != null){
//
//        }
//
//        if(productRegisterDto.getActivityIntensity() != null){
//
//        }

        // 가이드 업데이트
        log.info("productRegisterDto.getGuide_code() : " + productRegisterDto.getGuide_code());
        if(productRegisterDto.getGuide_code() != null && !productRegisterDto.getGuide_code().equals("")){
            Guide guide = guideRepository.findByCode(productRegisterDto.getGuide_code()).orElseThrow(
                    () -> new CustomException(ErrorCode.NOT_FOUND_GUIDE)
            );

//        Guide guide1 = guideRepository.findById(productRegisterDto.getGuide_db_id()).orElseThrow(
//                () -> new CustomException(ErrorCode.NOT_FOUND_GUIDE)
//        );

            item.setGuide(guide);
            item.setGuide_comment(productRegisterDto.getGuide_comment());
        }else{
            item.setGuide(null);
            item.setGuide_comment(null);
        }

        long duration ;

        if(productRegisterDto.getDuration() != null && !productRegisterDto.getDuration().equals("")){
            if (productRegisterDto.getDuration().length() < 4){
                duration = Long.parseLong(productRegisterDto.getDuration());
            } else{
                duration = Long.parseLong(productRegisterDto.getDuration().split(" ")[1].split("일")[0]);
            }
            item.setDuration(Math.toIntExact(duration));
        }else{
            item.setDuration(null);
        }

        item.getTargetUsers().clear();

        if(productRegisterDto.getAccessAuthority() != null){
            log.info("productRegisterDto.getAccessAuthority().getAccessibleTier() : " + productRegisterDto.getAccessAuthority().getAccessibleTier());
            if (productRegisterDto.getAccessAuthority().getAccessibleTier().equals("")){
                item.setGrade(Grade.Bronze);
            }else{

                item.setGrade(Grade.valueOf(productRegisterDto.getAccessAuthority().getAccessibleTier()));
            }

            if (productRegisterDto.getAccessAuthority().getAccessibleUserList() != null && !productRegisterDto.getAccessAuthority().getAccessibleUserList().isEmpty() ) {
                List<String> users = productRegisterDto.getAccessAuthority().getAccessibleUserList();
                users.forEach(email -> {
                    List<User> userList = userRepository.findByEmail(email);
                    if(userList.size() != 0){
                        userList.forEach(user -> {
                            TargetUser targetUser = new TargetUser();
                            targetUser.setItem(item);
                            targetUser.setUser(user);
                            targetUserRepository.save(targetUser);
                        });
                    }
                });
            }
        }

        if(productRegisterDto.getStartDate() != null && !productRegisterDto.getStartDate().equals("")){
            item.setStartDate(UtilMethod.date.parse(productRegisterDto.getStartDate()));
        }else{
            item.setStartDate(null);
        }

        log.info("productRegisterDto.getEndDate() : " + productRegisterDto.getEndDate());
        log.info(String.valueOf(productRegisterDto.getEndDate() != null));

        if(productRegisterDto.getEndDate() != null && !productRegisterDto.getEndDate().equals("")){
            item.setEndDate(UtilMethod.date.parse(productRegisterDto.getEndDate()));
        }else{
            item.setEndDate(null);
        }
//        item.setDisplayStatus(DisplayStatus.DISPLAY);
        item.setDisplay(true);
        return item;

    }



    //ItemImg
    public void processItemImg(ProductRegisterDto productRegisterDto, UtilMethod utilMethod, Item item) {

        if (productRegisterDto.getOption().equals("update")){
            List<Long> ordId = new ArrayList<>();
            List<Long> inputId = new ArrayList<>();

            // 기존 상품에 들어있는 이미지를 가지고 온다.
            item.getItemImg_list().forEach(itemImg -> ordId.add(itemImg.getId()));

            for(int i = 0 ; i < productRegisterDto.getMainImg().size() ; i++){
                ProductRegisterDto.MainImageDto mainImageDto = productRegisterDto.getMainImg().get(i);
                if(mainImageDto.getId() != null){
                    inputId.add(mainImageDto.getId());
                }
            }

//            productRegisterDto.getMainImg().forEach(itemImg -> inputId.add(itemImg.getId()));

            // 삭제된 건 제거한다.
            for (int i = ordId.size() -1 ; i >=0 ; i--){
                if (!inputId.contains(ordId.get(i))){
                    log.info("item.getItemImg_list().size() : " + item.getItemImg_list().size());
                    log.info("i : " + i);
                    item.getItemImg_list().remove(i);
                }
            }
//            item.getItemImg_list().clear();
        }


        for (int i = 0; i < productRegisterDto.getMainImg().size(); i++) {

            // 새로운 이미지일 때
            if(productRegisterDto.getMainImg().get(i).getId() == null){
                ItemImg itemImg = new ItemImg();
                String url = utilMethod.getImgUrl(productRegisterDto.getMainImg().get(i).getBase64Data(),
                        productRegisterDto.getMainImg().get(i).getFileName());

                itemImg.setImgUrl(url);
                itemImg.setItem(item);
                imgRepository.save(itemImg);
            }else{  // 기존 이미지일 때

                // 기존 이미지인데 base64Data가 없다면 db에 저장되어 있다.
                if (productRegisterDto.getMainImg().get(i).getBase64Data() == null && productRegisterDto.getOption().equals("create")){
//                if (productRegisterDto.getMainImg().get(i).getBase64Data() == null){
                    ItemImg itemImg = new ItemImg();
                    itemImg.setImgUrl(productRegisterDto.getMainImg().get(i).getImgUrl());
                    itemImg.setItem(item);
                    imgRepository.save(itemImg);
                }

                // 기존 이미지인데 Base64Data가 있다면 해당 이미지는 s3에 저장이 안되어 있다는 것이므로
                if (productRegisterDto.getMainImg().get(i).getBase64Data() !=null){
                    ItemImg itemImg = imgRepository.findById(productRegisterDto.getMainImg().get(i).getId()).orElseThrow(
                            () -> new CustomException(ErrorCode.IMAGE_NOT_FOUND)
                    );
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
    public void processItemTicket(ProductRegisterDto productRegisterDto, Item item)  {

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

                    itemTicketPriceRecode.setQuantity(prices.getQuantity());
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
                        if(prices.getStartDate() != null && !prices.getStartDate().equals("")){
                            startDateTemp = UtilMethod.date.parse(prices.getStartDate());
                        }else{
                            startDateTemp = null;
                        }


                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        if(prices.getEndDate() != null && !prices.getEndDate().equals("")){
                            endDateTemp = UtilMethod.date.parse(prices.getEndDate());
                        }else{
                            endDateTemp = null;
                        }

                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    startDate.add(startDateTemp);
                    endDate.add(endDateTemp);


                    datePoint.add(endDateTemp.getTime() - startDateTemp.getTime());

                });
                int count = datePoint.size();
                while (count-- != 0) {
                    if(datePoint.stream().min(Long::compareTo).isPresent()){
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
                }
                itemTicketRepository.save(itemTicket);
            }
        } else {

            //1. 입력된 티켓의 id를 저장

            List<Long> inputTicketId = new ArrayList<>();
            List<Long> oldTicketId = new ArrayList<>();

            productRegisterDto.getTicket().forEach(ticketDto -> inputTicketId.add(ticketDto.getId()));
            item.getItemTickets().forEach(itemTicket -> oldTicketId.add(itemTicket.getId()));


            item.getItemTickets().forEach(itemTicket -> {
                if (!inputTicketId.contains(itemTicket.getId())){
                    itemTicket.getItemTicketPrices().clear();
                    itemTicket.getItemTicketPriceRecodes().forEach(itemTicketPriceRecode -> itemTicketPriceRecode.setItemTicket(null));
                    itemTicket.setItem(null);

                }
            });

            for (int i = 0; i < productRegisterDto.getTicket().size(); i++) {
                if (productRegisterDto.getTicket().get(i).getId() !=null){
                    continue;
                }
                ItemTicket itemTicket = new ItemTicket();
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

                    ItemTicketPriceRecode itemTicketPriceRecode = prices.getId() == null ? new ItemTicketPriceRecode() : itemTicketPriceRecodeRepository.findById(prices.getId()).orElseThrow(() -> new RuntimeException("존재하지 않는 가격정보입니다."));

                    Date startDateTemp;
                    Date endDateTemp;

                    itemTicketPriceRecode.setStartDate(prices.getStartDate());
                    itemTicketPriceRecode.setEndDate(prices.getEndDate());
                    itemTicketPriceRecode.setItemTicket(itemTicket);
                    itemTicketPriceRecode.setQuantity(prices.getQuantity());
                    itemTicketPriceRecode.setMon(prices.getWeekdayPrices().getMon());
                    itemTicketPriceRecode.setTue(prices.getWeekdayPrices().getTue());
                    itemTicketPriceRecode.setWed(prices.getWeekdayPrices().getWed());
                    itemTicketPriceRecode.setThu(prices.getWeekdayPrices().getThu());
                    itemTicketPriceRecode.setFri(prices.getWeekdayPrices().getFri());
                    itemTicketPriceRecode.setSat(prices.getWeekdayPrices().getSat());
                    itemTicketPriceRecode.setSun(prices.getWeekdayPrices().getSun());
                    itemTicketPriceRecodeRepository.save(itemTicketPriceRecode);

                    try {
                        startDateTemp = UtilMethod.date.parse(prices.getStartDate());

                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        endDateTemp = UtilMethod.date.parse(prices.getEndDate());
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
        // 1. 기존에 있던 코스의 id중 입력된 코스의 id가 없는 id를 제거

        if (productRegisterDto.getOption().equals("update")) {
            List<Long> inputCourseId = new ArrayList<>();

            productRegisterDto.getCourse().forEach(courseDto -> {
                inputCourseId.add(courseDto.getId());
            });


            item.getItemCourses().forEach(courseId -> {
                if (!inputCourseId.contains(courseId.getId())) {
                    ItemCourse itemCourse = itemCourseRepository.findById(courseId.getId()).orElseThrow(() -> new RuntimeException("존재하지 않는 코스입니다."));
                    itemCourse.setItem(null);

                }
            });
        }

        for (int i = 0; i < productRegisterDto.getCourse().size(); i++) {
            ItemCourse itemCourse = null;

            if(productRegisterDto.getCourse().get(i).getId() == null || productRegisterDto.getOption().equals("create")){
                itemCourse = new ItemCourse();
            }else{
                Optional<ItemCourse> byId = itemCourseRepository.findById(productRegisterDto.getCourse().get(i).getId());
                if(!byId.isPresent()){
                    itemCourse = new ItemCourse();
                }else{
                    itemCourse = itemCourseRepository.findById(productRegisterDto.getCourse().get(i).getId()).orElseThrow(
                            () -> new CustomException(ErrorCode.COURSE_NOT_FOUND)
                    );
                }

            }

            String url = "";
            if(productRegisterDto.getCourse().get(i).getImage() != null){
                if (productRegisterDto.getCourse().get(i).getImage().getBase64Data() != null){
                    url = utilMethod.getImgUrl(productRegisterDto.getCourse().get(i).getImage().getBase64Data(),
                            productRegisterDto.getCourse().get(i).getImage().getFileName());
                    itemCourse.setImageUrl(url);
                }
            }

            itemCourse.setItem(item);
            itemCourse.setTitle(productRegisterDto.getCourse().get(i).getTitle());
            itemCourse.setTimeCost(productRegisterDto.getCourse().get(i).getTimeCost());
            itemCourse.setContent(productRegisterDto.getCourse().get(i).getContent());
            itemCourse.setSequenceId(productRegisterDto.getCourse().get(i).getSequenceId());
            itemCourse.setTimeCost(productRegisterDto.getCourse().get(i).getTimeCost());
            itemCourse.setDay(Math.toIntExact(productRegisterDto.getCourse().get(i).getDay()));

            // 코드 위도 경도 설정
            if(productRegisterDto.getCourse().get(i).getLocation() == null){
                itemCourse.setLatitude(null);
                itemCourse.setLongitude(null);
            }else{
                if(productRegisterDto.getCourse().get(i).getLocation().getLatitude() == null){
                    itemCourse.setLatitude(null);
                }else{
                    itemCourse.setLatitude(Double.valueOf(productRegisterDto.getCourse().get(i).getLocation().getLatitude()));
                }

                if(productRegisterDto.getCourse().get(i).getLocation().getLongitude() == null){
                    itemCourse.setLongitude(null);
                }else{
                    itemCourse.setLongitude(Double.valueOf(productRegisterDto.getCourse().get(i).getLocation().getLongitude()));
                }
            }

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
            findItemByCategories.add(new AdminPageResponse.findItemByCategory(data.getItemCode(),data.getTitle(),categoryNames,data.getAdmin() == null ? null : data.getAdmin().getAdminId(),data.getCreatedDate(),
                    data.getUpdateAdmin() == null ?null : data.getUpdateAdmin().getAdminId(),data.getUpdateAdmin() == null ? null : data.getModifiedDate()));
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
            findItemByCategories.add(new AdminPageResponse.findItemByCategory(data.getItemCode(),data.getTitle(),categoryNames,data.getAdmin() == null ? null : data.getAdmin().getAdminId(),data.getCreatedDate(),
                    data.getUpdateAdmin() == null ?null : data.getUpdateAdmin().getAdminId(),data.getUpdateAdmin() == null ? null : data.getModifiedDate()));
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
            findItemByCategories.add(new AdminPageResponse.findItemByCategory(data.getItemCode(),data.getTitle(),categoryNames,data.getAdmin() == null ? null : data.getAdmin().getAdminId(),data.getCreatedDate(),
                    data.getUpdateAdmin() == null ? null : data.getUpdateAdmin().getAdminId(),data.getUpdateAdmin() == null ? null : data.getModifiedDate()));
        });

        getItemByCategory.setData(findItemByCategories);
        return getItemByCategory;
    }

    @Transactional
    public void processDeleteItem(Long item_db_id) {
        Item item = itemRepository.findById(item_db_id).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

        item.getItemImg_list().clear();
        item.getItemHashTags().clear();
        item.getTargetUsers().clear();
        item.getItemIcon_list().clear();
        likeItemRepository.deleteByItem(item);
        item.getItemCourses().forEach(itemCourse -> {
            itemCourse.setItem(null);
        });
        item.getItemTickets().forEach(itemTicket -> {
            itemTicket.setItem(null);
        });
        item.getMainPages().forEach(mainPage -> {
            mainPage.setItem(null);
        });
        item.getItemReviews().forEach(
                itemReview -> {
                    itemReview.setItem(null);
                }
        );
        itemRepository.delete(item);
    }

    public ProductRegisterDto processGetItemDetail(Long item_db_id) {
        Item item = itemRepository.findById(item_db_id).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

        ProductRegisterDto productRegisterDto = new ProductRegisterDto();
        productRegisterDto.setId(item.getId());
        productRegisterDto.setProductId(item.getItemCode());

        //프론트 미구현

//        List<ProductRegisterDto.ItemIcon> itemIcons = new ArrayList<>();
//        item.getItemIcon_list().forEach(itemIcon -> {
//            ProductRegisterDto.ItemIcon itemIconInput = new ProductRegisterDto.ItemIcon();
//            itemIconInput.setIconId(itemIcon.getIcon().getId());
//            itemIconInput.setText(itemIcon.getText());
//            itemIcons.add(itemIconInput);
//        });
//        productRegisterDto.setItemIcon(itemIcons);

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

        ProductRegisterDto.accessData accessData = new ProductRegisterDto.accessData();
        List<String> userEmail = new ArrayList<>();

//        if (item.getGrade() != null){
//
//        }
        accessData.setAccessibleTier(item.getGrade() == null ? null : item.getGrade().name());

        if (item.getTargetUsers() != null){
            item.getTargetUsers().forEach(user ->{
                userEmail.add(user.getUser() == null ? null : user.getUser().getEmail());
            });
            accessData.setAccessibleUserList(userEmail);
        }
        productRegisterDto.setAccessAuthority(accessData);


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
            List<ProductRegisterDto.TicketDto.PriceListDto> priceListDtos = new ArrayList<>();
            itemTicket.getItemTicketPriceRecodes().forEach(itemTicketPriceRecode -> {
                ProductRegisterDto.TicketDto.PriceListDto priceRecode = new ProductRegisterDto.TicketDto.PriceListDto();
                priceRecode.setId(itemTicketPriceRecode.getId());
                priceRecode.setStartDate(itemTicketPriceRecode.getStartDate());
                priceRecode.setEndDate(itemTicketPriceRecode.getEndDate());
                priceRecode.setQuantity(itemTicketPriceRecode.getQuantity());
                ProductRegisterDto.TicketDto.PriceListDto.WeekdayPrices weekdayPrices = new ProductRegisterDto.TicketDto.PriceListDto.WeekdayPrices();
                weekdayPrices.setMon(itemTicketPriceRecode.getMon());
                weekdayPrices.setTue(itemTicketPriceRecode.getTue());
                weekdayPrices.setWed(itemTicketPriceRecode.getWed());
                weekdayPrices.setThu(itemTicketPriceRecode.getThu());
                weekdayPrices.setFri(itemTicketPriceRecode.getFri());
                weekdayPrices.setSat(itemTicketPriceRecode.getSat());
                weekdayPrices.setSun(itemTicketPriceRecode.getSun());
                priceRecode.setWeekdayPrices(weekdayPrices);
                priceListDtos.add(priceRecode);
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
            courseDto.setDay(Long.valueOf(itemCourse.getDay()));

            if(itemCourse.getLatitude() != null && itemCourse.getLongitude() != null){
                courseDto.setLocation(new ProductRegisterDto.CourseDto.LocationDto(itemCourse.getLatitude().toString(),itemCourse.getLongitude().toString()));
            }else{
                courseDto.setLocation(new ProductRegisterDto.CourseDto.LocationDto(null,null));
            }

            ProductRegisterDto.CourseDto.CourseImageDto courseImageDto = new ProductRegisterDto.CourseDto.CourseImageDto();
            courseImageDto.setImgUrl(itemCourse.getImageUrl());
            courseDto.setImage(courseImageDto);
            courseDtos.add(courseDto);
        });

        productRegisterDto.setCourse(courseDtos);
        productRegisterDto.setExtraInfo(item.getContent_2());
        productRegisterDto.setAdmin(item.getAdmin() == null ? null : item.getAdmin().getAdminId());
        productRegisterDto.setUpdateAdmin(item.getUpdateAdmin() == null ? null : item.getUpdateAdmin().getAdminId());
        productRegisterDto.setStartDate(item.getStartDate() == null ? null : String.valueOf(item.getStartDate()));
        productRegisterDto.setEndDate(item.getEndDate() == null ? null : String.valueOf(item.getEndDate()));
        productRegisterDto.setDuration(item.getDuration() == null ? null : String.valueOf(item.getDuration()));
        productRegisterDto.setLanguage(item.getLanguage());
        productRegisterDto.setStartPoint(item.getStartPoint());
        productRegisterDto.setRunningTime(item.getRunningTime());
        productRegisterDto.setActivityIntensity(item.getActivityIntensity());

        ProductRegisterDto.accessData accessAuthority = new ProductRegisterDto.accessData();
        List<TargetUser> users = item.getTargetUsers();
        accessAuthority.setAccessibleTier(item.getGrade() == null ? null : item.getGrade().name());
        if (!users.isEmpty()){
            List<String> targetUsers = new ArrayList<>();
            users.forEach(targetUser -> {
                if(!(targetUser.getUser() == null)){
                    targetUsers.add(targetUser.getUser() == null ? null : targetUser.getUser().getEmail());
                }
            });

            accessAuthority.setAccessibleUserList(targetUsers);

        }
        productRegisterDto.setAccessAuthority(accessAuthority);
        productRegisterDto.setStartPrice(item.getStartPrice());
        productRegisterDto.setOption("update"); //create

        if(item.getGuide() == null){
            productRegisterDto.setGuide_code(null);
            productRegisterDto.setGuide_comment(null);
        }else{
            productRegisterDto.setGuide_code(item.getGuide().getCode());
            productRegisterDto.setGuide_comment(item.getGuide_comment());
        }

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
        return new ResponseTemplate(new ItemResponse.getAllItemId(new ArrayList<>(allItemId)));
    }

@Transactional
    public void changeItemStatus(AdminPageRequest.changeDisplayStatus request,Long item_db_id){
        Item item = itemRepository.findById(item_db_id).orElseThrow(
                () -> new CustomException(ErrorCode.ITEM_NOT_FOUND)
        );

        if(request.getDisplay_true() == null){
            item.setDisplay(false);
        }

        if (request.getDisplay_true()){
//            item.setDisplayStatus(DisplayStatus.DISPLAY);
            item.setDisplay(true);
        } else{
//            item.setDisplayStatus(DisplayStatus.HIDDEN);
            item.setDisplay(false);
        }

        itemRepository.save(item);
    }


    public AdminPageResponse.getItemByDisplayStatus processGetAllDisplayItems(int limit, int sqlPage,String status) {
        Pageable pageable = PageRequest.of(Math.toIntExact(sqlPage), Math.toIntExact(limit), Sort.by("id").ascending());
        Page<Item> allDisplayItems = null;
        if (status.equals("DISPLAY")){
            allDisplayItems = itemRepository.findAllByDisplay(true, pageable);
        } else{
            allDisplayItems = itemRepository.findAllByDisplay(false, pageable);
        }
        AdminPageResponse.getItemByDisplayStatus getItemByDisplayStatus = new AdminPageResponse.getItemByDisplayStatus();
        List<AdminPageResponse.getItemsByDisplayStat> itemsByDisplayStats = new ArrayList<>();
        getItemByDisplayStatus.setPageCount((long) allDisplayItems.getTotalPages());

        allDisplayItems.getContent().forEach(item ->{
            AdminPageResponse.getItemsByDisplayStat itemsByDisplayStat = new AdminPageResponse.getItemsByDisplayStat();
            itemsByDisplayStat.setItem_db_id(item.getId());
            itemsByDisplayStat.setItemCode(item.getItemCode());
            itemsByDisplayStat.setTitle(item.getTitle());
            if (item.getItemImg_list().isEmpty()){
                itemsByDisplayStat.setImgUrl(null);
            } else{
                itemsByDisplayStat.setImgUrl(item.getItemImg_list().get(0).getImgUrl());
            }
            itemsByDisplayStats.add(itemsByDisplayStat);
        });

        getItemByDisplayStatus.setData(itemsByDisplayStats);


        return getItemByDisplayStatus;
    }


    public Object getPageCount(int limit) {
        return (int) Math.ceil(itemRepository.count() / (double) limit);
    }
}
