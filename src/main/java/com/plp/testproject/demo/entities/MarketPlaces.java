package com.plp.testproject.demo.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "MARKETPLACES")
@Data
public class MarketPlaces {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id",updatable = false,nullable = false)
    private Long id;

    private String marketplace_name;


}
