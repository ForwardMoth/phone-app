package com.nexign.phone_service_app.util;

import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.util.Random;
import java.util.UUID;

@UtilityClass
public class GeneratorUtil {

    private final Integer MIN_RANDOM_NUMBER = 10;
    private final Integer MAX_RANDOM_NUMBER = 100;

    private static final Random random = new Random();

    /**
     * Метод, генерирующий рандомное число, которое соотвествует количеству созданных записей в таблицу call_data
     *
     * @return Количество создаваемых записей в таблицу call_data
     */
    public static int generateRandomNumberCallRecords() {
        return random.nextInt(MAX_RANDOM_NUMBER) + MIN_RANDOM_NUMBER;
    }

    /**
     * Метод, генерирующий рандомный индекс абонента из списка абонентов в таблице callers
     *
     * @return Индекс абонента из списка абонентов в таблице callers
     */
    public static int generateRandomCallerIndex(int size) {
        return random.nextInt(size);
    }

    /**
     * Метод, генерирующий рандомное значение длительности (Duration)
     *
     * @return Значение длительности (Duration)
     */
    public static Duration generateRandomDuration(Duration start, Duration end) {
        return Duration.ofSeconds(random.nextLong(start.getSeconds(), end.getSeconds()));
    }

    /**
     * Метод генерации внешнего идентификатора записи
     *
     * @return Рандомное значение uuid
     */
    public static UUID generateRandomUuid() {
        return UUID.randomUUID();
    }

}
