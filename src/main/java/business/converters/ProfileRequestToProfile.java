package business.converters;

import business.domain.Profile;
import business.dto.request.ProfileRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProfileRequestToProfile implements Converter<ProfileRequest, Profile> {

    @Override
    public Profile convert(final ProfileRequest source) {

        if (source == null) {
            return null;
        }

        return Profile.builder()
                .name(source.getName())
                .description(source.getDescription())
                .build();
    }
}
