package com.nexign.phone_service_app.domain.dto;

import lombok.Builder;
import lombok.Data;

/**
 * UDR отчет по абоненту
 */
@Data
@Builder
public class CallerUDRResponse {

    /**
     * Номер телефона абонента, обязательный
     */
    private String msisdn;

    /**
     * Длительность входящих вызовов, обязательный
     */
    private CallDataTimeDto incomingCall;

    /**
     * Длительность входящих вызовов, обязательный
     */
    private CallDataTimeDto outcomingCall;

}
