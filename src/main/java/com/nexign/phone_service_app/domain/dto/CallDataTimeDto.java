package com.nexign.phone_service_app.domain.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Длительность вызова
 */
@Data
@Builder
public class CallDataTimeDto {

    /**
     * Длительность вызовов (формат: 'hh:mm:ss'), обязательный
     */
    private String totalTime;

}
