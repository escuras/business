package business.converters;

import business.domain.Address;
import business.domain.Person;
import business.dto.response.PersonResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PersonToPersonResponse implements Converter<Person, PersonResponse> {

    @Override
    public PersonResponse convert(final Person source) {

        if (source == null) {
            return null;
        }

        return PersonResponse.builder()
                .active(source.isActive())
                .id(source.getId())
                .email(source.getEmail())
                .document(source.getDocument())
                .inclusionDate(source.getInclusionDate())
                .name(source.getName())
                .addressId(Optional.ofNullable(source.getAddress()).orElse(new Address()).getId())
                .build();
    }
}
