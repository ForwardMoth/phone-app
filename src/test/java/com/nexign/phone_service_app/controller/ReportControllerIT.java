package com.nexign.phone_service_app.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexign.phone_service_app.BaseIT;
import com.nexign.phone_service_app.domain.dto.CallerRequest;
import com.nexign.phone_service_app.domain.dto.CallerUDRResponse;
import com.nexign.phone_service_app.domain.dto.MonthRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class ReportControllerIT extends BaseIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql({"/script/drop_data.sql", "/script/caller_udr_with_period_test.sql"})
    @SneakyThrows
    void getCallerUDRWithPeriodTest() {
        final var callerId = UUID.fromString("3a51cd46-82f8-45fc-a088-98700d7d791e");
        final var period = "03.2025";

        final var request = CallerRequest.builder()
                .id(callerId)
                .period(period)
                .build();

        final var result = mockMvc.perform(post("/report/callerUDR")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        checkSecondCaller(objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        }));
    }

    @Test
    @Sql({"/script/drop_data.sql", "/script/caller_udr_without_period_test.sql"})
    @SneakyThrows
    void getCallerUDRWithoutPeriodTest() {
        final var callerId = UUID.fromString("ee106b0d-27e9-4020-98db-8fd46b454d48");

        final var request = CallerRequest.builder()
                .id(callerId)
                .build();

        final var result = mockMvc.perform(post("/report/callerUDR")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        checkFirstCaller(objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        }));
    }

    @Test
    @Sql({"/script/drop_data.sql", "/script/caller_udr_with_period_test.sql"})
    @SneakyThrows
    void getMonthCallersUDRTest() {
        final var period = "03.2025";

        final var request = MonthRequest.builder()
                .period(period)
                .build();

        final var result = mockMvc.perform(post("/report/monthCallersUDR")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        final var response = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<CallerUDRResponse>>() {
                });
        assertThat(response).isNotNull();
        assertThat(response).hasSize(2);
        checkFirstCaller(response.get(1));
        checkSecondCaller(response.get(0));
    }

    private void checkFirstCaller(CallerUDRResponse caller) {
        assertThat(caller).isNotNull();
        assertThat(caller.getMsisdn()).isEqualTo("12345");
        assertThat(caller.getIncomingCall().getTotalTime()).isEqualTo("03:02:12");
        assertThat(caller.getOutcomingCall().getTotalTime()).isEqualTo("01:47:05");
    }

    private void checkSecondCaller(CallerUDRResponse caller) {
        assertThat(caller).isNotNull();
        assertThat(caller.getMsisdn()).isEqualTo("111111");
        assertThat(caller.getIncomingCall().getTotalTime()).isEqualTo("01:47:05");
        assertThat(caller.getOutcomingCall().getTotalTime()).isEqualTo("03:02:12");
    }

}
