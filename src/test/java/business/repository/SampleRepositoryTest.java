package business.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import business.domain.Sample;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
public class SampleRepositoryTest {

	@Autowired
	private TestEntityManager testEntityManager;

	@Autowired
	private SampleRepository repository;

	@BeforeEach
	public void setup() {
		testEntityManager.persist(Sample.builder().name("name1").build());
	}
	
	@AfterEach
	public void tearDown() {
		testEntityManager.clear();
	}
	
	@Test
	public void deleteByIdTest() {
		repository.deleteById(1L);
		Optional<Sample> sample = repository.findById(1L);
		assertFalse(sample.isPresent());
	}
	
	@Test
	public void findByIdTest() {
		Iterable<Sample> samples = repository.findAll();
		samples.forEach(item -> {
			Optional<Sample> sample = repository.findById(item.getId());
			assertEquals(sample.get().getName(), "name1");
		});
	}
}
