package business.domain;

import business.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserProfile> userProfiles = new ArrayList<>();

    @PrePersist
    private void addInclusionDate() {
        this.setInclusionDate(LocalDateTime.now());
    }

    public Profile getActiveProfile() {
        if (!CollectionUtils.isEmpty(this.userProfiles)) {
            final Optional<UserProfile> optionalUserProfile = this.userProfiles.stream().filter(e -> e.isActive()).findFirst();
            if (optionalUserProfile.isPresent()) {
                return optionalUserProfile.get().getProfile();
            }
        }
        return null;
    }

}
