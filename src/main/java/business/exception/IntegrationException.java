package business.exception;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Exception customizada para encapsular exceptions da camada de Integracao com sistemas externos
 */
public class IntegrationException extends Exception {

	private static final long serialVersionUID = 4549527453862274630L;

	private final HttpStatus status;
	private final List<ErrorMessage> erros;

	public IntegrationException(final HttpStatus status, final ErrorMessage erro, final Throwable cause) {
		super(cause);
		if (erro == null) {
			throw new IllegalArgumentException("Mensagem de erro obrigatoria");
		}
		this.status = status;
		this.erros = new ArrayList<>();
		this.erros.add(erro);
	}

	public IntegrationException(final HttpStatus status, final String erro) {
		super(erro);
		if (erro == null) {
			throw new IllegalArgumentException("Mensagem de erro obrigatoria");
		}
		this.status = status;
		this.erros = new ArrayList<>();
		this.erros.add(new ErrorMessage(erro, "", ""));
	}
	
	public IntegrationException(final HttpStatus status) {
		this.status = status;
		this.erros = new ArrayList<>();
	}
	
	public IntegrationException(String erro) {
		super(erro);
		this.status = HttpStatus.INTERNAL_SERVER_ERROR;
		this.erros = new ArrayList<>();
		this.erros.add(new ErrorMessage(erro, "", ""));
	}
	
	public IntegrationException() {
		this.status = HttpStatus.BAD_REQUEST;
		this.erros = new ArrayList<>();
	}

	public IntegrationException(final HttpStatus status, final List<ErrorMessage> erros) {
		if (erros == null || erros.size() == 0) {
			throw new IllegalArgumentException("Mensagem de erro obrigatória");
		}
		this.status = status;
		this.erros = erros;
	}

	public List<ErrorMessage> getErros() {
		return this.erros;
	}
	
	public HttpStatus getHttpStatus() {
		return this.status;
	}
}
