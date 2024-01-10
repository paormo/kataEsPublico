package com.example.prueba.es.publico.domain.ports.external;

import com.example.prueba.es.publico.domain.model.PageOrder;

public interface EsPublicoApiPort {

    PageOrder getPageOrderByPageAndMaxPerPage(Integer page, Integer maxPerPage);

    PageOrder getPageOrderByUrl(String url);
}
