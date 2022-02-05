package business.assembler.type;

import business.assembler.ResponseAssembler;
import business.controller.PersonController;
import business.domain.Person;
import business.dto.response.PersonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
@RequiredArgsConstructor
public class PersonResponseAssembler implements RepresentationModelAssembler<Person, PersonResponse> {

    private final ConversionService conversionService;

    @Override
    public PersonResponse toModel(final Person entity) {
        final ResponseAssembler<Person, PersonResponse> assembler = new ResponseAssembler(PersonController.class, PersonResponse.class, this.conversionService);
        return assembler.toModel(entity)
                .add(linkTo(assembler.getController()).slash(entity.getId()).withSelfRel());
    }
}
