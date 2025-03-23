package com.nexign.phone_service_app.service.impl;

import com.nexign.phone_service_app.domain.entity.CallData;
import com.nexign.phone_service_app.domain.entity.Caller;
import com.nexign.phone_service_app.domain.exception.NotFoundException;
import com.nexign.phone_service_app.repository.CallDataRepository;
import com.nexign.phone_service_app.repository.CallerRepository;
import com.nexign.phone_service_app.service.GeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.nexign.phone_service_app.util.Constants.Caller.CALLERS_IS_NOT_FOUND_EXCEPTION;
import static com.nexign.phone_service_app.util.GeneratorUtil.generateRandomCallerIndex;
import static com.nexign.phone_service_app.util.GeneratorUtil.generateRandomDuration;
import static com.nexign.phone_service_app.util.GeneratorUtil.generateRandomNumberCallRecords;
import static com.nexign.phone_service_app.util.GeneratorUtil.generateRandomUuid;
import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeneratorServiceImpl implements GeneratorService {

    private final CallerRepository callerRepository;
    private final CallDataRepository callDataRepository;

    /**
     * Генерация записей таблицу записей звонков
     * Если нет абонентов, то ошибка 404
     * Затем генерируется случайное число - количество записей, которое будет добавлено в таблицу записей звонков
     * Запись заполняется рандомными значениями и обновляется хронологический порядок
     */
    @Override
    @Transactional
    public void generateCDR() {
        final var callers = callerRepository.findAll();
        if (callers.isEmpty()) {
            throw new NotFoundException(CALLERS_IS_NOT_FOUND_EXCEPTION);
        }

        final var numberCallRecord = generateRandomNumberCallRecords();
        var currentDateTime = callDataRepository.findTopByOrderByIdDesc()
                .map(callData -> callData.getStartCallTime().plusYears(1))
                .orElse(LocalDateTime.now());

        List<CallData> callDataList = new ArrayList<>();
        for (int i = 0; i < numberCallRecord; i++) {
            final var callData = generateCallData(callers, currentDateTime);
            callDataList.add(callData);
            currentDateTime = callData.getStartCallTime();
        }
        callDataRepository.saveAll(callDataList);
        log.info("Сгенерировано {} записей звонков", numberCallRecord);
    }

    /**
     * @param callers         - Список абонентов
     * @param currentDateTime - Текущее время и дата для отслеживания хронологического порядка
     * @return Запись для справочника CDR
     */
    private CallData generateCallData(List<Caller> callers, LocalDateTime currentDateTime) {
        Caller incomingCaller = callers.get(generateRandomCallerIndex(callers.size()));
        Caller outcomingCaller = null;
        while (isNull(outcomingCaller) || incomingCaller.equals(outcomingCaller)) {
            outcomingCaller = callers.get(generateRandomCallerIndex(callers.size()));
        }

        final var durationHours = generateRandomDuration(Duration.ofHours(1), Duration.ofHours(23));
        final var durationMinutes = generateRandomDuration(Duration.ofMinutes(1), Duration.ofMinutes(240));

        currentDateTime = currentDateTime.plus(durationHours);

        var callData = CallData.builder()
                .incomingCaller(incomingCaller)
                .outcomingCaller(outcomingCaller)
                .startCallTime(currentDateTime)
                .finishCallTime(currentDateTime.plus(durationMinutes))
                .build();
        callData.setUuid(generateRandomUuid());
        return callData;
    }

}
