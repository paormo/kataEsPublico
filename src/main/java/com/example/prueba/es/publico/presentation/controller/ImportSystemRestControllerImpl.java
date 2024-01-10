package com.example.prueba.es.publico.presentation.controller;

import com.example.prueba.es.publico.domain.service.OrderImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ImportSystemRestControllerImpl {

    private final OrderImportService orderImportService;
    @PostMapping("/orders-summary")
    public String ordersSummary() throws IOException {
        String csvPath = orderImportService.importOrdersAndGenerateCSV();
        return "Import complete, and \"result.csv\" CSV generated in: " + csvPath;
    }
}
