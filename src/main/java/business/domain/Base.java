package business.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class Base {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
