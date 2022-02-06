package business.service.impl;

import business.client.AddressViacepClient;
import business.client.dto.AddressViacep;
import business.domain.Address;
import business.domain.Person;
import business.dto.request.PaginationRequest;
import business.exception.BusinessException;
import business.repository.AddressRepository;
import business.service.AddressService;
import business.service.PersonService;
import business.util.ConverterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressViacepClient addressViacepClient;
    private final PersonService personService;
    private final ConversionService conversionService;

    @Override
    public Optional<Address> findById(@NotNull(message = "Id can't be null.") final Long id) {
        return this.addressRepository.findById(id);
    }

    @Override
    public Address create(@NotNull(message = "Address can't be null.") final Address address) {
        address.setId(null);
        return this.addressRepository.save(address);
    }

    @Override
    public Optional<Address> getByCep(@NotNull final String cep) {
        return Optional.ofNullable(this.addressRepository.findByCep(ConverterUtil.extractNumber(cep))
                .orElseGet(() -> {
                    final AddressViacep addressViacep = this.addressViacepClient.getAddress(cep);
                    if (addressViacep.getCep() != null) {
                        final Address address = this.conversionService.convert(addressViacep, Address.class);
                        return this.create(address);
                    }
                    return null;
                }));
    }

    @Override
    public Page<Address> findByPageable(@NotNull(message = "Pagination can't be null") final PaginationRequest pagination) {
        if (pagination.getSize() < 1) {
            pagination.setSize(1);
        }
        return this.addressRepository.findAll(PageRequest.of(pagination.getPage(), pagination.getSize()));
    }

    @Override
    public void delete(@NotNull(message = "Id can't be null.") final Long id) {
        this.addressRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(@NotNull(message = "Id can't be null.") final Long id,
                       @NotNull(message = "Address can't be null.") final Address address) {
        final Address dbAddress = this.addressRepository.findById(id).orElseGet(() -> {
            throw new BusinessException("Address was not found with id: " + id + ".");
        });
        dbAddress.setCep(address.getCep());
        dbAddress.setCity(address.getCity());
        dbAddress.setComplement(address.getComplement());
        dbAddress.setCountry(address.getCountry());
        dbAddress.setDistrict(address.getDistrict());
        dbAddress.setNumber(address.getNumber());
        dbAddress.setUf(address.getUf());
        dbAddress.setLocal(address.getLocal());
    }

    @Override
    public void setPersonAddress(@NotNull(message = "Address id can't be null.") final Long addressId,
                                 @NotNull(message = "User id can't be null.") final Long personId) {
        final Optional<Address> optionalAddress = this.findById(addressId);
        final Optional<Person> optionalPerson = this.personService.findById(personId);
        if (optionalAddress.isPresent() && optionalPerson.isPresent()) {
            final Person person = optionalPerson.get();
            person.setAddress(optionalAddress.get());
            this.personService.update(person.getId(), person);
        }

    }
}
