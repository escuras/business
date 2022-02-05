package business.converters;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import business.domain.Sample;
import business.dto.response.SampleResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SampleToSampleResponseTest {

	@Autowired
	private SampleToSampleResponse converter;
	
	@Test
	public void converterRequestTest() {
		Sample objRequest = Sample.builder().id(1L).build();
		SampleResponse expected = converter.convert(objRequest);
		assertEquals(expected.getId(), objRequest.getId());
	}
}
