package business.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import business.domain.Sample;
import business.dto.request.PaginationRequest;
import business.repository.SampleRepository;
import business.service.impl.SampleServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SampleServiceTest {

	@InjectMocks
	private SampleServiceImpl service;

	@Mock
	private SampleRepository repository;

	@Test
	public void findByIdTest() throws Exception {
		Optional<Sample> expectedSample = Optional.of(Sample.builder().id(1L).name("name").build());
		when(repository.findById(1L)).thenReturn(expectedSample);
		Optional<Sample> actualSample = service.findById(1L);
		assertEquals(actualSample.get().getId(), expectedSample.get().getId());
		assertEquals(actualSample.get().getName(), expectedSample.get().getName());
	}

	@Test
	public void saveTest() throws Exception {
		Sample expectedSample = Sample.builder().id(1L).name("name").build();
		when(repository.save(expectedSample)).thenReturn(expectedSample);
		Sample actualSample = service.save(expectedSample);
		assertEquals(actualSample.getId(), expectedSample.getId());
		assertEquals(actualSample.getName(), expectedSample.getName());
	}

	@Test
	public void findByPageableTest() throws Exception {
		List<Sample> samples = Arrays.asList(Sample.builder().id(1L).name("name 1").build(),
				Sample.builder().id(2L).name("name 2").build());
		when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<Sample>(samples));
		Page<Sample> actualSample = service.findByPageable(PaginationRequest.builder().page(1).size(2).build());
		assertEquals(2, actualSample.getNumberOfElements());
	}
	
	@Test
	public void deleteTest() throws Exception {
		doNothing().when(this.repository).deleteById(1L);
		when(this.repository.existsById(1L)).thenReturn(true);
		service.delete(1L);
		verify(this.repository, times(1)).deleteById(1L);
	}
	
	@Test
	public void updateTest() throws Exception {
		Sample expectedSampleAfterUpdate = Sample.builder().id(1L).name("alterado").build();
		when(this.repository.existsById(1L)).thenReturn(true);
		when(repository.save(expectedSampleAfterUpdate)).thenReturn(expectedSampleAfterUpdate);
		service.update(1L, expectedSampleAfterUpdate);
		verify(this.repository, times(1)).save(expectedSampleAfterUpdate);
	}
}
