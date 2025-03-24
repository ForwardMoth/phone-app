package com.nexign.phone_service_app.util;

import com.nexign.phone_service_app.domain.entity.CallData;
import com.nexign.phone_service_app.domain.exception.BadRequestException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.nexign.phone_service_app.util.Constants.DIR_BAD_REQUEST_EXCEPTION;
import static com.nexign.phone_service_app.util.Constants.FILE_BAD_REQUEST_EXCEPTION;

@Slf4j
@UtilityClass
public class FileGeneratorUtil {

    private final static Path REPORTS_DIR = Paths.get("reports");

    public static void generateCDRFile(String fileName, List<CallData> callDataList, String phoneNumber) {
        try {
            if (!Files.exists(REPORTS_DIR)) {
                Files.createDirectories(REPORTS_DIR);
            }

            Path filePath = REPORTS_DIR.resolve(fileName);

            try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                for (CallData callData : callDataList) {
                    String fileRow = createFileRow(callData, phoneNumber);
                    writer.write(fileRow);
                    writer.newLine();
                }
            } catch (Exception e) {
                log.error("Ошибка записи в файл: ", e);
                throw new BadRequestException(FILE_BAD_REQUEST_EXCEPTION);
            }
        } catch (IOException e) {
            log.error("Ошибка при создании директории для отчетов: ", e);
            throw new BadRequestException(DIR_BAD_REQUEST_EXCEPTION);
        }
    }

    private String createFileRow(CallData callData, String phoneNumber) {
        var incomingPhoneNumber = callData.getIncomingCaller().getPhoneNumber();
        var outcomingPhoneNumber = callData.getOutcomingCaller().getPhoneNumber();
        var startCall = callData.getStartCallTime().truncatedTo(ChronoUnit.SECONDS);
        var finishCall = callData.getFinishCallTime().truncatedTo(ChronoUnit.SECONDS);
        var callType = phoneNumber.equals(incomingPhoneNumber) ? "01" : "02";

        return String.format("%s,%s,%s,%s,%s", callType, incomingPhoneNumber, outcomingPhoneNumber,
                startCall, finishCall);
    }

}
