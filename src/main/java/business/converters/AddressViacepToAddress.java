package business.converters;

import business.client.dto.AddressViacep;
import business.domain.Address;
import business.util.ConverterUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AddressViacepToAddress implements Converter<AddressViacep, Address> {

    private static final String BRASIL = "Brasil";

    @Override
    public Address convert(final AddressViacep source) {
        if (null == source) {
            return null;
        }

        return Address.builder()
                .cep(ConverterUtil.extractNumber(source.getCep()))
                .city(source.getLocalidade())
                .number(source.getSiafi())
                .complement(source.getComplemento())
                .district(source.getBairro())
                .local(source.getLogradouro())
                .country(BRASIL)
                .uf(source.getUf())
                .build();
    }
}
