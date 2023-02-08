package peaksoft.repository;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import peaksoft.config.Util;
import peaksoft.entity.Address;
import peaksoft.entity.Country;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class CountryRepositoryImpl implements CountryRepository, AutoCloseable {
    static final EntityManagerFactory entityManagerFactory = Util.getEntityManagerFactory();


    @Override
    public String saveCountry(Country country) {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            en.persist(country);
            en.getTransaction().commit();
            en.close();
            return country.getCountry() + " successfully saved...";
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String saveCountry(List<Country> countryList) {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            for (Country country : countryList) {
                en.persist(country);
            }
            en.getTransaction().commit();
            en.close();
            return "Countries successfully saved....";
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Country> getAllCountry() {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            List<Country> countries = en.createQuery("SELECT l FROM Country l", Country.class).getResultList();
            en.getTransaction().commit();
            en.close();
            return countries;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Country getByID(Long id) {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            Country country = en.createQuery("SELECT l FROM Country l WHERE id=:id", Country.class).setParameter("id", id).getSingleResult();
            en.getTransaction().commit();
            en.close();
            return country;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String deleteByID(Long id) {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            Country country = en.find(Country.class, id);
            for (int i = 0; i < country.getAddresses().size(); i++) {
                if (country.getAddresses().get(i).getProgrammer() != null) {
                    country.getAddresses().get(i).getProgrammer().setAddress(null);
                }
                country.getAddresses().get(i).setCountry(null);
                en.remove(country.getAddresses().get(i));
            }
            en.remove(country);
            en.getTransaction().commit();
            en.close();
            return country.getCountry() + " successfully deleted...";
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
            List<Address> addresses = en.createQuery("select l from Address l", Address.class).getResultList();
            for (Address address : addresses) {
                if (address.getProgrammer() != null) {
                    address.getProgrammer().setAddress(null);
                }
                address.setCountry(null);
            }
            en.createQuery("delete from Address ").executeUpdate();
            en.createQuery("delete from Country").executeUpdate();
            en.getTransaction().commit();
            en.close();
            return "Successfully cleaned...";
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Country updateByID(Long id, Country country) {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            Country oldCountry = en.createQuery("SELECT l FROM Country l WHERE id = :id", Country.class).setParameter("id", id).getSingleResult();
            oldCountry.setCountry(country.getCountry());
            oldCountry.setDescription(country.getDescription());
            en.merge(oldCountry);
            en.getTransaction().commit();
            en.close();
            return oldCountry;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Country getByLongDescription() {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            Country country = (Country) en.createNativeQuery("select * from countries order by length(description) desc  limit 1", Country.class).getSingleResult();
            en.getTransaction().commit();
            en.close();
            return country;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Integer getByCountryNameCount(String name) {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            List<Country> countries = en.createQuery("select l from Country l where country=:c", Country.class).setParameter("c", name).getResultList();
            en.getTransaction().commit();
            en.close();
            return countries.size();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        entityManagerFactory.close();
    }
}
