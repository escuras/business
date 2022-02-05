package business.converters;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import business.domain.Sample;
import business.dto.request.SampleRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SampleRequestToSampleTest {

	@Autowired
	private SampleRequestToSample converter;
	
	@Test
	public void converterRequestTest() {
		SampleRequest objRequest = SampleRequest.builder().id(1L).build();
		Sample expected = converter.convert(objRequest);
		assertEquals(expected.getId(), objRequest.getId());
	}
}
