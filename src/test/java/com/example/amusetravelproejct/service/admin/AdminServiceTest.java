package com.example.amusetravelproejct.service.admin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.domain.Admin;
import com.example.amusetravelproejct.dto.request.AuthRequest;
import com.example.amusetravelproejct.dto.response.AdminResponse;
import com.example.amusetravelproejct.dto.response.AdminResponse.getAllAccountsId;
import com.example.amusetravelproejct.oauth.entity.UserPrincipal;
import com.example.amusetravelproejct.repository.AdminRepository;
import com.example.amusetravelproejct.service.AdminService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
public class AdminServiceTest {
    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void signUp() {
        // given
        String adminId = "admin";
        String password = "password";

        // when
        adminService.createAdmin(adminId, password);

        // then
        Admin admin = adminRepository.findByAdminId(adminId)
            .orElseThrow();
        Assertions.assertThat(admin).isNotNull();
    }

//    @Test
//    public void testGetAdminByAdminId() {
//        String adminId = "admin123";
//        Admin admin = Admin.builder().adminId(adminId).password("password123").build();
//        when(adminRepository.findByAdminId(adminId)).thenReturn(Optional.of(admin));
//
//        Optional<Admin> result = adminService.getAdminByAdminId(adminId);
//
//        assertTrue(result.isPresent());
//        assertEquals(adminId, result.get().getAdminId());
//    }
//
//    @Test
//    public void testCreateAdmin() {
//        String adminId = "admin123";
//        String password = "password123";
//        Admin admin = Admin.builder().adminId(adminId).password(password).build();
//
//        adminService.createAdmin(adminId, password);
//
//        verify(adminRepository, times(1)).save(admin);
//    }
//
//    @Test
//    public void testGetAdminPrincipal() {
//        UserPrincipal userPrincipal = new UserPrincipal("admin123");
//        String adminId = userPrincipal.getUserId();
//        Admin admin = Admin.builder().adminId(adminId).password("password123").build();
//        when(adminRepository.findByAdminId(adminId)).thenReturn(Optional.of(admin));
//
//        Admin result = adminService.getAdminPrincipal(userPrincipal);
//
//        assertNotNull(result);
//        assertEquals(adminId, result.getAdminId());
//    }
//
//    @Test
//    public void testGetAllAccountsId() {
//        List<Admin> adminList = Collections.singletonList(Admin.builder().adminId("admin123").password("password123").build());
//        when(adminRepository.findAll()).thenReturn(adminList);
//
//        ResponseTemplate<getAllAccountsId> response = adminService.getAllAccountsId();
//
//        assertNotNull(response);
//        assertNotNull(response.getData());
//        assertEquals(1, response.getData().getAccounts().size());
//        assertEquals("admin123", response.getData().getAccounts().get(0));
//    }
//
//    @Test
//    public void testChangeAdminPassword() {
//        String adminId = "admin123";
//        AuthRequest.changeAdminPassword request = new AuthRequest.changeAdminPassword();
//        request.setPassword_for_change("newPassword123");
//        Admin admin = Admin.builder().adminId(adminId).password("password123").build();
//        when(adminRepository.findByAdminId(adminId)).thenReturn(Optional.of(admin));
//
//        ResponseTemplate<String> response = adminService.changeAdminPassword(admin, request);
//
//        assertNotNull(response);
//        assertEquals("비밀 번호 변경 완료", response.getData());
//        verify(adminRepository, times(1)).save(admin);
//    }
//
//    @Test
//    public void testDeleteAdmin() {
//        String adminId = "admin123";
//        Admin admin = Admin.builder().adminId(adminId).password("password123").build();
//        when(adminRepository.findByAdminId(adminId)).thenReturn(Optional.of(admin));
//
//        adminService.deleteAdmin(admin);
//
//        verify(adminRepository, times(1)).delete(admin);
//    }

}
