package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.domain.Item;
import com.example.amusetravelproejct.domain.LikeItem;
import com.example.amusetravelproejct.domain.User;
import com.example.amusetravelproejct.dto.response.MyPageResponse;
import com.example.amusetravelproejct.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;

    private final UserService userService;

    public ResponseTemplate<MyPageResponse.getLikeItems> getLikeItems(User findUser) {

        List<Item> findLikeItems = findUser.getLikeItems().stream().map(LikeItem::getItem).collect(Collectors.toList());

        List<MyPageResponse.ItemInfo> itemInfoList = new ArrayList<>();
        for (Item likeItem : findLikeItems) {
            String item_img = null;

            if (likeItem.getItemImg_list().size() != 0) {
                item_img = likeItem.getItemImg_list().get(0).getImgUrl();
            }
            itemInfoList.add(new MyPageResponse.ItemInfo(likeItem.getId(), likeItem.getItemCode(),
                    item_img, likeItem.getTitle(), likeItem.getCountry(), likeItem.getCity(), likeItem.getDuration(),
                    likeItem.getLike_num(), likeItem.getStartPrice()));
        }

        return new ResponseTemplate(new MyPageResponse.getLikeItems(itemInfoList));
    }
}
