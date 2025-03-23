package com.nexign.phone_service_app.controller;

import com.nexign.phone_service_app.service.GeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generator")
@RequiredArgsConstructor
public class GeneratorController {

    private final GeneratorService generatorService;

    /**
     * Метод генерации записей звонков
     *
     * @return Возвращает код 201
     */
    @PostMapping("/generateCDR")
    public ResponseEntity<Void> generateCDR() {
        generatorService.generateCDR();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
