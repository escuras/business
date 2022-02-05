package business.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import business.domain.Sample;
import business.dto.request.SampleRequest;

@Component
public class SampleRequestToSample implements Converter<SampleRequest, Sample> {

    @Nullable
    @Synchronized
    @Override
    public Sample convert(SampleRequest request) {
        if (request == null) {
            return null;
        }
        return Sample.builder()
                .id(request.getId())
                .name(request.getName())
                .build();
    }
}
