package business.converters;

import business.domain.Person;
import business.domain.User;
import business.dto.response.UserResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserToUserResponse implements Converter<User, UserResponse> {

    @Override
    public UserResponse convert(final User source) {
        if (null == source) {
            return null;
        }
        return UserResponse.builder()
                .id(source.getId())
                .active(source.isActive())
                .document(source.getDocument())
                .email(source.getEmail())
                .inclusionDate(source.getInclusionDate())
                .personId(Optional.ofNullable(source.getPerson()).orElse(new Person()).getId())
                .build();
    }
}
