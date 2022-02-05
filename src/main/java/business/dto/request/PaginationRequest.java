package business.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import business.util.Constants;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginationRequest {
    private Long totalElementos;

    @Builder.Default
    private Integer page = Integer.parseInt(Constants.DEFAULT_PAGE_INITAL);

    @Builder.Default
    private Integer size = Integer.parseInt(Constants.DEFAULT_PAGE_SIZE);

    private Integer tamanhoLista;
}
