package com.nexign.phone_service_app.util;

public final class Constants {

    public static class Caller {

        public static final String CALLER_WITH_ID_IS_NOT_FOUND_EXCEPTION = "Абонент с таким id=%s не найден";
        public static final String CALLERS_IS_NOT_FOUND_EXCEPTION = "Абоненты не найдены";

    }

    public static final String MONTH_BAD_REQUEST_EXCEPTION = "Неверный формат месяца. " +
            "Месяц задаётся в формате 'mm.yyyy'";

    private Constants() {
    }

}
