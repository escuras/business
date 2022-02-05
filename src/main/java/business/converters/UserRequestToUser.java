package business.converters;

import business.domain.Person;
import business.domain.User;
import business.dto.request.UserRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserRequestToUser implements Converter<UserRequest, User> {

    @Override
    public User convert(final UserRequest source) {

        if (null == source) {
            return null;
        }

        return User.builder()
                .email(source.getEmail())
                .active(source.isActive())
                .document(source.getDocument())
                .person(this.person(source.getPersonId()))
                .build();
    }

    private Person person(final Long id) {
        final Person person = new Person();
        person.setId(id);
        return person;
    }
}
