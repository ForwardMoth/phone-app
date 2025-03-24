package com.nexign.phone_service_app.service.impl;

import com.nexign.phone_service_app.domain.entity.Caller;
import com.nexign.phone_service_app.domain.exception.NotFoundException;
import com.nexign.phone_service_app.repository.CallerRepository;
import com.nexign.phone_service_app.service.CallerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.nexign.phone_service_app.util.Constants.Caller.CALLER_WITH_ID_IS_NOT_FOUND_EXCEPTION;

@Service
@RequiredArgsConstructor
public class CallerServiceImpl implements CallerService {

    private final CallerRepository callerRepository;

    @Override
    @Transactional(readOnly = true)
    public Caller findCaller(UUID uuid) {
        return callerRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException(String.format(CALLER_WITH_ID_IS_NOT_FOUND_EXCEPTION, uuid)));
    }
}
