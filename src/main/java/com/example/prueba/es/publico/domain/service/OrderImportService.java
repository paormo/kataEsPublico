package com.example.prueba.es.publico.domain.service;

import java.io.IOException;

public interface OrderImportService {

    String importOrdersAndGenerateCSV() throws IOException;
}
