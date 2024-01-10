package com.example.prueba.es.publico.infrastructure.external.espublico.mapper;

import com.example.prueba.es.publico.domain.model.Links;
import com.example.prueba.es.publico.infrastructure.external.espublico.model.LinksDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LinksDTOClientMapper {

    Links toDomainFromDTO(LinksDTO links);
}
