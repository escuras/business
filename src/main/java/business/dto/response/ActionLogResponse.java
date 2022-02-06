package business.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActionLogResponse extends RepresentationModel<ActionLogResponse> {
    private Long personId;
    private String actionName;
    private String description;
    private String reason;
    private Long userId;
    private String profileName;
    private LocalDateTime inclusionDate;
}
