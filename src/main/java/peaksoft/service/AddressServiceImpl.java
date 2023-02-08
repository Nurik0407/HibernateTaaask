package peaksoft.service;

import peaksoft.entity.Address;
import peaksoft.repository.AddressRepository;
import peaksoft.repository.AddressRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class AddressServiceImpl implements AddressService{
    AddressRepository addressRepository = new AddressRepositoryImpl();
    @Override
    public String saveAddress(Long countryId, Address address) {
        return addressRepository.saveAddress(countryId, address);
    }

    @Override
    public String saveAddress(Long countryId, List<Address> addresses) {
        return addressRepository.saveAddress(countryId, addresses);
    }

    @Override
    public List<Address> getAll() {
        return addressRepository.getAll();
    }

    @Override
    public Optional<Address> getByID(Long id) {
        return addressRepository.getByID(id);
    }

    @Override
    public String deleteByID(Long id) {
        return addressRepository.deleteByID(id);
    }

    @Override
    public String deleteAll() {
        return addressRepository.deleteAll();
    }

    @Override
    public Address updateByID(Long id, Address address) {
        return addressRepository.updateByID(id, address);
    }
}
