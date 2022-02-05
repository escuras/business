package business.assembler.type;

import business.assembler.ResponseAssembler;
import business.controller.CompanyController;
import business.domain.Company;
import business.dto.response.CompanyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RequiredArgsConstructor
@Component
public class CompanyResponseAssembler implements RepresentationModelAssembler<Company, CompanyResponse> {

    private final ConversionService conversionService;

    @Override
    public CompanyResponse toModel(final Company entity) {
        final ResponseAssembler<Company, CompanyResponse> assembler = new ResponseAssembler(CompanyController.class, CompanyResponse.class, this.conversionService);
        return assembler.toModel(entity)
                .add(linkTo(assembler.getController()).slash(entity.getId()).withSelfRel());
    }


}
