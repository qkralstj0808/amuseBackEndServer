package com.example.amusetravelproejct.controller.payment;

import com.example.amusetravelproejct.domain.User;
import com.example.amusetravelproejct.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void getPaymentPage() throws Exception {
        // given
        User fakeUser = User.builder()
                .username("kim")
                .phoneNumber("01044445555")
                .email("test@example.com")
                .point(1000)
                .build();

        //when
        given(userService.getUserByPrincipal(any())).willReturn(fakeUser);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/payment")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userName").value("kim"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userPhoneNumber").value("01044445555"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userEmail").value("test@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userPoint").value(1000));
    }
}