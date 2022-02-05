package business.converters;

import business.domain.Company;
import business.domain.Person;
import business.dto.response.CompanyResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CompanyToCompanyResponse implements Converter<Company, CompanyResponse> {

    @Override
    public CompanyResponse convert(final Company source) {
        return CompanyResponse.builder()
                .financesEmail(source.getFinancesEmail())
                .hrResponsible(source.getHrResponsible())
                .financesResponsible(source.getFinancesResponsible())
                .inclusionDate(source.getInclusionDate())
                .workersNumber(source.getWorkersNumber())
                .sector(source.getSector())
                .id(source.getId())
                .personId(Optional.ofNullable(source.getPerson()).orElse(new Person()).getId())
                .build();
    }


}
