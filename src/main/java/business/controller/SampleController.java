package business.controller;

import business.api.Api;
import business.assembler.SampleResponseAssembler;
import business.domain.Sample;
import business.dto.request.PaginationRequest;
import business.dto.request.SampleRequest;
import business.dto.response.SampleResponse;
import business.service.SampleService;
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

import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = Constants.BASE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class SampleController implements Api<SampleResponse, SampleRequest> {

    private final SampleService service;
    private final SampleResponseAssembler assembler;
    private final PagedResourcesAssembler pagedAssembler;
    private final ConversionService converterService;

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<SampleResponse> getById(@PathVariable("id") final Long id) {
        // get
        final Optional<Sample> optionalSample = this.service.findById(id);

        //verifico se existe
        if (optionalSample.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //converto
        final Sample sample = optionalSample.get();
        final SampleResponse sampleConvertido = this.assembler.toModel(sample);

        // retorno
        return new ResponseEntity<>(sampleConvertido, HttpStatus.OK);

    }

    @GetMapping
    @Override
    public ResponseEntity<CollectionModel<SampleResponse>> getAll(final PaginationRequest pagination) {

        final Page<Sample> samples = this.service.findByPageable(pagination);
        final Integer totalPages = samples.getTotalPages();

        if (totalPages.equals(Constants.TOTAL_PAGE_TO_CODE_200)) {
            return new ResponseEntity<>(this.pagedAssembler.toModel(samples, this.assembler), HttpStatus.OK);
        }
        final HttpHeaders header = ApiTools.createHeadersPaginacao(samples.getTotalElements(), pagination.getPage(), pagination.getSize(), samples.getContent().size());
        return new ResponseEntity<>(this.pagedAssembler.toModel(samples, this.assembler), header, HttpStatus.PARTIAL_CONTENT);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<SampleResponse> save(@RequestBody(required = true) final SampleRequest request) {
        return new ResponseEntity<>(this.assembler.toModel(this.service.save(this.converterService.convert(request, Sample.class))), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> deleteById(@PathVariable("id") final Long id) {
        this.service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<SampleResponse> update(@NotNull @PathVariable("id") final Long id,
                                                 @NotNull @RequestBody final SampleRequest sampleRequest) {
        this.service.update(id, this.converterService.convert(sampleRequest, Sample.class));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
