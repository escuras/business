package business.converters;

import business.domain.Company;
import business.domain.Person;
import business.dto.request.CompanyRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CompanyRequestToCompany implements Converter<CompanyRequest, Company> {

    @Override
    public Company convert(final CompanyRequest source) {
        return Company.builder()
                .financesEmail(source.getFinancesEmail())
                .financesResponsible(source.getFinancesResponsible())
                .hrResponsible(source.getHrResponsible())
                .sector(source.getSector())
                .person(this.buildPerson(source.getPersonId()))
                .build();
    }

    private Person buildPerson(final Long id) {
        final Person person = new Person();
        person.setId(id);
        return person;
    }
}
