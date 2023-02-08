package peaksoft.service;

import peaksoft.entity.Country;
import peaksoft.repository.CountryRepository;
import peaksoft.repository.CountryRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class CountryServiceImpl implements CountryService{
    CountryRepository countryRepository = new CountryRepositoryImpl();
    @Override
    public String saveCountry(Country country) {
        return countryRepository.saveCountry(country);
    }

    @Override
    public String saveCountry(List<Country> countryList) {
        return countryRepository.saveCountry(countryList);
    }

    @Override
    public List<Country> getAllCountry() {
        return countryRepository.getAllCountry();
    }

    @Override
    public Country getByID(Long id) {
        return countryRepository.getByID(id);
    }

    @Override
    public String deleteByID(Long id) {
        return countryRepository.deleteByID(id);
    }

    @Override
    public String deleteAll() {
        return countryRepository.deleteAll();
    }

    @Override
    public Country updateByID(Long id, Country country) {
        return countryRepository.updateByID(id, country);
    }

    @Override
    public Country getByLongDescription() {
        return countryRepository.getByLongDescription();
    }

    @Override
    public Integer getByCountryNameCount(String name) {
        return countryRepository.getByCountryNameCount(name);
    }
}
