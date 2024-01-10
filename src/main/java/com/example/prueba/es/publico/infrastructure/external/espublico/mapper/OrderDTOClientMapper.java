package com.example.prueba.es.publico.infrastructure.external.espublico.mapper;

import com.example.prueba.es.publico.domain.model.Order;
import com.example.prueba.es.publico.infrastructure.external.espublico.model.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderDTOClientMapper {

    @Mapping(source = "item_type", target = "itemType")
    @Mapping(source = "sales_channel", target = "salesChannel")
    @Mapping (target = "shipDate",expression = "java(parseDate(order.getShip_date()))")
    @Mapping (target = "date",expression = "java(parseDate(order.getDate()))")
    @Mapping(source = "units_sold", target = "unitsSold")
    @Mapping(source = "unit_price", target = "unitPrice")
    @Mapping(source = "unit_cost", target = "unitCost")
    @Mapping(source = "total_revenue", target = "totalRevenue")
    @Mapping(source = "total_cost", target = "totalCost")
    @Mapping(source = "total_profit", target = "totalProfit")
    Order toDomainFromDTO(OrderDTO order);

    List<Order> toDomainFromDTOList(List<OrderDTO> orders);

    default Date parseDate(String dateString){
        String pattern = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }
}
