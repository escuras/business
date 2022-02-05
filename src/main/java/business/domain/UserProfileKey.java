package business.domain;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class UserProfileKey implements Serializable {

    @Column(name = "profile_id")
    private Long profileId;

    @Column(name = "user_id")
    private Long userId;

}
