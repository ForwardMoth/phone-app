package com.nexign.phone_service_app.domain.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Тело запроса для генерации CDR отчета
 */
@Data
public class GenerateCDRRequest {

    /**
     * Внешний идентификатор абоненту, обязательный
     */
    private UUID id;

    /**
     * Дата начала получения отчета, обязательный
     */
    private LocalDate startDate;

    /**
     * Дата окончания получения отчета, обязательный
     */
    private LocalDate finishDate;

}
