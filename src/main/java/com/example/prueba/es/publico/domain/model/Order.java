package com.example.prueba.es.publico.domain.model;

import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class Order {
    private Integer id;
    private UUID uuid;
    private String region;
    private String country;
    private String itemType;
    private String salesChannel;
    private String priority;
    private Date date;
    private Date shipDate;
    private Integer unitsSold;
    private Double unitPrice;
    private Double unitCost;
    private Double totalRevenue;
    private Double totalCost;
    private Double totalProfit;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        String pattern = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        sb.append(id);
        sb.append(",").append(priority);
        sb.append(",").append(simpleDateFormat.format(date));
        sb.append(",").append(region);
        sb.append(",").append(country);
        sb.append(",").append(itemType);
        sb.append(",").append(salesChannel);
        sb.append(",").append(simpleDateFormat.format(shipDate));
        sb.append(",").append(unitsSold);
        sb.append(",").append(unitPrice);
        sb.append(",").append(unitCost);
        sb.append(",").append(totalRevenue);
        sb.append(",").append(totalCost);
        sb.append(",").append(totalProfit);
        return sb.toString();
    }
}
