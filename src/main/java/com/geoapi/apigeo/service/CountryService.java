package com.geoapi.apigeo.service;

import com.geoapi.apigeo.exception.StatusException;
import com.geoapi.apigeo.model.Country;
import com.geoapi.apigeo.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public Country getCountryById(Long id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new StatusException.ResourceNotFoundException("Country", "id", id));
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Country addCountry(Country country) {
        return countryRepository.save(country);
    }

    public Country updateCountry(Long id, Country countryDetails) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new StatusException.ResourceNotFoundException("Country", "id", id));

        country.setName(countryDetails.getName());
        country.setCapital(countryDetails.getCapital());
        country.setFlagUrl(countryDetails.getFlagUrl());
        country.setDescription(countryDetails.getDescription());

        return countryRepository.save(country);
    }

    public void deleteCountry(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new StatusException.ResourceNotFoundException("Country", "id", id));

        countryRepository.delete(country);
    }

}


