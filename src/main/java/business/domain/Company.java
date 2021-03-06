package business.domain;

import business.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Constants.TABLE_COMPANY_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company extends Base {

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    @NotNull
    private Person person;

    @NotNull
    @Column(nullable = false)
    private String sector;

    private long workersNumber;

    private String hrResponsible;

    private String financesResponsible;

    private String financesEmail;

    private LocalDateTime inclusionDate;

    @ManyToMany
    @JoinTable(name = "company_workers",
            joinColumns = @JoinColumn(name = "foreign_company_id"),
            inverseJoinColumns = @JoinColumn(name = "foreign_person_id"))
    private Set<Person> workers = new HashSet<>();

    @Builder
    public Company(final Long id, final Person person, final String sector, final long workersNumber, final String hrResponsible, final String financesResponsible, final String financesEmail, final LocalDateTime inclusionDate) {
        super(id);
        this.person = person;
        this.sector = sector;
        this.workersNumber = workersNumber;
        this.hrResponsible = hrResponsible;
        this.financesResponsible = financesResponsible;
        this.financesEmail = financesEmail;
        this.inclusionDate = inclusionDate;
    }


    @PrePersist
    private void addInclusionDate() {
        this.setInclusionDate(LocalDateTime.now());
    }
}
