package business.api;

import business.dto.request.PaginationRequest;
import business.util.Constants;
import business.util.OpenApi3Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Tag(name = Constants.DOC_API, description = "Api Sample")
public interface Api<T, S> {

    @Operation(description = "Get all",
            tags = Constants.DOC_API,
            method = OpenApi3Constants.GET)
    @ApiResponse(responseCode = "200", description = OpenApi3Constants.MSG_STATUS_200)
    default ResponseEntity<CollectionModel<T>> getAll(final PaginationRequest pagination) {
        return new ResponseEntity<>(CollectionModel.empty(), HttpStatus.OK);
    }

    @Operation(description = "Get by Id",
            tags = Constants.DOC_API,
            method = OpenApi3Constants.GET)
    @ApiResponse(responseCode = "200", description = OpenApi3Constants.MSG_STATUS_200)
    default ResponseEntity<T> getById(@Parameter(required = true, description = OpenApi3Constants.ID) final Long id) {
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Save new Sample",
            tags = Constants.DOC_API,
            method = OpenApi3Constants.GET)
    @ApiResponse(responseCode = "201", description = OpenApi3Constants.MSG_STATUS_201)
    default ResponseEntity<T> save(@RequestBody(required = true,
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)) final S request) {
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Delete by Id",
            tags = Constants.DOC_API,
            method = OpenApi3Constants.GET)
    @ApiResponse(responseCode = "204", description = OpenApi3Constants.MSG_STATUS_204)
    default ResponseEntity<?> deleteById(@Parameter(required = true, description = OpenApi3Constants.ID) final Long id) {
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Update sample",
            tags = Constants.DOC_API,
            method = OpenApi3Constants.PUT)
    @ApiResponse(responseCode = "204", description = OpenApi3Constants.MSG_STATUS_204)
    default ResponseEntity<?> update(final Long id,
                                     @RequestBody(required = true,
                                             content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)) final S request) {
        return ResponseEntity.noContent().build();
    }
}
