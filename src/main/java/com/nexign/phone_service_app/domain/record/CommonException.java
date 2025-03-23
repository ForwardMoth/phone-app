package com.nexign.phone_service_app.domain.record;

import lombok.Builder;

@Builder
public record CommonException(int code, String message) {

}
