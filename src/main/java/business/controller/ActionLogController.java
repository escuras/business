package business.controller;

import business.assembler.type.ActionLogResponseAssembler;
import business.domain.ActionLog;
import business.dto.response.ActionLogResponse;
import business.service.ActionLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/log")
public class ActionLogController {

    private final ActionLogService actionLogService;
    private final ActionLogResponseAssembler actionLogResponseAssembler;

    @GetMapping
    public ResponseEntity<CollectionModel<ActionLogResponse>> getLogsBetween(@RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime before,
                                                                             @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime after) {
        final List<ActionLog> actionLogs = this.actionLogService.getLogs(after, before);
        return ResponseEntity.ok(this.actionLogResponseAssembler.toCollectionModel(actionLogs));
    }

    @GetMapping("/action/{actionName}")
    public ResponseEntity<CollectionModel<ActionLogResponse>> getLogsBetweenByAction(@PathVariable final String actionName,
                                                                                     @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime before,
                                                                                     @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime after) {
        final List<ActionLog> actionLogs = this.actionLogService.getLogsByAction(actionName, after, before);
        return ResponseEntity.ok(this.actionLogResponseAssembler.toCollectionModel(actionLogs));
    }

    @GetMapping("/person/{personId}")
    public ResponseEntity<CollectionModel<ActionLogResponse>> getLogsBetweenByPerson(@PathVariable final Long personId,
                                                                                     @RequestParam final @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime before,
                                                                                     @RequestParam final @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime after) {
        final List<ActionLog> actionLogs = this.actionLogService.getPersonLogs(personId, after, before);
        return ResponseEntity.ok(this.actionLogResponseAssembler.toCollectionModel(actionLogs));
    }

    @PostMapping
    public ResponseEntity<?> getLogsBetweenByPerson(@RequestParam final Long userId,
                                                    @RequestParam final String actionName,
                                                    @RequestParam final String description,
                                                    @RequestParam final String reason) {
        this.actionLogService.test(userId, actionName, description, reason);
        return ResponseEntity.noContent().build();
    }
}
