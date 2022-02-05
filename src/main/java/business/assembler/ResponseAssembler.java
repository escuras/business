package business.assembler;

import org.springframework.core.convert.ConversionService;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class ResponseAssembler<S, T extends RepresentationModel<? extends T>> extends RepresentationModelAssemblerSupport<S, T> {

    private final ConversionService conversionService;
    private final Class<T> resourceType;

    public ResponseAssembler(final Class<?> controllerClass,
                             final Class<T> resourceType,
                             final ConversionService conversionService) {
        super(controllerClass, resourceType);
        this.resourceType = resourceType;
        this.conversionService = conversionService;
    }

    @Override
    public T toModel(final S entity) {
        return this.conversionService.convert(entity, this.resourceType);
    }

    public Class<?> getController() {
        return super.getControllerClass();
    }
}
