package peaksoft.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import peaksoft.enums.Status;
import peaksoft.exceptions.BadrequestException;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "programmers")
public class Programmer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "programmer_id_generator")
    @SequenceGenerator(name = "programmer_id_generator",
            allocationSize = 1)
    private Long id;
    @Column(name = "full_name")
    private String fullName;
    private String email;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany(mappedBy = "programmers",
            cascade = {MERGE, PERSIST, DETACH, REFRESH},fetch = FetchType.LAZY)
    private List<Project> projects;

    @OneToOne(cascade = {MERGE, REFRESH, DETACH, PERSIST})
    private Address address;

    public Programmer(String fullName, String email, LocalDate dateOfBirth, Status status) throws BadrequestException {
        this.fullName = fullName;
        this.email = email;
        constrainAge(dateOfBirth);
        this.status = status;
    }

    private void constrainAge(LocalDate dateOfBirth){
        try {
            if (dateOfBirth.isAfter(LocalDate.now())) {
                 throw new BadrequestException();
            } else {
                this.dateOfBirth = dateOfBirth;
            }
        }catch (BadrequestException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "\nProgrammer{" +
                "\nid=" + id +
                "\nfullName='" + fullName + '\'' +
                "\nemail='" + email + '\'' +
                "\ndateOfBirth=" + dateOfBirth +
                "\nstatus=" + status;
    }
}
