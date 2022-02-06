package business.converters;

import business.domain.Sample;
import business.dto.request.SampleRequest;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class SampleRequestToSample implements Converter<SampleRequest, Sample> {

    @Nullable
    @Synchronized
    @Override
    public Sample convert(final SampleRequest request) {
        if (request == null) {
            return null;
        }
        return Sample.builder()
                .id(request.getId())
                .name(request.getName())
                .build();
    }
}
