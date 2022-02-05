package business.controller;

import business.dto.response.SampleResponse;
import business.util.Constants;
import business.util.OpenApi3Constants;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(Constants.API_ROOT_RESOURCE_PATH)
public class ApiController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RepresentationModel<SampleResponse>> apiRoot() throws Exception {
        final RepresentationModel<SampleResponse> model = new RepresentationModel<>();

        model.add(linkTo(methodOn(ApiController.class).apiRoot()).withSelfRel());
        model.add(linkTo(methodOn(SampleController.class).getAll(null)).withRel(OpenApi3Constants.GET));
        model.add(linkTo(methodOn(SampleController.class).getById(null)).withRel(OpenApi3Constants.GET));
        model.add(linkTo(methodOn(SampleController.class).save(null)).withRel(OpenApi3Constants.POST));
        model.add(linkTo(methodOn(SampleController.class).deleteById(null)).withRel(OpenApi3Constants.DELETE));

        return ResponseEntity.ok(model);
    }
}
