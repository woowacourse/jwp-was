package webserver.http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class ParameterParser {
    private static final Logger log = LoggerFactory.getLogger(ParameterParser.class);

    private static final String DELIMITER_QUERY = "&";
    private static final String DELIMITER_PAIR = "=";

    private ParameterParser() {
    }

    static Map<String, String> parse(final String queryParamsText) {
        if (StringUtils.isEmpty(queryParamsText)) {
            return Collections.EMPTY_MAP;
        }

        try {
            final String queryParams = URLDecoder.decode(queryParamsText, StandardCharsets.UTF_8.toString());

            return Arrays.stream(queryParams.split(DELIMITER_QUERY))
                    .map(queryParam -> queryParam.split(DELIMITER_PAIR))
                    .collect(Collectors.toMap(pair -> pair[0], pair -> pair[1]));
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
