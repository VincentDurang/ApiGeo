package com.geoapi.apigeo.controller;

import com.geoapi.apigeo.model.Country;
import com.geoapi.apigeo.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable Long id) {
        Country country = countryService.getCountryById(id);
        return ResponseEntity.ok().body(country);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Country>> getAllCountries() {
        List<Country> countries = countryService.getAllCountries();
        return ResponseEntity.ok().body(countries);
    }

    @PostMapping("/add")
    public ResponseEntity<Country> addCountry(@RequestBody Country country) {
        Country newCountry = countryService.addCountry(country);
        return ResponseEntity.ok().body(newCountry);
    }

}
