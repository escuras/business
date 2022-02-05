package business.domain;

import business.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = Constants.TABLE_PERSON_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person extends Base {

    private String name;
    private String email;
    private Double document;
    private boolean active;

    private LocalDateTime inclusionDate;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "person", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private List<Company> ownerCompanies = new ArrayList<>();

    @ManyToMany(mappedBy = "workers", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Set<Company> workingCompanies = new HashSet<>();

    @ManyToOne
    private Address address;

    @Builder
    public Person(final Long id, final String name, final String email, final Double document, final boolean active, final LocalDateTime inclusionDate) {
        super(id);
        this.name = name;
        this.email = email;
        this.document = document;
        this.active = active;
        this.inclusionDate = inclusionDate;
    }

    @PrePersist
    private void setDate() {
        this.setInclusionDate(LocalDateTime.now());
    }
}
