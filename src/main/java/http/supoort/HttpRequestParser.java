package http.supoort;

import http.model.*;
import utils.IOUtils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestParser {
    private static final String SEPARATOR = " ";
    private static final String QUERY_STRING_INDICATOR = "?";
    private static final String QUERY_STRING_SEPARATOR = "&";
    private static final String QUERY_STRING_DELIMITER = "=";

    public static HttpRequest parse(InputStream inputStream) {
        List<String> requestLines = IOUtils.readData(inputStream);
        validate(requestLines);
        String requestHeadline = requestLines.get(0);

        String[] tmp = requestHeadline.split(SEPARATOR);

        HttpMethod method = HttpMethod.valueOf(tmp[0]);

        HttpUri uri = new HttpUri(tmp[1]);
        HttpParameters httpParameters = null;
        HttpProtocol protocol = new HttpProtocol(tmp[2]);

        if (tmp[1].contains(QUERY_STRING_INDICATOR)) {
            Map<String, String> parameters = new HashMap<>();
            String params = tmp[1].substring(tmp[1].indexOf(QUERY_STRING_INDICATOR) + 1);
            String[] details = params.split(QUERY_STRING_SEPARATOR);
            for (String detail : details) {
                String[] entry = detail.split(QUERY_STRING_DELIMITER);
                parameters.put(entry[0], entry[1]);
            }
            httpParameters = new HttpParameters(parameters);
            uri = new HttpUri(tmp[1].substring(0, tmp[1].indexOf(QUERY_STRING_INDICATOR)));
        }

        return new HttpRequest(method, uri, httpParameters, protocol);
    }

    private static void validate(List<String> requestLines) {
        if (requestLines.size() == 0) {
            throw new IllegalHttpRequest();
        }
    }
}
