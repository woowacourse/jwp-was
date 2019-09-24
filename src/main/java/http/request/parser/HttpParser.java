package http.request.parser;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import http.request.RequestInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpParser {

    private static final Logger log = LoggerFactory.getLogger(HttpParser.class);

    private static List<ParameterParser> parsers = Arrays.asList(new UrlParameterParser(), new UrlAndBodyParameterParser(), new BodyParameterParser());

    public static RequestInformation parse(BufferedReader br) throws IOException {
        Map<String, String> requestInformation = new HashMap<>();

        String line = br.readLine();
        requestInformation.put("Request-Line:", line);

        putKeyValues(br, requestInformation, line);
        putParametersIfPresent(br, requestInformation);

        return new RequestInformation(requestInformation);
    }

    private static void putKeyValues(BufferedReader br, Map<String, String> requestInformation, String line) throws IOException {
        while (!StringUtils.isBlank(line)) {
            line = br.readLine();
            put(requestInformation, line);
        }
    }

    private static void put(Map<String, String> requestInformation, String line) {
        if (!StringUtils.isBlank(line)) {
            String[] tokens = line.split(" ");
            requestInformation.put(tokens[0], tokens[1]);
        }
    }

    private static void putParametersIfPresent(BufferedReader br, Map<String, String> requestInformation) throws IOException {

        parsers.stream()
                .filter(parser -> parser.isParseable(requestInformation))
                .forEach(parser -> {
                    try {
                        parser.parse(br, requestInformation);
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
                });
    }
}
