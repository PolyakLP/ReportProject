package com.plp.testproject.demo.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "LOCATIONS")
@Data
public class Locations extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false)
    private String uuid;
    //


    @Column(columnDefinition = "text")
    private String manager_name;
    @Column(columnDefinition = "text")
    private String phone;
    @Column(columnDefinition = "text")
    private String address_primary;
    @Column(columnDefinition = "text")
    private String address_secondary;
    @Column(columnDefinition = "text")
    private String country;
    @Column(columnDefinition = "text")
    private String town;
    @Column(columnDefinition = "text")
    private String postal_code;

    public Locations(Long id, String uuid, String manager_name, String phone, String address_primary, String address_secondary, String country, String town, String postal_code) {
        this.id = id;
        this.uuid = uuid;
        this.manager_name = manager_name;
        this.phone = phone;
        this.address_primary = address_primary;
        this.address_secondary = address_secondary;
        this.country = country;
        this.town = town;
        this.postal_code = postal_code;
    }

    public Locations() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getManager_name() {
        return manager_name;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress_primary() {
        return address_primary;
    }

    public void setAddress_primary(String address_primary) {
        this.address_primary = address_primary;
    }

    public String getAddress_secondary() {
        return address_secondary;
    }

    public void setAddress_secondary(String address_secondary) {
        this.address_secondary = address_secondary;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }
}
