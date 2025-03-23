package com.nexign.phone_service_app.service;

import com.nexign.phone_service_app.domain.dto.CallerRequest;
import com.nexign.phone_service_app.domain.dto.CallerUDRResponse;
import com.nexign.phone_service_app.domain.dto.GenerateCDRRequest;
import com.nexign.phone_service_app.domain.dto.MonthRequest;
import com.nexign.phone_service_app.domain.dto.UuidDto;

import java.util.List;

public interface ReportService {

    CallerUDRResponse getCallerUDR(CallerRequest request);

    List<CallerUDRResponse> getMonthCallersUDR(MonthRequest request);

    UuidDto generateCDR(GenerateCDRRequest request);

}
