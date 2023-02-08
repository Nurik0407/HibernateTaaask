package peaksoft.repository;

import org.hibernate.HibernateException;
import peaksoft.config.Util;
import peaksoft.entity.Programmer;
import peaksoft.entity.Project;
import peaksoft.enums.Status;
import peaksoft.exceptions.BadrequestException;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class ProjectRepositoryImpl implements ProjectRepository, AutoCloseable {
    static final EntityManagerFactory entityManagerFactory = Util.getEntityManagerFactory();

    @Override
    public void close() throws Exception {
        entityManagerFactory.close();
    }

    @Override
    public String saveProject(Project project) {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            en.persist(project);
            en.getTransaction().commit();
            en.close();
            return project.getProjectName() + "successfully saved...";
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String saveProject(List<Project> projects) {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            for (Project project : projects) {
                en.persist(project);
            }
            en.getTransaction().commit();
            en.close();
            return "Projects successfully saved...";
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Project> getAll() {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            List<Project> projects = en.createQuery("select p from Project p", Project.class).getResultList();
            en.getTransaction().commit();
            en.close();
            return projects;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Optional<Project> getByID(Long id) {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            Project project = en.createQuery("select l from Project l where id=:id", Project.class)
                    .setParameter("id", id).getSingleResult();
            en.getTransaction().commit();
            en.close();
            return Optional.ofNullable(project);
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
            Project result = en.createQuery("select l from Project l where id=:id", Project.class)
                    .setParameter("id", id).getSingleResult();
            en.remove(result);
            en.getTransaction().commit();
            en.close();
            return result.getProjectName() + " successfully deleted...";
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
            en.createNativeQuery("truncate table  projects cascade").executeUpdate();
            en.getTransaction().commit();
            en.close();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Project update(Long id, Project newProject) {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            Project project = en.createQuery("select l from Project l where id=:id", Project.class)
                    .setParameter("id", id).getSingleResult();
            project.setProjectName(newProject.getProjectName());
            project.setPrice(newProject.getPrice());
            project.setDescription(newProject.getDescription());
            project.setDateOfFinish(newProject.getDateOfFinish());
            project.setDateOfStart(newProject.getDateOfStart());
            en.merge(project);
            en.getTransaction().commit();
            en.close();
            return project;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String assignProgrammer(Long projectID, Long programmerID) {
        try {

            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            Project project = en.createQuery("select l from Project l where id=:id", Project.class)
                    .setParameter("id", projectID).getSingleResult();
            Programmer programmer1 = en.find(Programmer.class, programmerID);
            for (Programmer programmer : project.getProgrammers()) {
                if (programmer.getStatus().equals(programmer1.getStatus()) && programmer.getStatus().equals(Status.OWNER)) {
                    return "The owner already exists!!!";
                }
            }
            project.getProgrammers().add(programmer1);
            en.merge(project);
            en.getTransaction().commit();
            en.close();
            return "Programmer " + programmer1.getFullName() + " has been successfully added to the " + project.getProjectName() + " project";
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Project maxPriceProject() {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            Project project = (Project) en.createNativeQuery("select *,price as pr from projects  order by pr desc limit 1", Project.class).getSingleResult();
            en.getTransaction().commit();
            en.close();
            return project;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String getQuickProject() {
        try {
            EntityManager en = entityManagerFactory.createEntityManager();
            en.getTransaction().begin();
            Project project = (Project) en.createNativeQuery("select *,concat(extract(year from date_of_finish)-extract(year from date_of_start)) as bet from projects order by bet limit 1",
                    Project.class).getSingleResult();
            en.getTransaction().commit();
            en.close();
            int year = (int) Period.between(project.getDateOfStart(), project.getDateOfFinish()).get(ChronoUnit.YEARS);
            int month = (int) Period.between(project.getDateOfStart(), project.getDateOfFinish()).get(ChronoUnit.MONTHS);
            int days = (int) Period.between(project.getDateOfStart(), project.getDateOfFinish()).get(ChronoUnit.DAYS);
            return "nameProject: " + project.getProjectName() + "\nperiod: "+ year+"/"+month+"/"+days;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
