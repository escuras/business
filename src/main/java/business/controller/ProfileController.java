package business.controller;

import business.api.Api;
import business.assembler.type.ProfileResponseAssembler;
import business.domain.Profile;
import business.dto.request.ProfileRequest;
import business.dto.response.ProfileResponse;
import business.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController implements Api<ProfileResponse, ProfileRequest> {

    private final ProfileService profileService;
    private final ProfileResponseAssembler assembler;
    private final ConversionService converterService;


    @GetMapping
    public ResponseEntity<CollectionModel<ProfileResponse>> getAll() {
        return ResponseEntity.ok(this.assembler.toCollectionModel(this.profileService.findAll()));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> getById(@PathVariable final Long id) {
        return this.profileService.findById(id)
                .map(op -> new ResponseEntity<>(this.assembler.toModel(op), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    @PostMapping
    public ResponseEntity<ProfileResponse> save(@RequestBody final ProfileRequest profile) {
        return ResponseEntity.ok(
                this.assembler.toModel(
                        this.profileService.create(this.converterService.convert(profile, Profile.class))));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable final Long id) {
        this.profileService.delete(id);
        return Api.super.deleteById(id);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @RequestBody @Valid final ProfileRequest request) {
        this.profileService.update(id, this.converterService.convert(request, Profile.class));
        return Api.super.update(id, request);
    }
}
