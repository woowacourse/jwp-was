package http.supoort;

import http.exceptions.IllegalHttpRequestException;
import http.model.HttpRequest;
import http.supoort.converter.HttpRequestConverter;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequestParser {
    private static final String SEPARATOR = " ";

    public static HttpRequest parse(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String requestLine = IOUtils.readFirstLine(bufferedReader);
        try {
            validate(requestLine);
            String[] requestLineTokens = requestLine.split(SEPARATOR);
            String method = requestLineTokens[0];
            return HttpRequestConverter.of(method).convert(requestLineTokens[1], requestLineTokens[2], bufferedReader);

        } catch (IndexOutOfBoundsException e) {
            throw new IllegalHttpRequestException(e.getMessage());
        }
    }

    private static void validate(String requestLine) {
        if (requestLine == null || requestLine.isEmpty()) {
            throw new IllegalHttpRequestException();
        }
    }
}
