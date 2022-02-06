package business.converters;

import business.domain.Sample;
import business.dto.request.SampleRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SampleRequestToSampleTest {

    @Autowired
    private SampleRequestToSample converter;

    @Test
    public void converterRequestTest() {
        final SampleRequest objRequest = SampleRequest.builder().id(1L).build();
        final Sample expected = this.converter.convert(objRequest);
        assertEquals(expected.getId(), objRequest.getId());
    }
}
