package business.domain;


import business.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Constants.TABLE_ACTION_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Action extends Base {

    @NotNull
    @Column(nullable = false)
    private String name;

    private String description;

    @OneToMany(mappedBy = "action", fetch = FetchType.LAZY)
    private Set<ActionLog> personLogs = new HashSet<>();

    @Builder
    public Action(Long id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
    }
}
