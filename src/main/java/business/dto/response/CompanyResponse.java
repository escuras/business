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
public class CompanyResponse extends RepresentationModel<CompanyResponse> {

    private Long id;
    private String sector;
    private long workersNumber;
    private String hrResponsible;
    private String financesResponsible;
    private String financesEmail;
    private LocalDateTime inclusionDate;
    private Long personId;

}
