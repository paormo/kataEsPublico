package com.example.prueba.es.publico.infrastructure.persistence.ports;

import com.example.prueba.es.publico.domain.model.Order;
import com.example.prueba.es.publico.domain.ports.internal.OrderPersistencePort;
import com.example.prueba.es.publico.infrastructure.persistence.entities.OrderEntity;
import com.example.prueba.es.publico.infrastructure.persistence.mapper.OrderMapper;
import com.example.prueba.es.publico.infrastructure.persistence.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements OrderPersistencePort {


    private final OrderJpaRepository orderJpaRepository;

    private final OrderMapper orderMapper;

    @Override
    public void saveInPersistence(List<Order> orders) {
        orderJpaRepository.saveAll(
                orderMapper.toEntityFromDomainList(orders));
    }

    @Override
    public Page<Order> getPageFromPersistenceOrderedByOrderId(Integer page, Integer maxPerPage) {
        Pageable pageableAndSort = PageRequest.of(page,maxPerPage, Sort.by("id").ascending());
        Page<OrderEntity> resultSet = orderJpaRepository.findAll(pageableAndSort);
        return new PageImpl<>(orderMapper.toDomainFromEntityList(resultSet.getContent()),pageableAndSort,resultSet.getTotalElements());
    }


}
