package business.enums;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public enum SampleEnum {

	BLACK("Black", "white"),
	WHITE("White", "white");

	@Getter
	private String firstUpperCase;

	@Getter
	private String lowerCase;

	public static SampleEnum getSampleEnum(String firstUpperCase) {
		return Arrays.stream(SampleEnum.values())
				.filter(s -> s.firstUpperCase.equals(firstUpperCase))
				.findFirst()
				.orElse(null);
	}
}
