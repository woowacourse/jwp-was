package webserver.http.request;

import utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestHeadersFactory {
    private static final String DELIMITER = ": ";

    private RequestHeadersFactory() {
    }

    static RequestHeaders generate(final BufferedReader br) throws IOException {
        final Map<String, String> headers = new HashMap<>();

        String line;
        while (StringUtils.isNotEmpty(line = br.readLine())) {
            final String[] splitHeader = line.split(DELIMITER);
            final String name = splitHeader[0];
            final String value = splitHeader[1];
            headers.put(name, value);
        }
        return new RequestHeaders(headers);
    }
}
