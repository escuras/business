package business.converters;

import business.domain.Sample;
import business.dto.response.SampleResponse;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class SampleToSampleResponse implements Converter<Sample, SampleResponse> {

    @Nullable
    @Synchronized
    @Override
    public SampleResponse convert(final Sample domain) {
        if (domain == null) {
            return null;
        }

        return SampleResponse.builder()
                .id(domain.getId())
                .nome(domain.getName())
                .build();
    }
}
