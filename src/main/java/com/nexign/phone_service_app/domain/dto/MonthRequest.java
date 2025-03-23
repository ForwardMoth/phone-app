package com.nexign.phone_service_app.domain.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Тело запроса для получения UDR по всем сотрудникам за заданный месяц
 */
@Data
@Builder
public class MonthRequest {

    /**
     * Период для получения UDR (месяц в формате "mm.yyyy"), обязательный
     */
    private String period;

}
