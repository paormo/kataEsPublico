package com.example.prueba.es.publico.infrastructure.persistence.mapper;

import com.example.prueba.es.publico.domain.model.Order;
import com.example.prueba.es.publico.infrastructure.persistence.entities.OrderEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toDomainFromEntity(OrderEntity order);

    OrderEntity toEntityFromDomain(Order order);

    List<Order> toDomainFromEntityList(List<OrderEntity> orderList);

    List<OrderEntity> toEntityFromDomainList(List<Order> orderList);


}
