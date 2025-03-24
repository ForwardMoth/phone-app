package com.nexign.phone_service_app.util;

public final class Constants {

    public static class Caller {

        public static final String CALLER_WITH_ID_IS_NOT_FOUND_EXCEPTION = "Абонент с таким id=%s не найден";
        public static final String CALLERS_IS_NOT_FOUND_EXCEPTION = "Абоненты не найдены";

    }

    public static final String MONTH_BAD_REQUEST_EXCEPTION = "Неверный формат месяца. " +
            "Месяц задаётся в формате 'mm.yyyy'";
    public static final String PERIOD_SHOULD_BE_FILLED_BAD_REQUEST_EXCEPTION = "Параметры startDate и finishDate должны " +
            "быть заполнены в формате 'yyyy-mm-dd'";

    public static final String FILE_BAD_REQUEST_EXCEPTION = "Ошибка записи в файл";
    public static final String DIR_BAD_REQUEST_EXCEPTION = "Ошибка при создании директории для отчетов";

    private Constants() {
    }

}
