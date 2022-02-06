package business.converters;

import business.domain.Action;
import business.dto.response.ActionResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ActionToActionResponse implements Converter<Action, ActionResponse> {

    @Override
    public ActionResponse convert(final Action source) {
        return ActionResponse.builder()
                .id(source.getId())
                .name(source.getName())
                .description(source.getDescription())
                .build();
    }
}
