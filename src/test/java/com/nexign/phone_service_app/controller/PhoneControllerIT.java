package com.nexign.phone_service_app.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PhoneController.class)
class PhoneControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getPhone() throws Exception {
        final var phoneId = 1L;

        var result = mockMvc.perform(get("/phone/{id}", phoneId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result);
    }
}
