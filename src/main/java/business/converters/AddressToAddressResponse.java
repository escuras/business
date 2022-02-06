package business.converters;

import business.domain.Address;
import business.dto.response.AddressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class AddressToAddressResponse implements Converter<Address, AddressResponse> {

    @Override
    public AddressResponse convert(final Address source) {
        if (null == source) {
            return null;
        }
        return AddressResponse.builder()
                .id(source.getId())
                .cep(source.getCep())
                .city(source.getCity())
                .complement(source.getComplement())
                .country(source.getCountry())
                .district(source.getDistrict())
                .inclusionDate(source.getInclusionDate())
                .local(source.getLocal())
                .uf(source.getUf())
                .userIds(this.userIds(source))
                .number(source.getNumber())
                .build();
    }

    private List<Long> userIds(final Address address) {
        if (CollectionUtils.isEmpty(address.getPersons())) {
            return Collections.emptyList();
        }
        return address.getPersons().stream().map(e -> e.getId()).collect(Collectors.toList());
    }
}
