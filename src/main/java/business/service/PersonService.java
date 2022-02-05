package business.service;

import business.domain.Person;
import business.dto.request.PaginationRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface PersonService {

    Optional<Person> findById(Long id);

    Person create(Person person);

    Page<Person> findByPageable(PaginationRequest pagination);

    void delete(Long id);

    void update(Long id, Person person);

}
