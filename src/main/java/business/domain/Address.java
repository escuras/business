package business.domain;

import business.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = Constants.TABLE_ADDRESS_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address extends Base {

    @OneToMany(mappedBy = "address")
    private List<Person> persons = new ArrayList<>();

    private Long cep;
    private String local;
    private String number;
    private String complement;
    private String district;
    private String city;
    private String uf;

    @NotNull
    private String country;

    private LocalDateTime inclusionDate;


    @Builder
    public Address(final Long id, final List<Person> persons, final Long cep, final String local, final String number, final String complement, final String district, final String city, final String uf, final String country, final LocalDateTime inclusionDate) {
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

    @PrePersist
    private void inclusionDate() {
        this.setInclusionDate(LocalDateTime.now());
    }
}
