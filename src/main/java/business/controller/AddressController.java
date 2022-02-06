package business.controller;

import business.api.Api;
import business.assembler.type.AddressResponseAssembler;
import business.assembler.type.PersonResponseAssembler;
import business.domain.Address;
import business.dto.request.AddressRequest;
import business.dto.request.PaginationRequest;
import business.dto.response.AddressResponse;
import business.service.AddressService;
import business.util.ApiTools;
import business.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/address")
public class AddressController implements Api<AddressResponse, AddressRequest> {

    private final AddressService addressService;
    private final AddressResponseAssembler assembler;
    private final PersonResponseAssembler personResponseAssembler;
    private final PagedResourcesAssembler pagedAssembler;
    private final ConversionService conversionService;

    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<AddressResponse>> getAll(final PaginationRequest pagination) {
        final Page<Address> addresses = this.addressService.findByPageable(pagination);
        final Integer totalPages = addresses.getTotalPages();

        if (totalPages.equals(Constants.TOTAL_PAGE_TO_CODE_200)) {
            return new ResponseEntity<>(this.pagedAssembler.toModel(addresses, this.assembler), HttpStatus.OK);
        }
        final HttpHeaders header = ApiTools.createHeadersWithPagination(addresses.getTotalElements(), pagination.getPage(), pagination.getSize(), addresses.getContent().size());
        return new ResponseEntity<>(this.pagedAssembler.toModel(addresses, this.assembler), header, HttpStatus.PARTIAL_CONTENT);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getById(@PathVariable final Long id) {
        return this.addressService.findById(id)
                .map(e -> new ResponseEntity<>(this.assembler.toModel(e), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @GetMapping("/cep/{cep}")
    public ResponseEntity<AddressResponse> getByCep(@PathVariable final String cep) {
        return this.addressService.getByCep(cep)
                .map(e -> new ResponseEntity<>(this.assembler.toModel(e), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PutMapping("/{addressId}/person/{personId}")
    public ResponseEntity<?> setPerson(@PathVariable final Long addressId,
                                       @PathVariable final Long personId) {
        this.addressService.setPersonAddress(addressId, personId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping
    public ResponseEntity<AddressResponse> save(@RequestBody final AddressRequest request) {
        return new ResponseEntity<>(
                this.assembler.toModel(
                        this.addressService.create(
                                this.conversionService.convert(request, Address.class))), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable final Long id) {
        this.addressService.delete(id);
        return Api.super.deleteById(id);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @RequestBody final AddressRequest request) {
        this.addressService.update(id, this.conversionService.convert(request, Address.class));
        return Api.super.update(id, request);
    }
}
