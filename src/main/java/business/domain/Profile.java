package business.domain;

import business.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = Constants.TABLE_PROFILE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile extends Base {

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    private String description;

}
