package business.service;

import business.domain.Company;
import business.dto.request.PaginationRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface CompanyService {

    Optional<Company> findById(Long id);

    Company create(Company company);

    Page<Company> findByPageable(PaginationRequest pagination);

    void delete(Long id);

    void update(Long id, Company company);
}
