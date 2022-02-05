package business.assembler.type;

import business.assembler.ResponseAssembler;
import business.controller.UserController;
import business.domain.User;
import business.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequiredArgsConstructor
public class UserResponseAssembler implements RepresentationModelAssembler<User, UserResponse> {

    private final ConversionService conversionService;

    @Override
    public UserResponse toModel(final User entity) {
        final ResponseAssembler<User, UserResponse> assembler = new ResponseAssembler(UserController.class,
                UserResponse.class, this.conversionService);
        return assembler.toModel(entity)
                .add(linkTo(assembler.getController()).slash(entity.getId()).withSelfRel());
    }
}
