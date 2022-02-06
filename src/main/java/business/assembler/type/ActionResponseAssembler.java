package business.assembler.type;

import business.assembler.ResponseAssembler;
import business.controller.ActionController;
import business.domain.Action;
import business.dto.response.ActionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
@RequiredArgsConstructor
public class ActionResponseAssembler implements RepresentationModelAssembler<Action, ActionResponse> {

    private final ConversionService conversionService;

    @Override
    public ActionResponse toModel(final Action action) {
        final ResponseAssembler<Action, ActionResponse> assembler = new ResponseAssembler(ActionController.class, ActionResponse.class, this.conversionService);
        return assembler.toModel(action)
                .add(linkTo(assembler.getController()).slash(action.getId()).withSelfRel());
    }
}
