package business.assembler.type;

import business.assembler.ResponseAssembler;
import business.controller.ActionLogController;
import business.domain.ActionLog;
import business.dto.response.ActionLogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
@RequiredArgsConstructor
public class ActionLogResponseAssembler implements RepresentationModelAssembler<ActionLog, ActionLogResponse> {

    private final ConversionService conversionService;

    @Override
    public ActionLogResponse toModel(final ActionLog actionLog) {
        final ResponseAssembler<ActionLog, ActionLogResponse> assembler = new ResponseAssembler(ActionLogController.class, ActionLogResponse.class, this.conversionService);
        return assembler.toModel(actionLog)
                .add(linkTo(assembler.getController()).slash(actionLog.getId()).withSelfRel());
    }
}
