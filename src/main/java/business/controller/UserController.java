package business.controller;

import business.api.Api;
import business.assembler.type.ProfileResponseAssembler;
import business.assembler.type.UserResponseAssembler;
import business.domain.User;
import business.dto.request.PaginationRequest;
import business.dto.request.UserRequest;
import business.dto.response.ProfileResponse;
import business.dto.response.UserResponse;
import business.service.UserService;
import business.util.ApiTools;
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

@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController implements Api<UserResponse, UserRequest> {

    private final UserService userService;
    private final UserResponseAssembler assembler;
    private final ProfileResponseAssembler profileResponseAssembler;
    private final PagedResourcesAssembler pagedAssembler;
    private final ConversionService converterService;

    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<UserResponse>> getAll(final PaginationRequest pagination) {
        final Page<User> users = this.userService.findByPageable(pagination);
        final Integer totalPages = users.getTotalPages();

        if (totalPages.equals(Constants.TOTAL_PAGE_TO_CODE_200)) {
            return new ResponseEntity<>(this.pagedAssembler.toModel(users, this.assembler), HttpStatus.OK);
        }
        final HttpHeaders header = ApiTools.createHeadersWithPagination(users.getTotalElements(), pagination.getPage(), pagination.getSize(), users.getContent().size());
        return new ResponseEntity<>(this.pagedAssembler.toModel(users, this.assembler), header, HttpStatus.PARTIAL_CONTENT);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable @NotNull final Long id) {
        return this.userService.findById(id)
                .map(op -> new ResponseEntity<>(this.assembler.toModel(op), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    @PostMapping
    public ResponseEntity<UserResponse> save(@RequestBody final UserRequest request) {
        return ResponseEntity.ok(
                this.assembler.toModel(
                        this.userService.create(this.converterService.convert(request, User.class))));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable @NotNull final Long id) {
        this.userService.delete(id);

        // call Api default method: no content
        return Api.super.deleteById(id);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable @NotNull final Long id,
                                    @RequestBody @NotNull final UserRequest request) {
        this.userService.update(id, this.converterService.convert(request, User.class));

        // call Api default method: no content
        return Api.super.update(id, request);
    }

    @PutMapping("/{userId}/profile/{profileName}")
    public ResponseEntity<?> setProfileActive(@PathVariable @NotNull final Long userId, @PathVariable @NotNull final String profileName) {
        this.userService.profileActive(userId, profileName);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}/profile/add/{profileName}")
    public ResponseEntity<?> addProfile(@PathVariable @NotNull final Long userId, @PathVariable @NotNull final String profileName) {
        this.userService.addProfile(userId, profileName);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<CollectionModel<ProfileResponse>> getUserProfiles(@PathVariable @NotNull final Long userId) {
        return ResponseEntity.ok(this.profileResponseAssembler.toCollectionModel(this.userService.getUSerProfiles(userId)));
    }

    @GetMapping("/{userId}/profile/active")
    public ResponseEntity<ProfileResponse> getActiveProfile(@PathVariable @NotNull final Long userId) {
        return ResponseEntity.ok(this.profileResponseAssembler.toModel(this.userService.getUserActiveProfile(userId)));
    }

    @DeleteMapping("/{userId}/profile/{profileName}")
    public ResponseEntity<ProfileResponse> deleteProfile(@PathVariable @NotNull final Long userId, @PathVariable @NotNull final String profileName) {
        this.userService.deleteProfile(userId, profileName);
        return ResponseEntity.noContent().build();
    }
}
