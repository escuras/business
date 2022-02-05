package business.service;

import business.domain.Company;
import business.domain.Person;
import business.dto.request.PaginationRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.Set;

public interface CompanyService {

    Optional<Company> findById(Long id);

    Company create(Company company);

    Page<Company> findByPageable(PaginationRequest pagination);

    void delete(Long id);

    void update(Long id, Company company);

    void addWorker(Long companyId, Long personId);

    Set<Person> getWorkers(Long companyId);

    void setOwner(Long companyId, Long personId);

    Optional<Person> getOwner(Long companyId);
}
