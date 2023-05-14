package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.domain.Advertisement;
import com.example.amusetravelproejct.domain.Alarm;
import com.example.amusetravelproejct.dto.request.AdminPageRequest;
import com.example.amusetravelproejct.dto.response.AdminPageResponse;
import com.example.amusetravelproejct.repository.AlarmRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AlarmService {
    private final AlarmRepository alarmRepository;

    public AdminPageResponse.noticeRegister processRegisterNotice(AdminPageRequest.noticeRegister noticeRegisterDto, AdminService adminService, AlarmService alarmService) {
        Alarm alarm = new Alarm();
        alarm.setTitle(noticeRegisterDto.getTitle());
        alarm.setContent(noticeRegisterDto.getContent());
        alarm.setAdmin(adminService.getAdminByEmail(noticeRegisterDto.getCreatedBy()).get());
        alarm.setContent(noticeRegisterDto.getContent());
        alarm = alarmRepository.save(alarm);


        return new AdminPageResponse.noticeRegister(alarm.getId(), alarm.getTitle(), alarm.getContent(),alarm.getCreatedAt() , alarm.getAdmin().getEmail());
    }

    public AdminPageResponse.noticeEdit processEditNotice(AdminPageRequest.noticeEdit noticeEditDto,AdminService adminService){
        Alarm alarm = alarmRepository.findById(noticeEditDto.getId()).get();
        alarm.setTitle(noticeEditDto.getTitle());
        alarm.setContent(noticeEditDto.getContent());
        alarm.setUpdateAdmin(adminService.getAdminByEmail(noticeEditDto.getUpdatedBy()).get());
        alarm = alarmRepository.save(alarm);

        return new AdminPageResponse.noticeEdit(alarm.getId(), alarm.getTitle(), alarm.getContent(), alarm.getCreatedAt(),alarm.getAdmin().getEmail(), alarm.getUpdatedAt(),alarm.getUpdateAdmin().getEmail());
    }



    public AdminPageResponse.noticeEdit processGetNoticeDetail(Long id) {

        Alarm alarm = alarmRepository.findById(id).orElseThrow(()->new CustomException(ErrorCode.ITEM_NOT_FOUND));

        return new AdminPageResponse.noticeEdit(alarm.getId(), alarm.getTitle(), alarm.getContent(), alarm.getCreatedAt(),alarm.getAdmin().getEmail(), alarm.getUpdateAdmin() == null ? null : alarm.getUpdatedAt() ,alarm.getUpdateAdmin() == null ? "" : alarm.getUpdateAdmin().getEmail());
    }

    public List<AdminPageResponse.noticeList> processGetAllNotices(Long offset, int limit, int page) {

        int pageSize = limit;
        Long offsetCount = offset;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").ascending());
        List<Alarm> notices = alarmRepository.findAdsByOffsetAndLimitCount(offsetCount, pageable);


        List<AdminPageResponse.noticeList> noticeListList = new ArrayList<>();
        for (Alarm notice : notices) {
            noticeListList.add(new AdminPageResponse.noticeList(notice.getId(),
                    notice.getTitle(),
                    notice.getCreatedAt(), notice.getAdmin().getEmail(),
                    notice.getUpdateAdmin() == null ? null : notice.getUpdatedAt(),
                    notice.getUpdateAdmin() == null ? "" : notice.getUpdateAdmin().getEmail()));
        }


        return noticeListList;
    }
    public int getPageCount(int limit) {
        int pageSize = limit;
        int totalPage = (int) Math.ceil(alarmRepository.count() / (double) pageSize);
        return totalPage;
    }
}
