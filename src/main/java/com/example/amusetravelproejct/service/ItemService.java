package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.config.util.UtilMethod;
import com.example.amusetravelproejct.domain.*;
import com.example.amusetravelproejct.dto.request.ProductRegisterDto;
import com.example.amusetravelproejct.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.util.*;

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
    ObjectMapper objectMapper = new ObjectMapper();

    static String bucketName = "amuse-img";

    //Admin
    public Optional<Admin> getAdminByEmail(String email) {
        return Optional.ofNullable(adminRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Admin not found")));
    }



    //Item
    @Transactional
    public Item processItem(ProductRegisterDto productRegisterDto) throws ParseException {
        Item item = new Item();
        itemRepository.save(item);
        item.setItemCode(productRegisterDto.getProductId());
        item.setTitle(productRegisterDto.getTitle());
        List<String> hashTags = productRegisterDto.getCategory();


        hashTags.forEach(data ->{
            ItemHashTag itemHashTag = new ItemHashTag();
            itemHashTag.setItem(item);
            itemHashTag.setHash_tag(data);
            itemHashTagRepository.save(itemHashTag);
        });
        item.setCountry(productRegisterDto.getLocation().getCountry());
        item.setCity(productRegisterDto.getLocation().getCity());
        item.setContent_1(productRegisterDto.getMainInfo());
        item.setContent_2(productRegisterDto.getExtraInfo());
        item.setAdmin(getAdminByEmail(productRegisterDto.getAdmin()).get());
        item.setStartPrice(productRegisterDto.getStartPrice());
        item.setDuration(productRegisterDto.getDuration().intValue());
        item.setStartDate(UtilMethod.date.parse(productRegisterDto.getStartDate()));
        item.setEndDate(UtilMethod.date.parse(productRegisterDto.getEndDate()));
        return item;
    }


    //ItemImg
    public void processItemImg(ProductRegisterDto productRegisterDto, UtilMethod utilMethod, Item item){
        for (int i = 0; i < productRegisterDto.getMainImg().size(); i++){
            ItemImg itemImg = new ItemImg();
            String url = utilMethod.getImgUrl(productRegisterDto.getMainImg().get(i).getBase64Data(),
                    productRegisterDto.getMainImg().get(i).getFileName());

            itemImg.setImgUrl(url);
            itemImg.setItem(item);
            imgRepository.save(itemImg);
        }
    }

    //ItemTicket
    public void processItemTicket(ProductRegisterDto productRegisterDto ,Item item) throws ParseException {
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
                            itemTicketPrice.setPrice(Long.valueOf(weekDayPrices.get(UtilMethod.day[startDateTemp.getDay()])));
                            itemTicketPrice.setStartDate(startDateTemp.toString());
                            itemTicketPrice.setItemTicket(itemTicket);
                            itemTicketPriceRepository.save(itemTicketPrice);
                        }
                        startDateTemp.setTime(startDateTemp.getTime() + (1000 * 60 * 60 * 24));     //하루 더하기
                    }
                }else{
                    Date startDateTemp = new Date(startDate.get(index).getTime());
                    while(startDateTemp.getTime() <= endDate.get(index).getTime()){
                        if (!(Long.valueOf(weekDayPrices.get(UtilMethod.day[startDateTemp.getDay()])) == 0) && dateSet.add(startDateTemp.getTime())){
                            ItemTicketPrice itemTicketPrice = new ItemTicketPrice();
                            itemTicketPrice.setPrice(Long.valueOf(weekDayPrices.get(UtilMethod.day[startDateTemp.getDay()])));
                            itemTicketPrice.setStartDate(startDateTemp.toString());
                            itemTicketPrice.setItemTicket(itemTicket);
                            itemTicketPrices.add(itemTicketPrice);
                            itemTicketPriceRepository.save(itemTicketPrice);
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
                            itemTicketPriceRepository.save(itemTicketPrice);
                        }
                        startDateTemp.setTime(startDateTemp.getTime() + (1000 * 60 * 60 * 24));     //하루 더하기
                    }
                }
                datePoint.set(index,Long.MAX_VALUE);
            }
            itemTicketRepository.save(itemTicket);
        }
    }




    //ItemCourse
    public void processItemCourse(ProductRegisterDto productRegisterDto,UtilMethod utilMethod,Item item){
        for(int i = 0; i < productRegisterDto.getCourse().size(); i++){
            ItemCourse itemCourse = new ItemCourse();
            itemCourse.setItem(item);
            itemCourse.setTitle(productRegisterDto.getCourse().get(i).getTitle());
            itemCourse.setTimeCost(productRegisterDto.getCourse().get(i).getTimeCost());
            itemCourse.setContent(productRegisterDto.getCourse().get(i).getContent());
            itemCourse.setSequenceId(Long.valueOf(i));

            String url = utilMethod.getImgUrl(productRegisterDto.getCourse().get(i).getImage().getBase64Data(),
                    productRegisterDto.getCourse().get(i).getImage().getFileName());

            itemCourse.setImageUrl(url);
            itemCourse.setTimeCost(productRegisterDto.getCourse().get(i).getTimeCost());
            itemCourseRepository.save(itemCourse);
        }
    }
}
