package business.service.impl;

import business.domain.Sample;
import business.dto.request.PaginationRequest;
import business.exception.BusinessException;
import business.repository.SampleRepository;
import business.service.SampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SampleServiceImpl implements SampleService {

    private final SampleRepository repository;

    @Override
    public Optional<Sample> findById(final Long id) {
        return this.repository.findById(id);
    }

    @Override
    public Sample save(final Sample sample) {
        return this.repository.save(sample);
    }

    @Override
    public Page<Sample> findByPageable(final PaginationRequest pagination) {
        final Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getSize());
        return this.repository.findAll(pageable);
    }

    @Override
    public void delete(@NotNull(message = "Id can't be null") final Long id) {
        this.repository.deleteById(id);
    }

    @Override
    public void update(@NotNull(message = "Id can't be null") final Long id,
                       @NotNull(message = "Sample can't be null") final Sample sample) {
        if (!this.repository.existsById(id)) {
            throw new BusinessException("id not exists");
        }
        sample.setId(id);
        this.repository.save(sample);
    }
}
