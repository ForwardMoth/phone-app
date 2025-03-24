package com.nexign.phone_service_app.controller;

import com.nexign.phone_service_app.domain.dto.CallerRequest;
import com.nexign.phone_service_app.domain.dto.CallerUDRResponse;
import com.nexign.phone_service_app.domain.dto.GenerateCDRRequest;
import com.nexign.phone_service_app.domain.dto.MonthRequest;
import com.nexign.phone_service_app.domain.dto.UuidDto;
import com.nexign.phone_service_app.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    /**
     * Метод получения отчета об использовании данных по абоненту за период времени (месяц или весь период)
     *
     * @param request - Тело запроса CallerRequest
     * @return CallerUDRResponse - отчет по сотруднику
     */
    @PostMapping("/callerUDR")
    public ResponseEntity<CallerUDRResponse> getCallerUDR(@RequestBody CallerRequest request) {
        return ResponseEntity.ok().body(reportService.getCallerUDR(request));
    }

    /**
     * Метод получения отчета об использовании данных по всем абонентам за определенный месяц
     *
     * @param request - Тело запроса MonthRequest
     * @return List<CallerUDRResponse> - список отчетов по всем абонентам
     */
    @PostMapping("/monthCallersUDR")
    public ResponseEntity<List<CallerUDRResponse>> getMonthCallersUDR(@RequestBody MonthRequest request) {
        return ResponseEntity.ok().body(reportService.getMonthCallersUDR(request));
    }

    /**
     * Метод генерации отчета CDR по пользователю за заданный период времени
     *
     * @param request - Тело запроса GenerateCDRRequest
     * @return UuidDto - Объект с идентификатором создаваемого файла
     */
    @PostMapping("/generateCDR")
    public ResponseEntity<UuidDto> generateCDR(@RequestBody GenerateCDRRequest request) {
        return ResponseEntity.ok(reportService.generateCDR(request));
    }

}
