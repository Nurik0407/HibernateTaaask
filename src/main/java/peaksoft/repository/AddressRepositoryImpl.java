package peaksoft.repository;

import org.hibernate.HibernateException;
import peaksoft.config.Util;
import peaksoft.entity.Address;
import peaksoft.entity.Country;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class AddressRepositoryImpl implements AddressRepository, AutoCloseable {
    static final EntityManagerFactory entityManagerFactory = Util.getEntityManagerFactory();

    @Override
    public void close() throws Exception {
        entityManagerFactory.close();
    }

    @Override
    public String saveAddress(Long countryId, Address address) {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            Country country = en.find(Country.class, countryId);
            address.setCountry(country);
            en.persist(address);
            en.getTransaction().commit();
            en.close();
            return address.getRegionName() + " successfully saved...";
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String saveAddress(Long countryId, List<Address> addresses) {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            Country country = en.find(Country.class, countryId);
            for (Address address : addresses) {
                address.setCountry(country);
            }
            for (Address address : addresses) {
                en.persist(address);
            }
            en.getTransaction().commit();
            en.close();
            return "Addresses successfully saved...";
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Address> getAll() {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            List<Address> addresses = en.createQuery("from Address ", Address.class).getResultList();
            en.getTransaction().commit();
            en.close();
            return addresses;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Optional<Address> getByID(Long id) {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            Address address = en.find(Address.class, id);
            en.getTransaction().commit();
            en.close();
            return Optional.ofNullable(address);
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public String deleteByID(Long id) {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            Address address = en.find(Address.class, id);
            address.getCountry().getAddresses().remove(address);
            if (address.getProgrammer() != null) {
                address.getProgrammer().setAddress(null);
            }
            en.remove(address);
            en.getTransaction().commit();
            en.close();
            return address.getRegionName() + " successfully deleted...";
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String deleteAll() {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            en.createNativeQuery("truncate table Address cascade").executeUpdate();
            en.getTransaction().commit();
            en.close();
            return "Successfully cleaned...";
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Address updateByID(Long id, Address address) {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            Address oldAddress = en.createQuery("select  l from Address  l where id=:id", Address.class).
                    setParameter("id", id).getSingleResult();
            oldAddress.setStreet(address.getStreet());
            oldAddress.setRegionName(address.getRegionName());
            oldAddress.setHomeNumber(address.getHomeNumber());
            en.merge(oldAddress);
            en.getTransaction().commit();
            en.close();
            return oldAddress;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
