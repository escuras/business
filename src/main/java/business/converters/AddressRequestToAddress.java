package business.converters;

import business.domain.Address;
import business.dto.request.AddressRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class AddressRequestToAddress implements Converter<AddressRequest, Address> {

    @Override
    public Address convert(final AddressRequest source) {
        if (null == source) {
            return null;
        }

        return Address.builder()
                .country(source.getCountry())
                .local(source.getLocal())
                .uf(source.getUf())
                .district(source.getDistrict())
                .complement(source.getComplement())
                .number(source.getNumber())
                .cep(source.getCep())
                .city(source.getCity())
                .build();
    }
}
