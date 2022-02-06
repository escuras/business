package business.client;

import business.client.dto.AddressViacep;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(url = "${domain.viacep.url}", name = "viacep")
public interface AddressViacepClient {

    @GetMapping("/{cep}/json")
    AddressViacep getAddress(@PathVariable String cep);

    @GetMapping("/{state}/{local}/{place}/json")
    List<AddressViacep> getAddress(@PathVariable String state, @PathVariable String local, @PathVariable String place);
}
