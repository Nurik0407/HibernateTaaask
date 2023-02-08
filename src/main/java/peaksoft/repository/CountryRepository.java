package peaksoft.repository;

import peaksoft.entity.Country;

import java.util.List;
import java.util.Optional;

public interface CountryRepository {
    String saveCountry(Country country);

    String saveCountry(List<Country> countryList);

    List<Country> getAllCountry();

    Country getByID(Long id);
    String deleteByID(Long id);

    String deleteAll();

    Country updateByID(Long id, Country country);

    Country getByLongDescription();

    Integer getByCountryNameCount(String name);
}
