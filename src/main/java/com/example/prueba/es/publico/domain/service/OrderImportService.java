package com.example.prueba.es.publico.domain.service;

import java.io.IOException;

public interface OrderImportService {

    void importOrdersAndGenerateCSV() throws IOException;
}
