package com.plp.testproject.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Table(name = "LISTINGS")
@Data
public class Listings extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false)
    private String uuid;


    @Column(columnDefinition = "text", length = 3)
    private String currency;
    @Column(columnDefinition = "text")
    private String title;
    @Column(columnDefinition = "text")
    private String description;
    @Column(columnDefinition = "text")
    private String upload_time;
    @Column(columnDefinition = "text")
    private String owner_email_address;

    private Integer quantity;
    private BigDecimal listing_price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "locations_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Locations locations;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "listing_status_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private ListingStatus listingstatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "marketplace_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private MarketPlaces marketplaces;


    public Listings(Long id, String uuid, String currency, String title, String description, String upload_time, String owner_email_address, Integer quantity, BigDecimal listing_price, Locations locations, ListingStatus listingstatus, MarketPlaces marketplaces) {
        this.id = id;
        this.uuid = uuid;
        this.currency = currency;
        this.title = title;
        this.description = description;
        this.upload_time = upload_time;
        this.owner_email_address = owner_email_address;
        this.quantity = quantity;
        this.listing_price = listing_price;
        this.locations = locations;
        this.listingstatus = listingstatus;
        this.marketplaces = marketplaces;
    }

    public Listings() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }

    public String getOwner_email_address() {
        return owner_email_address;
    }

    public void setOwner_email_address(String owner_email_address) {
        this.owner_email_address = owner_email_address;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getListing_price() {
        return listing_price;
    }

    public void setListing_price(BigDecimal listing_price) {
        this.listing_price = listing_price;
    }

    public Locations getLocations() {
        return locations;
    }

    public void setLocations(Locations locations) {
        this.locations = locations;
    }

    public ListingStatus getListingstatus() {
        return listingstatus;
    }

    public void setListingstatus(ListingStatus listingstatus) {
        this.listingstatus = listingstatus;
    }

    public MarketPlaces getMarketplaces() {
        return marketplaces;
    }

    public void setMarketplaces(MarketPlaces marketplaces) {
        this.marketplaces = marketplaces;
    }
}
