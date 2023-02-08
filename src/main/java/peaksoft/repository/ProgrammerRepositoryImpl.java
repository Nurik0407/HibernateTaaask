package peaksoft.repository;

import org.hibernate.HibernateException;
import peaksoft.config.Util;
import peaksoft.entity.Address;
import peaksoft.entity.Programmer;
import peaksoft.entity.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProgrammerRepositoryImpl implements ProgrammerRepository, AutoCloseable {
    static final EntityManagerFactory entityManagerFactory = Util.getEntityManagerFactory();

    @Override
    public void close() throws Exception {
        entityManagerFactory.close();
    }

    @Override
    public String addConstrainUnique() {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            en.createNativeQuery("alter table programmers add constraint email unique(email);").executeUpdate();
            en.getTransaction().commit();
            en.close();
            return "Constrain unique successfully added...";
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String saveProgrammer(Programmer programmer, Long addressID) {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            Address address = en.createQuery("select l from Address l where id=:id", Address.class)
                    .setParameter("id", addressID).getSingleResult();
            programmer.setAddress(address);
            en.persist(programmer);
            en.getTransaction().commit();
            en.close();
            return programmer.getFullName() + " successfully saved...";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String saveProgrammer(List<Programmer> programmers, Long addressID) {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            Address address = en.createQuery("select l from Address l where id=:id", Address.class)
                    .setParameter("id", addressID).getSingleResult();
            for (Programmer programmer : programmers) {
                programmer.setAddress(address);
                en.persist(programmer);
            }
            en.getTransaction().commit();
            en.close();
            return "Programmers successfully saved...";
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Programmer> getAll() {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            List<Programmer> programmers = en.createQuery("select l from Programmer l ", Programmer.class).getResultList();
            en.getTransaction().commit();
            en.close();
            return programmers;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Optional<Programmer> getByID(Long id) {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            Programmer programmer = en.createQuery("select l from Programmer l where id=:id", Programmer.class)
                    .setParameter("id", id).getSingleResult();
            en.getTransaction().commit();
            en.close();
            return Optional.ofNullable(programmer);
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
            Programmer programmer = en.find(Programmer.class, id);
            programmer.setAddress(null);
            Project projectDel = null;
            for (Project project : programmer.getProjects()) {
                for (Programmer projectProgrammer : project.getProgrammers()) {
                    if (projectProgrammer.getId().equals(programmer.getId())) {
                        projectDel = project;
                    }
                }
            }
            projectDel.getProgrammers().remove(programmer);
            en.merge(programmer);
            en.merge(projectDel);
            en.remove(programmer);
            en.getTransaction().commit();
            en.close();
            return programmer.getFullName() + " successfully deleted...";
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
            List<Programmer> programmers = en.createQuery("select l from Programmer l", Programmer.class).getResultList();
            for (Programmer programmer : programmers) {
                if (programmer.getAddress() != null) {
                    programmer.setAddress(null);
                }
                if (programmer.getProjects() != null) {
                    for (Project project : programmer.getProjects()) {
                        project.setProgrammers(null);
                    }
                }
            }
            en.createQuery("delete from Programmer ").executeUpdate();
            en.getTransaction().commit();
            en.close();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Programmer update(Long id, Programmer newProgrammer) {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            Programmer programmer = en.createQuery("select l from Programmer l where id=:id", Programmer.class)
                    .setParameter("id", id).getSingleResult();
            programmer.setFullName(newProgrammer.getFullName());
            programmer.setEmail(newProgrammer.getEmail());
            programmer.setDateOfBirth(newProgrammer.getDateOfBirth());
            programmer.setStatus(newProgrammer.getStatus());
            en.merge(programmer);
            en.getTransaction().commit();
            en.close();
            return programmer;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Programmer> getProgrammerByCountry(String country) {
        List<Programmer> programmers = new ArrayList<>();
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            programmers = en.createNativeQuery("select * from programmers p join addresses a on a.id = p.address_id join countries c on c.id = a.country_id where c.country=:country",
                    Programmer.class).setParameter("country", country).getResultList();
            en.getTransaction().commit();
            en.close();
            return programmers;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String getYoungProgrammer() {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            Programmer programmer = (Programmer) en.createNativeQuery("select *,extract(year from date_of_birth) as year from programmers order by year desc limit 1", Programmer.class).getSingleResult();
            en.getTransaction().commit();
            en.close();
            int age = (int) Period.between(programmer.getDateOfBirth(), LocalDate.now()).get(ChronoUnit.YEARS);
            return "fullName: " + programmer.getFullName() + "\nage: " + age;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String getSeniorProgrammer() {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            Programmer programmer = (Programmer) en.createNativeQuery("select *,extract(year from date_of_birth) as year from programmers order by year limit 1", Programmer.class)
                    .getSingleResult();
            en.getTransaction().commit();
            en.close();
            int age = (int) Period.between(programmer.getDateOfBirth(), LocalDate.now()).get(ChronoUnit.YEARS);
            return "fullName: " + programmer.getFullName() + "\nage: " + age;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
