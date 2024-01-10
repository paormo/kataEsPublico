package com.example.prueba.es.publico.infrastructure.external.espublico.model;

import lombok.Data;

import java.util.List;

@Data
public class PageOrderDTO {
    Integer page;
    List<OrderDTO> content;
    LinksDTO links;
}
