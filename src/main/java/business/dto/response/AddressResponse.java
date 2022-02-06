package business.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse extends RepresentationModel<AddressResponse> {

    private Long id;
    private Long cep;
    private String local;
    private String number;
    private String complement;
    private String district;
    private String city;
    private String uf;
    private String country;
    private List<Long> userIds;
    private LocalDateTime inclusionDate;
}
