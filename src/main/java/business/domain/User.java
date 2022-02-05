package business.domain;

import business.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = Constants.TABLE_USER_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends Base {

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @NotNull
    @Column(nullable = false)
    private String email;
    private Double document;
    private boolean active;
    private LocalDateTime inclusionDate;

    @PrePersist
    private void addInclusionDate() {
        this.setInclusionDate(LocalDateTime.now());
    }

}
