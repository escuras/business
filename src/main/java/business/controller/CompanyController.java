package business.controller;

import business.api.Api;
import business.assembler.type.CompanyResponseAssembler;
import business.assembler.type.PersonResponseAssembler;
import business.domain.Company;
import business.dto.request.CompanyRequest;
import business.dto.request.PaginationRequest;
import business.dto.response.CompanyResponse;
import business.dto.response.PersonResponse;
import business.service.CompanyService;
import business.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/company")
public class CompanyController implements Api<CompanyResponse, CompanyRequest> {

    private final CompanyService companyService;
    private final CompanyResponseAssembler assembler;
    private final PersonResponseAssembler personResponseAssembler;
    private final PagedResourcesAssembler pagedAssembler;
    private final ConversionService conversionService;

    @Override
    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<CollectionModel<CompanyResponse>> getAll(final PaginationRequest pagination) {
        final Page<Company> companies = this.companyService.findByPageable(pagination);
        final Integer totalPages = companies.getTotalPages();

        if (totalPages.equals(Constants.TOTAL_PAGE_TO_CODE_200)) {
            return new ResponseEntity<>(this.pagedAssembler.toModel(companies, this.assembler), HttpStatus.OK);
        }
        final HttpHeaders header = ApiTools.createHeadersPaginacao(companies.getTotalElements(), pagination.getPage(), pagination.getSize(), companies.getContent().size());
        return new ResponseEntity<>(this.pagedAssembler.toModel(companies, this.assembler), header, HttpStatus.PARTIAL_CONTENT);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getById(@PathVariable final Long id) {
        return this.companyService.findById(id)
                .map(op -> new ResponseEntity<>(this.assembler.toModel(op), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    @PostMapping
    public ResponseEntity<CompanyResponse> save(@RequestBody final CompanyRequest request) {
        return ResponseEntity.ok(
                this.assembler.toModel(
                        this.companyService.create(
                                this.conversionService.convert(request, Company.class))));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable final Long id) {
        this.companyService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @RequestBody final CompanyRequest request) {
        this.companyService.update(id, this.conversionService.convert(request, Company.class));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/{companyId}/worker/{personId}")
    public ResponseEntity<?> addWorker(@PathVariable final Long companyId, @PathVariable final Long personId) {
        this.companyService.addWorker(companyId, personId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{companyId}/owner/{personId}")
    public ResponseEntity<?> setOwner(@PathVariable final Long companyId, @PathVariable final Long personId) {
        this.companyService.setOwner(companyId, personId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{companyId}/owner")
    public ResponseEntity<PersonResponse> getOwner(@PathVariable final Long companyId) {
        return this.companyService.getOwner(companyId)
                .map(e -> new ResponseEntity<>(this.personResponseAssembler.toModel(e), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{companyId}/workers")
    public ResponseEntity<CollectionModel<PersonResponse>> getWorkers(@PathVariable final Long companyId) {
        return ResponseEntity.ok(this.personResponseAssembler.toCollectionModel(this.companyService.getWorkers(companyId)));
    }

}
