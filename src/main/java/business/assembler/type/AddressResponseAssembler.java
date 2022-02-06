package business.assembler.type;

import business.assembler.ResponseAssembler;
import business.controller.AddressController;
import business.domain.Address;
import business.dto.response.AddressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
@RequiredArgsConstructor
public class AddressResponseAssembler implements RepresentationModelAssembler<Address, AddressResponse> {

    private final ConversionService conversionService;

    @Override
    public AddressResponse toModel(final Address entity) {
        final ResponseAssembler<Address, AddressResponse> assembler = new ResponseAssembler(AddressController.class, AddressResponse.class, this.conversionService);
        return assembler.toModel(entity)
                .add(linkTo(assembler.getController()).slash(entity.getId()).withSelfRel());
    }
}
