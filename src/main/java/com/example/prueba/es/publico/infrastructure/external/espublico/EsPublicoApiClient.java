package com.example.prueba.es.publico.infrastructure.external.espublico;

import com.example.prueba.es.publico.domain.model.PageOrder;
import com.example.prueba.es.publico.domain.ports.external.EsPublicoApiPort;
import com.example.prueba.es.publico.infrastructure.external.espublico.mapper.PageOrderDTOMapper;
import com.example.prueba.es.publico.infrastructure.external.espublico.model.PageOrderDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Component
public class EsPublicoApiClient implements EsPublicoApiPort {

    private final RestTemplate restTemplate;
    private final PageOrderDTOMapper mapper;
    private final String ORDERS_URL = "https://kata-espublicotech.g3stiona.com:443/v1/orders";


    public PageOrder getPageOrderByPageAndMaxPerPage(Integer page, Integer maxPerPage) {
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(ORDERS_URL)
                .queryParam("page", "{page}")
                .queryParam("max-per-page", "{max-per-page}")
                .encode()
                .toUriString();

        Map<String, Integer> paramsMap = new HashMap<>();
        paramsMap.put("page", page);
        paramsMap.put("max-per-page", maxPerPage);

        HttpEntity<PageOrderDTO> response = restTemplate.getForEntity(
                urlTemplate,
                PageOrderDTO.class,
                paramsMap
        );
        return mapper.toDomainFromDTO(response.getBody());
    }

    public PageOrder getPageOrderByUrl(String url) {
        HttpEntity<PageOrderDTO> response = restTemplate.getForEntity(
                url,
                PageOrderDTO.class
        );
        return mapper.toDomainFromDTO(response.getBody());
    }


}
