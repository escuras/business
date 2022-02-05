package business.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * Objeto Padrao de erros do servico
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ErrorMessage implements Serializable {

	private static final long serialVersionUID = -40089374255050241L;

	private String code;

	private String message;

	private String link;

	public ErrorMessage(final String code, final String msg, final String identification) {
		this.code = code;
		message = msg;
		link = identification;
	}

	public ErrorMessage(final String code, final String msg) {
		this.code = code;
		message = msg;
	}
	
	public ErrorMessage(final String msg) {
		message = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(final String code) {
		this.code = StringUtils.trimAllWhitespace(code);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = StringUtils.trimAllWhitespace(message);
	}

	public String getLink() {
		return link;
	}

	public void setLink(final String link) {
		this.link = StringUtils.trimAllWhitespace(link);
	}
}
