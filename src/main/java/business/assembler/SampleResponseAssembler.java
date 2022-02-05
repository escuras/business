package business.assembler;

import business.controller.SampleController;
import business.domain.Sample;
import business.dto.response.SampleResponse;
import org.springframework.core.convert.ConversionService;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class SampleResponseAssembler extends RepresentationModelAssemblerSupport<Sample, SampleResponse> {

    private final ConversionService conversionService;

    public SampleResponseAssembler(final ConversionService conversionService) {
        super(SampleController.class, SampleResponse.class);
        this.conversionService = conversionService;
    }

    @Override
    public SampleResponse toModel(final Sample domain) {
        final SampleResponse response = this.conversionService.convert(domain, SampleResponse.class);
        response.add(linkTo(this.getControllerClass()).slash(domain.getId()).withSelfRel());
        return response;
    }
}
