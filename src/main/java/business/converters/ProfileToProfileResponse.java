package business.converters;

import business.domain.Profile;
import business.dto.response.ProfileResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProfileToProfileResponse implements Converter<Profile, ProfileResponse> {

    @Override
    public ProfileResponse convert(final Profile source) {

        if (source == null) {
            return null;
        }

        return ProfileResponse.builder()
                .id(source.getId())
                .name(source.getName())
                .description(source.getDescription())
                .build();
    }
}
