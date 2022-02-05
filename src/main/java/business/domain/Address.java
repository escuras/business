package business.domain;

import business.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Constants.TABLE_ADDRESS_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address extends Base {

    @ManyToMany
    @JoinColumn(name = "person_id", nullable = false, updatable = false)
    @NotNull
    private Set<Person> persons = new HashSet<>();

    private Long cep;
    private String local;
    private String number;
    private String complement;
    private String district;
    private String city;
    private String uf;

    @NotNull
    private String country;

    private Date inclusionDate;


    @Builder
    public Address(Long id, Set<Person> persons, Long cep, String local, String number, String complement, String district, String city, String uf, String country, Date inclusionDate) {
        super(id);
        this.persons = persons;
        this.cep = cep;
        this.local = local;
        this.number = number;
        this.complement = complement;
        this.district = district;
        this.city = city;
        this.uf = uf;
        this.country = country;
        this.inclusionDate = inclusionDate;
    }
}
