package webserver.http.request;

import utils.IOUtils;
import webserver.http.common.HttpHeader;

import java.io.BufferedReader;
import java.io.IOException;

public class QueryStringParamsParser {
    private static final String CONTENT_LENGTH = "Content-Length";

    public static QueryStringParams parse(final BufferedReader bufferedReader,
                                          final RequestLine requestLine,
                                          final HttpHeader httpHeader) throws IOException {
        if (httpHeader.get(CONTENT_LENGTH) == null) {
            return QueryStringParams.of(requestLine.splitQueryString());
        }

        return QueryStringParams.of(
                IOUtils.readData(bufferedReader, Integer.parseInt(httpHeader.get(CONTENT_LENGTH)))
        );
    }
}
