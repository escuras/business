package business.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * Exception customizada para encapsular exceptions da camada de Persistencia
 */
public class PersistentException extends Exception {

	private static final long serialVersionUID = 1189505093175013258L;

	private final List<ErrorMessage> erros;

	public PersistentException(final ErrorMessage erro, final Throwable cause) {
		super(null, cause);
		if (erro == null) {
			throw new IllegalArgumentException("Mensagem de erro obrigatoria");
		}
		this.erros = new ArrayList<>();
		this.erros.add(erro);
	}

	public PersistentException(String erro) {
		super(erro);
		if (erro == null) {
			throw new IllegalArgumentException("Mensagem de erro obrigatoria");
		}
		this.erros = new ArrayList<>();
		this.erros.add(new ErrorMessage(erro));
	}
	
	public PersistentException(final Throwable cause) {
		super(cause);
		this.erros = new ArrayList<>();
	}

	public PersistentException() {
		this.erros = new ArrayList<>();
	}

	public List<ErrorMessage> getErros() {
		return this.erros;
	}
}
