package business.converters;

import business.domain.Sample;
import business.dto.response.SampleResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SampleToSampleResponseTest {

    @Autowired
    private SampleToSampleResponse converter;

    @Test
    public void converterRequestTest() {
        final Sample objRequest = Sample.builder().id(1L).build();
        final SampleResponse expected = this.converter.convert(objRequest);
        assertEquals(expected.getId(), objRequest.getId());
    }
}
