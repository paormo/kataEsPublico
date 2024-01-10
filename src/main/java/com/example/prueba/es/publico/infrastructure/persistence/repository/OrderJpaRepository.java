package com.example.prueba.es.publico.infrastructure.persistence.repository;

import com.example.prueba.es.publico.infrastructure.persistence.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, Integer> {



}
