package business.service;

import business.domain.Address;
import business.dto.request.PaginationRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface AddressService {

    Optional<Address> findById(Long id);

    Address create(Address address);

    Optional<Address> getByCep(String cep);

    Page<Address> findByPageable(PaginationRequest pagination);

    void delete(Long id);

    void update(Long id, Address address);

    void setPersonAddress(Long companyId, Long userId);
}
