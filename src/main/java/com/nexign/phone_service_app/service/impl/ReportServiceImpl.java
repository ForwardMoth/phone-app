package com.nexign.phone_service_app.service.impl;

import com.nexign.phone_service_app.domain.dto.CallDataTimeDto;
import com.nexign.phone_service_app.domain.dto.CallerDurationDto;
import com.nexign.phone_service_app.domain.dto.CallerRequest;
import com.nexign.phone_service_app.domain.dto.CallerUDRResponse;
import com.nexign.phone_service_app.domain.dto.GenerateCDRRequest;
import com.nexign.phone_service_app.domain.dto.MonthRequest;
import com.nexign.phone_service_app.domain.dto.UuidDto;
import com.nexign.phone_service_app.domain.entity.CallData;
import com.nexign.phone_service_app.domain.entity.Caller;
import com.nexign.phone_service_app.domain.exception.BadRequestException;
import com.nexign.phone_service_app.repository.CallDataRepository;
import com.nexign.phone_service_app.service.CallerService;
import com.nexign.phone_service_app.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static com.nexign.phone_service_app.util.Constants.PERIOD_SHOULD_BE_FILLED_BAD_REQUEST_EXCEPTION;
import static com.nexign.phone_service_app.util.DateUtil.getYearMonth;
import static com.nexign.phone_service_app.util.FileGeneratorUtil.generateCDRFile;
import static com.nexign.phone_service_app.util.GeneratorUtil.generateRandomUuid;
import static com.nexign.phone_service_app.util.StringUtil.formatTotalTime;
import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final CallerService callerService;
    private final CallDataRepository callDataRepository;

    /**
     * Если абонента не существует, то выбрасывается ошибка
     * Если период не задан, то поиск звонков идёт по id абонента
     * Если период задан, то поиск звонков по абоненту происходит за месяц
     * Вычисляем длительность входящих и исходящих звонков
     * Маппим ответ в необходимый формат
     *
     * @param request - Тело запроса с uuid абонента и периодом
     * @return CallerUDRResponse - отчет UDR по абоненту
     */
    @Override
    @Transactional(readOnly = true)
    public CallerUDRResponse getCallerUDR(CallerRequest request) {
        final var id = request.getId();
        final var caller = callerService.findCaller(id);

        final var period = request.getPeriod();
        final var callerId = caller.getId();
        List<CallData> callDataList;
        if (isNull(period)) {
            callDataList = callDataRepository.findByCaller(callerId);
        } else {
            final var yearMonth = getYearMonth(period);
            final var startDateTime = yearMonth.atDay(1).atStartOfDay();
            final var finishDateTime = yearMonth.atEndOfMonth().atTime(23, 59, 59);
            callDataList = callDataRepository.findByCallerAndPeriod(callerId, startDateTime, finishDateTime);
        }

        Duration incomingDuration = sumTotalDuration(callDataList, callerId, CallData::getIncomingCaller);
        Duration outgoingDuration = sumTotalDuration(callDataList, callerId, CallData::getOutcomingCaller);

        return CallerUDRResponse.builder()
                .msisdn(caller.getPhoneNumber())
                .incomingCall(getTotalTime(incomingDuration))
                .outcomingCall(getTotalTime(outgoingDuration))
                .build();
    }

    /**
     * Поиск звонков за заданный период
     * На каждой итерации вычисляем длительность звонков, а также по входящему/исходящему абоненту
     * длительность входящего/исходящего вызова
     * Преобразуем Map в список CallerUDRResponse
     *
     * @param request - Тело запроса с периодом абонента (месяц)
     * @return List<CallerUDRResponse> - список отчетов UDR по абонентам
     */
    @Override
    @Transactional(readOnly = true)
    public List<CallerUDRResponse> getMonthCallersUDR(MonthRequest request) {
        final var yearMonth = getYearMonth(request.getPeriod());
        final var startDateTime = yearMonth.atDay(1).atStartOfDay();
        final var finishDateTime = yearMonth.atEndOfMonth().atTime(23, 59, 59);

        final var callDataList = callDataRepository.findByPeriod(startDateTime, finishDateTime);

        Map<String, CallerDurationDto> callerDurationDtoMap = new HashMap<>();
        for (CallData callData : callDataList) {
            final var totalDuration = Duration.between(callData.getStartCallTime(), callData.getFinishCallTime());
            // Заполнение длительности для входящего абонента
            fillCallerDurationMap(callerDurationDtoMap, callData.getIncomingCaller().getPhoneNumber(), totalDuration,
                    CallerDurationDto::getIncomingDuration, CallerDurationDto::setIncomingDuration);
            // Заполнение длительности для исходящего абонента
            fillCallerDurationMap(callerDurationDtoMap, callData.getOutcomingCaller().getPhoneNumber(), totalDuration,
                    CallerDurationDto::getOutcomingDuration, CallerDurationDto::setOutcomingDuration);
        }

        return callerDurationDtoMap.entrySet().stream()
                .map(entry -> CallerUDRResponse.builder()
                        .msisdn(entry.getKey())
                        .incomingCall(getTotalTime(entry.getValue().getIncomingDuration()))
                        .outcomingCall(getTotalTime(entry.getValue().getOutcomingDuration()))
                        .build())
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UuidDto generateCDR(GenerateCDRRequest request) {
        final var caller = callerService.findCaller(request.getId());
        final var calledId = caller.getId();
        final var phoneNumber = caller.getPhoneNumber();

        if (isNull(request.getStartDate()) || isNull(request.getFinishDate())) {
            throw new BadRequestException(PERIOD_SHOULD_BE_FILLED_BAD_REQUEST_EXCEPTION);
        }

        final var startDateTime = request.getStartDate().atStartOfDay();
        final var finishDateTime = request.getFinishDate().atTime(23, 59, 59);

        final var callDataList = callDataRepository.findByCallerAndPeriod(calledId, startDateTime, finishDateTime);

        final var fileId = generateRandomUuid();
        final var fileName = String.format("%s_%s.csv", phoneNumber, fileId);

        generateCDRFile(fileName, callDataList, phoneNumber);

        return UuidDto.builder()
                .uuid(fileId)
                .build();
    }

    /**
     * Метод заполнения длительности вызовов звонков абонентов
     *
     * @param callerDurationDtoMap - Map (ключ - номер телефона, значение - CallerDurationDto)
     * @param phoneNumber          - Номер телефона абонента
     * @param totalDuration        - Общая длительность вызова
     * @param getCallerDuration    - Интерфейс для получения длительности вызова
     * @param setCallerDuration    - Интерфейс для установления длительности вызова
     */
    private void fillCallerDurationMap(Map<String, CallerDurationDto> callerDurationDtoMap,
                                       String phoneNumber,
                                       Duration totalDuration,
                                       Function<CallerDurationDto, Duration> getCallerDuration,
                                       BiConsumer<CallerDurationDto, Duration> setCallerDuration) {
        if (callerDurationDtoMap.containsKey(phoneNumber)) {
            var callerDurationDto = callerDurationDtoMap.get(phoneNumber);

            final var newTotalDuration = Optional.ofNullable(getCallerDuration.apply(callerDurationDto))
                    .map(duration -> duration.plus(totalDuration))
                    .orElse(Duration.ZERO.plus(totalDuration));
            setCallerDuration.accept(callerDurationDto, newTotalDuration);
        } else {
            final var callerDurationDto = CallerDurationDto.builder().build();
            setCallerDuration.accept(callerDurationDto, Duration.ZERO.plus(totalDuration));
            callerDurationDtoMap.put(phoneNumber, callerDurationDto);
        }
    }

    /**
     * Метод для создания объекта CallDataTimeDto
     *
     * @param duration - длительность вызовов
     * @return CallDataTimeDto
     */
    private CallDataTimeDto getTotalTime(Duration duration) {
        return CallDataTimeDto.builder()
                .totalTime(formatTotalTime(duration))
                .build();
    }

    /**
     * Метод вычисления длительности звонков для входящего/исходящего абонента
     *
     * @param callDataList - список звонков
     * @param callerId     - внутренний идентификатор абонента
     * @param getCaller    - интерефейс для получения входящего/исходящего абонента
     * @return Duration (длительность звонков для входящего/исходящего абонента)
     */
    private Duration sumTotalDuration(List<CallData> callDataList,
                                      Long callerId,
                                      Function<CallData, Caller> getCaller) {
        return callDataList.stream()
                .filter(call -> callerId.equals(getCaller.apply(call).getId()))
                .map(call -> Duration.between(call.getStartCallTime(), call.getFinishCallTime()))
                .reduce(Duration.ZERO, Duration::plus);
    }

}
