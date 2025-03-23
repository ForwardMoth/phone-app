package com.nexign.phone_service_app.controller;

import com.nexign.phone_service_app.BaseIT;
import com.nexign.phone_service_app.repository.CallDataRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class GeneratorControllerIT extends BaseIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CallDataRepository callDataRepository;

    @Test
    @SneakyThrows
    void generateCDR() {
        assertThat(callDataRepository.count()).isZero();

        mockMvc.perform(post("/generator/generateCDR"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        assertThat(callDataRepository.count()).isGreaterThan(0);
    }
}
