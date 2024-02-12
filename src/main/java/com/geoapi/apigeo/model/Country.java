package com.geoapi.apigeo.model;


import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Country")
    private String name;

    @Column(name = "Capital")
    private String capital;

    @Column(name = "Flag")
    private String flagUrl;

    @Column(name = "Description")
    private String description;
    
}