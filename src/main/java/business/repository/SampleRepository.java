package business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import business.domain.Sample;

import java.util.Optional;

@Repository
public interface SampleRepository extends JpaRepository<Sample, Long> {
}
