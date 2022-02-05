package business.controller;

import business.util.Constants;
import org.springframework.http.HttpHeaders;

public class ApiTools {

    public static HttpHeaders createHeadersPaginacao(final long totalElementos, final Integer page, final Integer size,
													 final int tamanhoLista) {
        final int elementoInicial = (page * size) + 1;
        final int elementoFinal = (page * size) + tamanhoLista;

        final String contentRange = Integer.toString(elementoInicial).concat("-").concat(Integer.toString(elementoFinal))
                .concat("/").concat(Long.toString(totalElementos));

        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Range", contentRange);
        headers.add("Accept-Ranges", Constants.DEFAULT_PAGE_SIZE);

        return headers;
    }

}
