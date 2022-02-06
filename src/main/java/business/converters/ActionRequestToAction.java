package business.converters;

import business.domain.Action;
import business.dto.request.ActionRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ActionRequestToAction implements Converter<ActionRequest, Action> {

    @Override
    public Action convert(final ActionRequest source) {
        return Action.builder()
                .name(source.getName())
                .description(source.getDescription())
                .build();
    }
}
