package com.nexign.phone_service_app.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * Объект для внешних идентификаторов
 */
@Data
@Builder
public class UuidDto {

    /**
     * Внешний идентификатор, обязательный
     */
    private UUID uuid;
}
