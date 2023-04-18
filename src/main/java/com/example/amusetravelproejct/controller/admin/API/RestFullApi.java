package com.example.amusetravelproejct.controller.admin.API;

import com.example.amusetravelproejct.controller.admin.DTO.ItemInfoDto;
import com.example.amusetravelproejct.domain.Category;
import com.example.amusetravelproejct.domain.Img;
import com.example.amusetravelproejct.domain.Iteminfo;
import com.example.amusetravelproejct.domain.person_enum.ImgRole;
import com.example.amusetravelproejct.repository.CategoryRepository;
import com.example.amusetravelproejct.repository.ImgRepository;
import com.example.amusetravelproejct.repository.IteminfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestFullApi {

    @Autowired
    private IteminfoRepository iteminfoRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    ImgRepository imgRepository;


    @GetMapping("/getItem")
    public ItemInfoDto getItem(@Param("id") String id){

        //get item info from DB
        Iteminfo iteminfo = iteminfoRepository.findByItemCode(id);

        //response
        ItemInfoDto itemInfoDto = new ItemInfoDto();
        itemInfoDto.setItemCode(iteminfo.getItemCode());
        itemInfoDto.setCategory(iteminfo.getCategory().getId());
        itemInfoDto.setItemTitle(iteminfo.getItemTitle());


        for(Img img : iteminfo.getImgs()){
            if (img.getImgRole().equals(ImgRole.THUMBNAIL)){
                itemInfoDto.setThumbnailImgUrl(img.getImgUrl());
            }
        }

        itemInfoDto.setLocation(iteminfo.getCityLocation());
        itemInfoDto.setStartingPrice(iteminfo.getStartingPrice());

        return itemInfoDto;
    }

    @PostMapping("/createItem")
    public ResponseEntity<String> createItem(@RequestBody ItemInfoDto itemInfoDto){
        Iteminfo iteminfo = new Iteminfo();
        Img img = new Img();

        iteminfo.setCategory(categoryRepository.findById(itemInfoDto.getCategory()).get());

        iteminfo.setCityLocation(itemInfoDto.getLocation());

        if (iteminfo.getCityLocation().equals("강원도")){
            iteminfo.setItemCode("033-1");
        }

        iteminfo.setItemTitle(itemInfoDto.getItemTitle());
        iteminfo.setStartingPrice(itemInfoDto.getStartingPrice());
        iteminfoRepository.save(iteminfo);


        // 1. Img 파일(base64, multipart)로 정보를 받는다
        // 2. Img 정보를 S3에 보내고 S3에서 받은 url을 DB에 저장한다.

        img.setImgUrl("https://s3.ap-northeast-2.amazonaws.com/amuse-travel-project/IMG_20200831_162000.jpg");
        img.setImgRole(ImgRole.THUMBNAIL);
        img.setIteminfo(iteminfo);

        imgRepository.save(img);



        return ResponseEntity.ok("Item created successfully!");
    }

    @PostMapping("/updateItem")

    public ResponseEntity<String> updateItem(@RequestBody ItemInfoDto itemInfoDto){
        Iteminfo iteminfo = iteminfoRepository.findByItemCode(itemInfoDto.getItemCode());
        List<Img> imgs = iteminfo.getImgs();

        iteminfo.setCategory(categoryRepository.findById(itemInfoDto.getCategory()).get());
        iteminfo.setCityLocation(itemInfoDto.getLocation());
        iteminfo.setItemTitle(itemInfoDto.getItemTitle());
        iteminfo.setStartingPrice(itemInfoDto.getStartingPrice());
        iteminfoRepository.save(iteminfo);

        for (Img img : imgs){
           if (!img.getImgUrl().equals(itemInfoDto.getThumbnailImgUrl())){
               imgRepository.delete(img);

               // S3 Img 삭제 필요


               // 1. Img 파일(base64, multipart)로 정보를 받는다
               // 2. Img 정보를 S3에 보내고 S3에서 받은 url을 DB에 저장한다.

               img.setImgUrl("https://s3.ap-northeast-2.amazonaws.com/amuse-travel-project/JPG_123213_12322.jpg");
               img.setImgRole(ImgRole.THUMBNAIL);
               img.setIteminfo(iteminfo);

               imgRepository.save(img);
           }
        }



        return ResponseEntity.ok("Item updated successfully!");
    }

    @GetMapping("/deleteItem")
    public ResponseEntity<String> deleteItem(@Param("itemCode") String itemCode){
        Iteminfo iteminfo = iteminfoRepository.findByItemCode(itemCode);
        System.out.println(iteminfo.toString());
        iteminfoRepository.delete(iteminfo);
        return ResponseEntity.ok("Item deleted successfully!");
    }


    @GetMapping("/readCategory")
    public ResponseEntity<String> readCategory(){


        List<Category> category = categoryRepository.findAll();
        String result = "";
        for (Category temp : category){
            result += temp.getCategoryName() + " ";
        }
        return ResponseEntity.ok(result);
    }


    @GetMapping("/createCategory")
    public ResponseEntity<String> createCategory(@Param("categoryName") String categoryName){

        Category category1 = new Category();
        category1.setCategoryName(categoryName);
        categoryRepository.save(category1);
        return ResponseEntity.ok("create category success!!");
    }

    @GetMapping("/updateCategory")
    public ResponseEntity<String> updateCategory(@Param("id") Long id, @Param("categoryName") String categoryName){

        Category category1 = categoryRepository.findById(id).get();
        category1.setCategoryName(categoryName);
        categoryRepository.save(category1);
        return ResponseEntity.ok("update category success!!");
    }

    @GetMapping("/deleteCategory")
    public ResponseEntity<String> deleteCategory(@Param("id") Long id){

        Category category1 = categoryRepository.findById(id).get();
        categoryRepository.delete(category1);
        return ResponseEntity.ok("delete category success!!");
    }

}
