package http.supoort;

import http.controller.NotFoundException;
import http.model.*;
import utils.IOUtils;

import java.io.InputStream;
import java.util.List;

public class HttpRequestParser {
    private static final String SEPARATOR = " ";

    public static HttpRequest parse(InputStream inputStream) {
        try {
            List<String> requestMessages = IOUtils.readData(inputStream);

            RequestLine requestLine = new RequestLine(requestMessages.get(0));

            List<String> headerLines = requestMessages.subList(1, requestMessages.size());

            String[] requestLineTokens = requestMessages.get(0).split(SEPARATOR);
            String uri = requestLineTokens[1];
            HttpMethod httpMethod = requestLine.getHttpMethod();

            HttpParameters httpParameters = new HttpParameters(uri, httpMethod, headerLines);
            HttpHeaders headers = new HttpHeaders(httpMethod, headerLines);

            return new HttpRequest(requestLine, httpParameters, headers);
        } catch (IndexOutOfBoundsException e) {
            throw new NotFoundException();
        }
    }
}
