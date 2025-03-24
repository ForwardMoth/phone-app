package com.nexign.phone_service_app.service;

import com.nexign.phone_service_app.domain.entity.Caller;

import java.util.UUID;

public interface CallerService {

    Caller findCaller(UUID uuid);

}
