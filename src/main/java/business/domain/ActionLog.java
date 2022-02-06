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
@Table(name = Constants.TABLE_USER_LOG_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ActionLog extends Base {

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false, updatable = false,
            referencedColumnName = "id", foreignKey = @ForeignKey(name = "person_id"))
    @NotNull
    private Person person;

    @ManyToOne
    @JoinColumn(name = "action_id", nullable = false, updatable = false)
    @NotNull
    private Action action;

    private String description;

    private String reason;

    private Long userId;

    private String profileName;

    private LocalDateTime inclusionDate;

    @Builder
    public ActionLog(final Long id, final Person person, final Action action, final String description, final String reason, final Long userId, final String profileName, final LocalDateTime inclusionDate) {
        super(id);
        this.person = person;
        this.action = action;
        this.description = description;
        this.reason = reason;
        this.userId = userId;
        this.profileName = profileName;
        this.inclusionDate = inclusionDate;
    }

    @PrePersist
    private void inclusionDate() {
        this.setInclusionDate(LocalDateTime.now());
    }
}
