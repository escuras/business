package business.assembler.type;

import business.assembler.ResponseAssembler;
import business.controller.ProfileController;
import business.domain.Profile;
import business.dto.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
@RequiredArgsConstructor
public class ProfileResponseAssembler implements RepresentationModelAssembler<Profile, ProfileResponse> {

    private final ConversionService conversionService;

    @Override
    public ProfileResponse toModel(final Profile entity) {
        final ResponseAssembler<Profile, ProfileResponse> assembler = new ResponseAssembler(ProfileController.class, ProfileResponse.class, this.conversionService);
        return assembler.toModel(entity)
                .add(linkTo(assembler.getController()).slash(entity.getId()).withSelfRel());
    }
}
