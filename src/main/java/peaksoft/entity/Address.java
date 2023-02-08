package peaksoft.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_id_address")
    @SequenceGenerator(name = "generator_id_address", allocationSize = 1)
    private Long id;
    @Column(name = "region_name")
    private String regionName;
    private String street;
    @Column(name = "home_number")
    private Integer homeNumber;
    @OneToOne(mappedBy = "address",cascade = {DETACH,REFRESH,MERGE,PERSIST},fetch = FetchType.LAZY)
    private Programmer programmer;
    @ManyToOne(cascade = {DETACH,REFRESH,PERSIST,MERGE})
    private Country country;

    public Address(Long id, String regionName, String street, Integer homeNumber) {
        this.id = id;
        this.regionName = regionName;
        this.street = street;
        this.homeNumber = homeNumber;
    }

    public Address(String regionName, String street, Integer homeNumber) {
        this.regionName = regionName;
        this.street = street;
        this.homeNumber = homeNumber;
    }

    @Override
    public String toString() {
        return "\nAddress{" +
                "\nid=" + id +
                "\nregionName='" + regionName + '\'' +
                "\nstreet='" + street + '\'' +
                "\nhomeNumber=" + homeNumber +"\n";
    }
}

