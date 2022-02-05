package business.service.impl;

import business.domain.Company;
import business.dto.request.PaginationRequest;
import business.exception.BusinessException;
import business.repository.CompanyRepository;
import business.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public Optional<Company> findById(@NotNull(message = "Id can't be null.") final Long id) {
        return this.companyRepository.findById(id);
    }

    @Override
    public Company create(@NotNull(message = "Company can't be null.") final Company company) {
        company.setInclusionDate(LocalDateTime.now());
        return this.companyRepository.save(company);
    }

    @Override
    public Page<Company> findByPageable(@NotNull(message = "Pagination can't be null") final PaginationRequest pagination) {
        if (pagination.getSize() < 1) {
            pagination.setSize(1);
        }
        return this.companyRepository.findAll(PageRequest.of(pagination.getPage(), pagination.getSize()));
    }

    @Override
    public void delete(@NotNull(message = "Id can't be null.") final Long id) {
        this.companyRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(@NotNull(message = "Id can't be null.") final Long id,
                       @NotNull(message = "Company can't be null") final Company company) {
        final Company dbCompany = this.companyRepository.findById(id).orElseGet(() -> {
            throw new BusinessException("Company doesn't exist.");
        });
        dbCompany.setFinancesEmail(company.getFinancesEmail());
        dbCompany.setHrResponsible(company.getHrResponsible());
        dbCompany.setSector(company.getSector());
        dbCompany.setFinancesResponsible(company.getFinancesResponsible());
    }
}
