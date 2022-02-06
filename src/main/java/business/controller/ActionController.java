package business.controller;

import business.api.Api;
import business.assembler.type.ActionResponseAssembler;
import business.domain.Action;
import business.dto.request.ActionRequest;
import business.dto.request.PaginationRequest;
import business.dto.response.ActionResponse;
import business.service.ActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/action")
public class ActionController implements Api<ActionResponse, ActionRequest> {

    private final ActionService actionService;
    private final ActionResponseAssembler assembler;
    private final ConversionService conversionService;

    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<ActionResponse>> getAll(final PaginationRequest pagination) {
        return ResponseEntity.ok(this.assembler.toCollectionModel(this.actionService.getAll()));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ActionResponse> getById(@PathVariable final Long id) {
        return this.actionService.findById(id)
                .map(e -> new ResponseEntity<>(this.assembler.toModel(e), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ActionResponse> getByName(@PathVariable final String name) {
        return this.actionService.findByName(name)
                .map(e -> new ResponseEntity<>(this.assembler.toModel(e), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @Override
    @PostMapping
    public ResponseEntity<ActionResponse> save(@RequestBody final ActionRequest request) {
        return new ResponseEntity<>(
                this.assembler.toModel(
                        this.actionService.create(
                                this.conversionService.convert(request, Action.class))), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable final Long id) {
        this.actionService.delete(id);
        return Api.super.deleteById(id);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @RequestBody final ActionRequest request) {
        this.actionService.update(id, this.conversionService.convert(request, Action.class));
        return Api.super.update(id, request);
    }
}
