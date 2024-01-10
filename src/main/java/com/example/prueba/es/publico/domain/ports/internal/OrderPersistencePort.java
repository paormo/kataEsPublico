package com.example.prueba.es.publico.domain.ports.internal;

import com.example.prueba.es.publico.domain.model.Order;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderPersistencePort {

    void saveInPersistence(List<Order> orders);

    Page<Order> getPageFromPersistenceOrderedByOrderId(Integer page, Integer maxPerPage);



}
