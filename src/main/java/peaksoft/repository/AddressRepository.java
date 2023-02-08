package peaksoft.repository;

import peaksoft.entity.Address;

import java.util.List;
import java.util.Optional;

public interface AddressRepository {
    String saveAddress(Long countryId, Address address);

    String saveAddress(Long countryId, List<Address> addresses);

    List<Address> getAll();


    Optional<Address> getByID(Long id);

    String deleteByID(Long id);

    String deleteAll();

    Address updateByID(Long id, Address address);


}
