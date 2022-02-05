package business.domain;

import business.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    @Builder
    public ActionLog(Long id, Person person, Action action, String description, String reason) {
        super(id);
        this.person = person;
        this.action = action;
        this.description = description;
        this.reason = reason;
    }
}
