package webserver.protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.IOUtils;

public class RequestHeadersParser {
    private static final int HEADER_KEY_INDEX = 0;
    private static final int HEADER_VALUE_INDEX = 1;
    private static final String HEADER_REGEX = ": ";

    public static RequestHeaders parse(final BufferedReader reader) throws IOException {
        final List<String> headerData = IOUtils.readHeaderData(reader);

        final Map<String, String> headers = new HashMap<>();
        for (final String header : headerData) {
            final String[] tokens = header.split(HEADER_REGEX, -1);
            headers.put(tokens[HEADER_KEY_INDEX], tokens[HEADER_VALUE_INDEX]);
        }
        return new RequestHeaders(headers);
    }
}
