package peaksoft.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_id_project")
    @SequenceGenerator(name = "generator_id_project", allocationSize = 1)
    private Long id;
    @Column(name = "project_name")
    private String projectName;
    private String description;
    @Column(name = "date_of_start")
    private LocalDate dateOfStart;
    @Column(name = "date_of_finish")
    private LocalDate dateOfFinish;
    private BigDecimal price;

    @ManyToMany(cascade = {MERGE,REFRESH,DETACH,PERSIST},fetch = FetchType.LAZY)
    @JoinTable(name = "project_programmers",
            joinColumns = @JoinColumn(name = "projects_id"),
            inverseJoinColumns = @JoinColumn(name = "programmers_id"))
    private List<Programmer> programmers;

    public Project(String projectName, String description, LocalDate dateOfStart, LocalDate dateOfFinish, BigDecimal price) {
        this.projectName = projectName;
        this.description = description;
        this.dateOfStart = dateOfStart;
        this.dateOfFinish = dateOfFinish;
        this.price = price;
    }

    @Override
    public String toString() {
        return "\nProject{" +
                "\nid=" + id +
                "\nprojectName='" + projectName + '\'' +
                "\ndescription='" + description + '\'' +
                "\ndateOfStart=" + dateOfStart +
                "\ndateOfFinish=" + dateOfFinish +
                "\nprice=" + price + "\n";
    }
}
