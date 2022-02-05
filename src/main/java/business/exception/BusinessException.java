package business.exception;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Exception customizada para encapsular exceptions da camada de Servico para erros de negocio
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 263477843027212621L;
	
	private final HttpStatus status;
	private final List<ErrorMessage> erros;

	public BusinessException(final HttpStatus status, final ErrorMessage erro, final Throwable cause) {
		super(cause);
		if (erro == null) {
			throw new IllegalArgumentException("Mensagem de erro obrigatoria");
		}
		this.status = status;
		this.erros = new ArrayList<>();
		this.erros.add(erro);
	}

	public BusinessException(final HttpStatus status, String erro) {
		super(erro);
		if (erro == null) {
			throw new IllegalArgumentException("Mensagem de erro obrigatoria");
		}
		this.status = status;
		this.erros = new ArrayList<>();
		this.erros.add(new ErrorMessage(erro));
	}

	public BusinessException(final HttpStatus status) {
		this.status = status;
		this.erros = new ArrayList<>();
	}

	public BusinessException(String erro) {
		super(erro);
		this.status = HttpStatus.BAD_REQUEST;
		this.erros = new ArrayList<>();
		this.erros.add(new ErrorMessage(erro));
	}
	
	public BusinessException(String erro, final Throwable cause) {
		super(erro, cause);
		this.status = HttpStatus.BAD_REQUEST;
		this.erros = new ArrayList<>();
		this.erros.add(new ErrorMessage(erro));
	}

	public BusinessException() {
		this.status = HttpStatus.BAD_REQUEST;
		this.erros = new ArrayList<>();
	}

	public BusinessException(final HttpStatus status, final List<ErrorMessage> erros) {
		if (erros == null || erros.size() == 0) {
			throw new IllegalArgumentException();
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
