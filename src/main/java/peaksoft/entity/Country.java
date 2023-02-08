package peaksoft.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_id_country")
    @SequenceGenerator(name = "generator_id_country", allocationSize = 1)
    private Long id;
    private String country;
    private String description;
    @OneToMany(mappedBy = "country",cascade = ALL,fetch = FetchType.EAGER)
    private List<Address> addresses;

    public Country(String country, String description) {
        this.country = country;
        this.description = description;
    }

    @Override
    public String toString() {
        return "\nCountry{" +
                "\nid=" + id +
                "\ncountry='" + country + '\'' +
                "\ndescription='" + description +"\n";
    }
}
