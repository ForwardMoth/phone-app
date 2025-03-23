package com.nexign.phone_service_app.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;

/**
 * Длительность вызовов для абонента
 */
@Data
@Builder
public class CallerDurationDto {

    /**
     * Длительность входящих вызовов, обязательный
     */
    private Duration incomingDuration;

    /**
     * Длительность исходящих вызовов, обязательный
     */
    private Duration outcomingDuration;

}
