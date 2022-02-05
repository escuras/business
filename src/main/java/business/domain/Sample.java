package business.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import business.util.Constants;

import javax.persistence.*;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = Constants.TABLE_NAME)
public class Sample extends Base {

    @Column(name = "NAME")
    private String name;

    @Builder
    public Sample(Long id, String name) {
        super(id);
        this.name = name;
    }

}
