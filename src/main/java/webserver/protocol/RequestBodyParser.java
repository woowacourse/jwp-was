package webserver.protocol;

import java.util.HashMap;
import java.util.Map;

public final class RequestBodyParser {

    private static final String PARAMS_REGEX = "&";
    private static final String PARAM_REGEX = "=";
    private static final int PARAM_KEY_INDEX = 0;
    private static final int PARAM_VALUE_INDEX = 1;

    public static RequestBody parse(final String bodyData) {
        final Map<String, String> contents = new HashMap<>();

        final String[] params = bodyData.split(PARAMS_REGEX, -1);
        for (final String param : params) {
            final String[] tokens = param.split(PARAM_REGEX, -1);
            contents.put(tokens[PARAM_KEY_INDEX], tokens[PARAM_VALUE_INDEX]);
        }

        return new RequestBody(contents);
    }
}
