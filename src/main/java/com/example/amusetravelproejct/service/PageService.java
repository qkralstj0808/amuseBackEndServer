package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.config.util.UtilMethod;
import com.example.amusetravelproejct.domain.*;
import com.example.amusetravelproejct.dto.request.AdminPageRequest;
import com.example.amusetravelproejct.dto.response.AdminPageResponse;
import com.example.amusetravelproejct.repository.AdminRepository;
import com.example.amusetravelproejct.repository.CategoryPageComponentRepository;
import com.example.amusetravelproejct.repository.CategoryRepository;
import com.example.amusetravelproejct.repository.PageComponentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PageService {

    private final AdminService adminService;

    private final CategoryRepository categoryRepository;

    private final AdminRepository adminRepository;

    private final PageComponentRepository pageComponentRepository;

    private final CategoryPageComponentRepository categoryPageComponentRepository;

    @Transactional
    public ResponseTemplate<String> createPage(
            AdminPageRequest.createPage request, UtilMethod utilMethod, Admin admin) {

        Category category = new Category();
        Category findCategory = categoryRepository.findByCategoryName(request.getName());

        if(findCategory != null){
            throw new CustomException(ErrorCode.CATEGORY_EXIT);
        }
//        Admin admin = adminRepository.findByAdminId(findUser.getUserId()).orElseThrow(
//                () -> new CustomException(ErrorCode.ADMIN_NOT_FOUND)
//        );


        category.setCategory_name(request.getName());
        category.setSequence(request.getSequence());
        category.setAdmin(admin);
        category.setMainDescription(request.getMainDescription());
        category.setSubDescription(request.getSubDescription());
        category.setImgUrl(utilMethod.getImgUrl(request.getBase64Data(), request.getFileName()));

        List<Long> newPageComponentId = new ArrayList<>();
        List<AdminPageRequest.PageComponentInfo> pageComponentInfos = request.getPageComponentInfos();

        for (AdminPageRequest.PageComponentInfo pageComponentInfo : pageComponentInfos) {
            newPageComponentId.add(pageComponentInfo.getComponentId());
        }

        List<PageComponent> pageComponentList = pageComponentRepository.findListByPageComponentIdList(newPageComponentId);

        List<CategoryPageComponent> categoryPageComponentArrayList = new ArrayList<>();

        for(PageComponent pageComponent:pageComponentList){
            CategoryPageComponent categoryPageComponent = new CategoryPageComponent();
            categoryPageComponent.setPageComponent(pageComponent);
            categoryPageComponent.setCategory(category);
            categoryPageComponentArrayList.add(categoryPageComponent);
        }
        category.setCategoryPageComponents(categoryPageComponentArrayList);

        categoryPageComponentRepository.saveAll(categoryPageComponentArrayList);

        category = categoryRepository.save(category);
        return new ResponseTemplate("페이지 생성 완료하였습니다.");

    }

    @Transactional
    public ResponseTemplate<AdminPageResponse.updatePage> updatePage(Long page_id, AdminPageRequest.updatePage request, UtilMethod utilMethod, Admin admin) {
        Category findCategory = categoryRepository.findById(page_id).orElseThrow(
                () -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND)
        );

        if(request.getDisable() == null){
            request.setDisable(false);
        }

        // 만약 disable을 true로 한다면 어차피 사용하지 않을 페이지라서 다른 업데이트 사항들을 반영 안했습니다
        if(request.getDisable()){
            List<Category> lessSequenceCategoryList = categoryRepository.findgreaterSequence(findCategory.getSequence());
            findCategory.setSequence(0L);
            for(Category category:lessSequenceCategoryList){
                category.setSequence(category.getSequence()-1);
                category.setUpdateAdmin(admin);
            }
            categoryRepository.saveAll(lessSequenceCategoryList);
//            categoryRepository.saveAndFlush(findCategory);
//            return new ResponseTemplate(new AdminPageResponse.updatePage(
//                    findCategory.getId(),findCategory.getDisable(),findCategory.getCategory_name(),findCategory.getImgUrl(),
//                    findCategory.getSequence(),findCategory.getMainDescription(),
//                    findCategory.getSubDescription(),findCategory.getCreatedDate(),
//                    findCategory.getAdmin().getName(),findCategory.getModifiedDate(),admin.getName(),
//                    null));
        }else{
            findCategory.setSequence(request.getSequence());
        }

        // admin 수정
        findCategory.setUpdateAdmin(admin);
        findCategory.setDisable(request.getDisable());

        // 카테고리 이름 수정
        if(request.getName() != null){
            findCategory.setCategory_name(request.getName());
        }

        // 카테고리 메인 내용 수정
        if (request.getMainDescription() != null){
            findCategory.setMainDescription(request.getMainDescription());
        }

        // 카테고리 sub 내용 수정
        if (request.getSubDescription() != null){
            findCategory.setSubDescription(request.getSubDescription());
        }

        // 카테고리 이미지 수정
        if (request.getBase64Data() != null && request.getFileName() != null && !request.getFileName().equals("")){
            log.info("fildName : " + request.getFileName());
            log.info("getBase64Data : " + request.getBase64Data());
            findCategory.setImgUrl(utilMethod.getImgUrl(request.getBase64Data(), request.getFileName()));
        }

        // 카테고리에서 수정한 component 담을 list wjddml
        List<Long> newPageComponentId = new ArrayList<>();

        // 기존 카테고리에 포함된 component 불러온다.
        List<CategoryPageComponent> categoryPageComponents = findCategory.getCategoryPageComponents();

        // 수정할 component가 있다면
        if(request.getPageComponentInfos() != null && request.getPageComponentInfos().size() != 0){
            categoryPageComponents.clear();


            List<AdminPageRequest.PageComponentInfo> pageComponentInfos = request.getPageComponentInfos();

            for (AdminPageRequest.PageComponentInfo pageComponentInfo : pageComponentInfos) {
                newPageComponentId.add(pageComponentInfo.getComponentId());
            }

            List<PageComponent> pageComponentList = pageComponentRepository.findListByPageComponentIdList(newPageComponentId);

            List<CategoryPageComponent> categoryPageComponentArrayList = new ArrayList<>();

            for(PageComponent pageComponent:pageComponentList){
                CategoryPageComponent categoryPageComponent = new CategoryPageComponent();
                categoryPageComponent.setPageComponent(pageComponent);
                categoryPageComponent.setCategory(findCategory);
                categoryPageComponentArrayList.add(categoryPageComponent);
            }

            categoryPageComponents.addAll(categoryPageComponentArrayList);
        }


        categoryRepository.saveAndFlush(findCategory);

        return new ResponseTemplate(new AdminPageResponse.updatePage(
                findCategory.getId(),findCategory.getDisable(),findCategory.getCategory_name(),findCategory.getImgUrl(),
                findCategory.getSequence(),findCategory.getMainDescription(),
                findCategory.getSubDescription(),findCategory.getCreatedDate(),
                findCategory.getAdmin() == null ? null : findCategory.getAdmin().getAdminId(),
                findCategory.getModifiedDate(),
                findCategory.getUpdateAdmin() == null ? null : findCategory.getUpdateAdmin().getAdminId(),
                findCategory.getCategoryPageComponents().stream().map(
                        CategoryPageComponent::getPageComponent).map(
                        pageComponent -> new AdminPageResponse.PageComponentInfo(
                                pageComponent.getId(),
                                pageComponent.getType(),
                                pageComponent.getTitle(),
                                pageComponent.getPcBannerUrl(),
                                pageComponent.getPcBannerLink(),
                                pageComponent.getMobileBannerUrl(),
                                pageComponent.getMobileBannerLink(),
                                pageComponent.getContent(),
                                pageComponent.getAdmin() != null ? pageComponent.getAdmin().getAdminId() : null,
                                pageComponent.getUpdateAdmin() != null ? pageComponent.getUpdateAdmin().getAdminId() : null
                        )
                ).collect(Collectors.toList())
                ));
    }


    public ResponseTemplate<AdminPageResponse.getPage> getPage(Long page_id) {
        Category findCategory = categoryRepository.findById(page_id).orElseThrow(
                () -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND)
        );

        List<CategoryPageComponent> categoryPageComponents = findCategory.getCategoryPageComponents();

        List<PageComponent> pageComponents = new ArrayList<>();
        for(CategoryPageComponent categoryPageComponent :categoryPageComponents){
            pageComponents.add(categoryPageComponent.getPageComponent());
        }

        return new ResponseTemplate(new AdminPageResponse.getPage(
                findCategory.getId(),findCategory.getDisable(),findCategory.getCategory_name(),findCategory.getImgUrl(),
                findCategory.getSequence(),findCategory.getMainDescription(),
                findCategory.getSubDescription(),findCategory.getCreatedDate(),
                findCategory.getAdmin() == null ? null : findCategory.getAdmin().getAdminId(),
                findCategory.getModifiedDate(),
                findCategory.getUpdateAdmin() == null ? null : findCategory.getUpdateAdmin().getAdminId(),
                pageComponents.stream().map(
                        pageComponent -> new AdminPageResponse.PageComponentInfo(
                                pageComponent.getId(),
                                pageComponent.getType(),
                                pageComponent.getTitle(),
                                pageComponent.getPcBannerUrl(),
                                pageComponent.getPcBannerLink(),
                                pageComponent.getMobileBannerUrl(),
                                pageComponent.getMobileBannerLink(),
                                pageComponent.getContent(),
                                pageComponent.getAdmin()== null ? null : pageComponent.getAdmin().getAdminId(),
                                pageComponent.getUpdateAdmin() == null ? null : pageComponent.getUpdateAdmin().getAdminId()
                        )
                ).collect(Collectors.toList())));
    }

    public ResponseTemplate<AdminPageResponse.getAllPage> getAllPage(Boolean disable) {

        List<Category> allCategory = categoryRepository.findAllByDisable(disable);

        return new ResponseTemplate(allCategory.stream().map(
                category -> new AdminPageResponse.getAllPage(
                        category.getId(),
                        category.getDisable(),
                        category.getCategory_name(),
                        category.getImgUrl(),
                        category.getSequence(),
                        category.getMainDescription(),
                        category.getSubDescription(),
                        category.getCreatedDate(),
                        category.getAdmin()== null ? null : category.getAdmin().getAdminId(),
                        category.getModifiedDate(),
                        category.getUpdateAdmin() == null ? null : category.getUpdateAdmin().getAdminId(),
                        category.getCategoryPageComponents().stream().map(
                                categoryPageComponent -> new AdminPageResponse.PageComponentId(categoryPageComponent.getPageComponent().getId())
                        ).collect(Collectors.toList())
                )
        ));
    }

    @Transactional
    public ResponseTemplate<String> deletePage(Long page_id) {
        Category findCategory = categoryRepository.findById(page_id).orElseThrow(
                () -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND)
        );

        categoryRepository.delete(findCategory);
        return new ResponseTemplate("삭제 완료되었습니다.");
    }
}
