package webserver.protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import utils.IOUtils;

public final class RequestBodyParser {
    private static final String PARAMS_REGEX = "&";
    private static final String PARAM_REGEX = "=";
    private static final int PARAM_KEY_INDEX = 0;
    private static final int PARAM_VALUE_INDEX = 1;

    public static RequestBody parse(final BufferedReader reader, final Map<String, String> headers) throws IOException {
        final Map<String, String> contents = new HashMap<>();

        final String contentLength = headers.get("Content-Length");
        if (Objects.isNull(contentLength)) {
            return new RequestBody(contents);
        }

        final String bodyData = IOUtils.readBodyData(reader, Integer.parseInt(contentLength));
        final String[] params = bodyData.split(PARAMS_REGEX, -1);
        for (final String param : params) {
            final String[] tokens = param.split(PARAM_REGEX, -1);
            contents.put(tokens[PARAM_KEY_INDEX], tokens[PARAM_VALUE_INDEX]);
        }

        return new RequestBody(contents);
    }
}
