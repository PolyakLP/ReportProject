package com.plp.testproject.demo.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "LISTING_STATUS")
@Data
public class ListingStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private String status_name;

}
