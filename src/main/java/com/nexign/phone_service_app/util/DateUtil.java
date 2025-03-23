package com.nexign.phone_service_app.util;

import com.nexign.phone_service_app.domain.exception.BadRequestException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.nexign.phone_service_app.util.Constants.MONTH_BAD_REQUEST_EXCEPTION;

@Slf4j
@UtilityClass
public class DateUtil {

    public static YearMonth getYearMonth(String period) {
        try {
            return YearMonth.parse(period, DateTimeFormatter.ofPattern("MM.yyyy"));
        } catch (DateTimeParseException e) {
            log.error("Неправильный формат месяца. Ожидается формат 'MM.yyyy'", e);
            throw new BadRequestException(MONTH_BAD_REQUEST_EXCEPTION);
        }
    }

}
