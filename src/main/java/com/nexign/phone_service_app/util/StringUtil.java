package com.nexign.phone_service_app.util;

import lombok.experimental.UtilityClass;

import java.time.Duration;

import static java.util.Objects.isNull;

@UtilityClass
public class StringUtil {

    public static String formatTotalTime(Duration duration) {
        if (isNull(duration)) {
            return "00:00:00";
        }
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

}
