package http.request;

import http.common.HttpHeader;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequestParamsParser {
    private static final String CONTENT_LENGTH = "Content-Length";

    public static HttpRequestParams parse(final BufferedReader bufferedReader,
                                          final RequestLine requestLine,
                                          final HttpHeader httpHeader) throws IOException {
        if (httpHeader.get(CONTENT_LENGTH) == null) {
            return HttpRequestParams.of(requestLine.splitQueryString());
        }

        return HttpRequestParams.of(
                IOUtils.readData(bufferedReader, Integer.parseInt(httpHeader.get(CONTENT_LENGTH)))
        );
    }
}
