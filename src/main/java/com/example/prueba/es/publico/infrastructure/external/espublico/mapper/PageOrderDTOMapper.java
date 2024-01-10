package com.example.prueba.es.publico.infrastructure.external.espublico.mapper;

import com.example.prueba.es.publico.domain.model.PageOrder;
import com.example.prueba.es.publico.infrastructure.external.espublico.model.PageOrderDTO;
import org.mapstruct.Mapper;

@Mapper(uses={OrderDTOClientMapper.class, LinksDTOClientMapper.class},componentModel = "spring")
public interface PageOrderDTOMapper {

    PageOrder toDomainFromDTO(PageOrderDTO kata);
}
