package business.controller;

import business.api.Api;
import business.assembler.type.PersonResponseAssembler;
import business.domain.Person;
import business.dto.request.PaginationRequest;
import business.dto.request.PersonRequest;
import business.dto.response.PersonResponse;
import business.service.PersonService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping(value = Constants.PERSON_API_PATH)
public class PersonController implements Api<PersonResponse, PersonRequest> {

    private final PersonService service;
    private final PersonResponseAssembler assembler;
    private final PagedResourcesAssembler pagedAssembler;
    private final ConversionService converterService;

    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    @Override
    public ResponseEntity<CollectionModel<PersonResponse>> getAll(final PaginationRequest pagination) {
        final Page<Person> persons = this.service.findByPageable(pagination);
        final Integer totalPages = persons.getTotalPages();

        if (totalPages.equals(Constants.TOTAL_PAGE_TO_CODE_200)) {
            return new ResponseEntity<>(this.pagedAssembler.toModel(persons, this.assembler), HttpStatus.OK);
        }
        final HttpHeaders header = ApiTools.createHeadersPaginacao(persons.getTotalElements(), pagination.getPage(), pagination.getSize(), persons.getContent().size());
        return new ResponseEntity<>(this.pagedAssembler.toModel(persons, this.assembler), header, HttpStatus.PARTIAL_CONTENT);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<PersonResponse> getById(@PathVariable final Long id) {
        return this.service.findById(id)
                .map(op -> new ResponseEntity<>(this.assembler.toModel(op), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Override
    public ResponseEntity<PersonResponse> save(@RequestBody final PersonRequest personRequest) {
        return ResponseEntity.ok(
                this.assembler.toModel(
                        this.service.create(
                                this.converterService.convert(personRequest, Person.class))));
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<?> deleteById(@PathVariable final Long id) {
        this.service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<?> update(@PathVariable final Long id,
                                    @RequestBody final PersonRequest personRequest) {
        this.service.update(id, this.converterService.convert(personRequest, Person.class));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    

}
