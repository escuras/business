package business.service;

import business.domain.Sample;
import business.dto.request.PaginationRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface SampleService {
    Optional<Sample> findById(Long id);

    Sample save(Sample sample);

    Page<Sample> findByPageable(PaginationRequest pagination);

    void delete(Long id);

    void update(Long id, Sample sample);
}
