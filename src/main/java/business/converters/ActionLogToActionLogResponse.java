package business.converters;

import business.domain.ActionLog;
import business.dto.response.ActionLogResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ActionLogToActionLogResponse implements Converter<ActionLog, ActionLogResponse> {

    @Override
    public ActionLogResponse convert(final ActionLog source) {
        return ActionLogResponse.builder()
                .actionName(source.getAction().getName())
                .description(source.getDescription())
                .inclusionDate(source.getInclusionDate())
                .profileName(source.getProfileName())
                .personId(source.getPerson().getId())
                .reason(source.getReason())
                .userId(source.getUserId())
                .build();
    }
}
