package business.service.impl;

import business.domain.Company;
import business.domain.Person;
import business.dto.request.PaginationRequest;
import business.exception.BusinessException;
import business.repository.CompanyRepository;
import business.service.CompanyService;
import business.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final PersonService personService;

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

    @Override
    public void addWorker(final Long companyId, final Long personId) {
        final Optional<Person> optionalPerson = this.personService.findById(personId);
        final Optional<Company> optionalCompany = this.findById(companyId);
        if (optionalCompany.isPresent() && optionalPerson.isPresent()) {
            final Company company = optionalCompany.get();
            company.getWorkers().add(optionalPerson.get());
            company.setWorkersNumber(company.getWorkers().size());
            this.companyRepository.save(company);
        }
    }

    @Override
    public Set<Person> getWorkers(final Long companyId) {
        final Optional<Company> optionalCompany = this.findById(companyId);
        if (optionalCompany.isPresent()) {
            final Company company = optionalCompany.get();
            return company.getWorkers();
        }
        return Collections.emptySet();
    }

    @Override
    public void setOwner(final Long companyId, final Long personId) {
        final Optional<Person> optionalPerson = this.personService.findById(personId);
        final Optional<Company> optionalCompany = this.findById(companyId);
        if (optionalCompany.isPresent() && optionalPerson.isPresent()) {
            final Company company = optionalCompany.get();
            company.setPerson(optionalPerson.get());
            this.companyRepository.save(company);
        }
    }

    @Override
    public Optional<Person> getOwner(final Long companyId) {
        final Optional<Company> optionalCompany = this.findById(companyId);
        if (optionalCompany.isPresent()) {
            return Optional.ofNullable(optionalCompany.get().getPerson());
        }
        return Optional.empty();
    }
}
