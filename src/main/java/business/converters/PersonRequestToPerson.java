package business.converters;

import business.domain.Address;
import business.domain.Person;
import business.dto.request.PersonRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PersonRequestToPerson implements Converter<PersonRequest, Person> {

    @Override
    public Person convert(final PersonRequest source) {
        if (source == null) {
            return null;
        }

        return Person.builder()
                .name(source.getName())
                .active(source.isActive())
                .email(source.getEmail())
                .document(source.getDocument())
                .address(this.address(source.getAddressId()))
                .build();
    }

    private Address address(final Long id) {
        if (null == id) {
            return null;
        }
        final Address address = new Address();
        address.setId(id);
        return address;
    }
}
