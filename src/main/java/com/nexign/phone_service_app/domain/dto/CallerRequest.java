package com.nexign.phone_service_app.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * Тело запроса для получения UDR по абоненту и заданному месяцу
 */
@Data
@Builder
public class CallerRequest {

    /**
     * Внешний идентификатор абоненту, обязательный
     */
    private UUID id;

    /**
     * Период для получения UDR (если задан - месяц в формате "mm.yyyy", иначе за весь период), необязательный
     */
    private String period;

}
