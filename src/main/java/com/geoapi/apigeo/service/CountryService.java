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


    }


