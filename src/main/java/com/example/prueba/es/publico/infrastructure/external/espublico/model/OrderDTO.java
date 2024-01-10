package com.example.prueba.es.publico.infrastructure.external.espublico.model;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderDTO{
    UUID uuid;
    Integer id;
    String region;
    String country;
    String item_type;
    String sales_channel;
    String priority;
    String date;
    String ship_date;
    Integer units_sold;
    Double unit_price;
    Double unit_cost;
    Double total_revenue;
    Double total_cost;
    Double total_profit;
    LinksDTO linksDTO;


}
