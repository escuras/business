package business.service.impl;

import business.domain.Person;
import business.dto.request.PaginationRequest;
import business.exception.BusinessException;
import business.repository.PersonRepository;
import business.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public Optional<Person> findById(@NotNull(message = "Id can't be null.") final Long id) {
        return this.personRepository.findById(id);
    }

    @Override
    @Transactional
    public Person create(@NotNull(message = "Person can't be null.") final Person person) {
        if (this.personRepository.existsByNameAndEmail(person.getName(), person.getEmail())) {
            throw new BusinessException("User already exists");
        }
        person.setId(null);
        return this.personRepository.save(person);
    }

    @Override
    public Page<Person> findByPageable(@NotNull(message = "Pagination can't be null.") final PaginationRequest pagination) {
        if (pagination.getSize() < 1) {
            pagination.setSize(1);
        }
        return this.personRepository.findAll(PageRequest.of(pagination.getPage(), pagination.getSize()));
    }

    @Override
    @Transactional
    public void delete(@NotNull(message = "Id can't be null.") final Long id) {
        this.personRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(@NotNull(message = "Id can't be null.") final Long id,
                       @NotNull(message = "Person can't be null.") final Person person) {
        final Person dbPerson = this.personRepository.findById(id).orElseGet(() -> {
            throw new BusinessException("User does not exist.");
        });
        dbPerson.setActive(person.isActive());
        dbPerson.setDocument(person.getDocument());
        dbPerson.setEmail(person.getEmail());
        dbPerson.setName(person.getName());
        dbPerson.setAddress(person.getAddress());
    }

    @Override
    @Transactional
    public void setActive(final Long id, final boolean active) {
        final Person dbPerson = this.personRepository.findById(id).orElseGet(() -> {
            throw new BusinessException("User does not exist.");
        });
        dbPerson.setActive(active);
    }
}
