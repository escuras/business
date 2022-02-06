package business.util;

public class ConverterUtil {
    public static final String FORMATO_DATA_ISO_8601 = "yyyy-MM-dd";


    public static Long extractNumber(final String cep) {
        if (cep == null) {
            return null;
        }
        final char[] numbers = cep.toCharArray();
        Long numberValue = 0L;
        int position = 0;
        for (int i = numbers.length - 1; i >= 0; i--) {
            if (Character.isDigit(numbers[i])) {
                numberValue += (long) (Character.getNumericValue(numbers[i]) * Math.pow(10, position));
                position++;
            }
        }
        return numberValue;
    }
}
