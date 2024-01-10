package com.example.prueba.es.publico.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageOrder {

    private Integer page;
    private List<Order> content;
    private Links links;




}
