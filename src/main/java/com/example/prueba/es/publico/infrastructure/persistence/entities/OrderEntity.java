package com.example.prueba.es.publico.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "order_entity", schema = "public")
public class OrderEntity {

    @Id
    @Column(name="id_pk")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGen")
    @SequenceGenerator(name = "seqGen", sequenceName = "seq")
    private Integer idPK;
    @Column(name="id")
    private Integer id;
    @Column(name="uuid")
    private UUID uuid;
    @Column(name="region")
    private String region;
    @Column(name="country")
    private String country;
    @Column(name="item_type")
    private String itemType;
    @Column(name="sales_channel")
    private String salesChannel;
    @Column(name="priority")
    private String priority;
    @Column(name="date")
    private Date date;
    @Column(name="ship_date")
    private Date shipDate;
    @Column(name="units_sold")
    private Integer unitsSold;
    @Column(name="unit_price")
    private Double unitPrice;
    @Column(name="unit_cost")
    private Double unitCost;
    @Column(name="total_revenue")
    private Double totalRevenue;
    @Column(name="total_cost")
    private Double totalCost;
    @Column(name="total_profit")
    private Double totalProfit;

}
