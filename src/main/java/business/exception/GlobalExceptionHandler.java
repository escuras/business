package business.exception;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import business.util.OpenApi3Constants;

import javax.naming.ServiceUnavailableException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handler Global de erros da aplicacao gerenciados pelo spring.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	// ERROS 400
	
	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ApiResponse(responseCode = "400", description = OpenApi3Constants.MSG_STATUS_400)
	protected ResponseEntity<List<ErrorMessage>> processBusinessException (final BusinessException ex) {
		logger(ex);
		return this.createBodyResponseListError(ex.getErros(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ApiResponse(responseCode = "400", description = OpenApi3Constants.MSG_STATUS_400)
	public ResponseEntity<List<ErrorMessage>> processArgumentNotValidException (final MethodArgumentNotValidException ex) {
		logger(ex);
		return this.createBodyResponseValidationFieldErrors(ex.getBindingResult().getFieldErrors());
	}

	@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
	@ApiResponse(responseCode = "406", description = OpenApi3Constants.MSG_STATUS_406)
	protected void processHttpMediaTypeNotAcceptableException (final HttpMediaTypeNotAcceptableException ex) {
		logger(ex);
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	@ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ApiResponse(responseCode = "415", description = OpenApi3Constants.MSG_STATUS_415)
	protected void processHttpMediaTypeNotSupportedException (final HttpMediaTypeNotSupportedException ex) {
		logger(ex);
	}

	// ERROS 500

	@ExceptionHandler(IntegrationException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ApiResponse(responseCode = "500", description = OpenApi3Constants.MSG_STATUS_500)
	protected void processInvocationTargetException (final IntegrationException ex) {
		logger(ex);
	}

	@ExceptionHandler(ServiceUnavailableException.class)
	@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
	@ApiResponse(responseCode = "503", description = OpenApi3Constants.MSG_STATUS_503)
	protected void processServiceUnavailableException (final ServiceUnavailableException ex) {
		logger(ex);
	}
	
	@ExceptionHandler(PersistentException.class)
	@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
	@ApiResponse(responseCode = "503", description = OpenApi3Constants.MSG_STATUS_503)
	protected void processDataBaseException (final PersistentException ex) {
		logger(ex);
	}

	@ExceptionHandler(SocketTimeoutException.class)
	@ResponseStatus(value = HttpStatus.GATEWAY_TIMEOUT)
	@ApiResponse(responseCode = "512", description = OpenApi3Constants.MSG_STATUS_512)
	protected void processSocketTimeoutException (final SocketTimeoutException ex) {
		logger(ex);
	}

	// ERROS GENERICOS

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ApiResponse(responseCode = "500", description = OpenApi3Constants.MSG_STATUS_500)
	protected void processException (final Exception ex) {
		logger(ex);
	}


	private ResponseEntity<List<ErrorMessage>> createBodyResponseListError (final List<ErrorMessage> errors,
			final HttpStatus httpStatus) {
		if (errors == null || httpStatus == null) {
			throw new IllegalArgumentException();
		}
		return new ResponseEntity<>(errors, httpStatus);
	}

	private ResponseEntity<List<ErrorMessage>> createBodyResponseValidationFieldErrors (List<FieldError> fieldErrors) {
		List<ErrorMessage> errors = new ArrayList<>();
		fieldErrors.stream().forEach(item -> {
			errors.add(new ErrorMessage(item.getDefaultMessage()));
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	private static void logger(final Exception e) {
		log.error("Class Name = {}, Message = {}", e.getClass().getSimpleName(), e.getMessage(), e);
	}
}