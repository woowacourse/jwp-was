package http.factory;

import http.HttpRequest;
import http.RequestHeader;
import http.RequestLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import utils.IOUtils;
import utils.ParamUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestFactory {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestFactory.class);
    private static final String COLON_DELIMITER = ": ";

    public static HttpRequest createRequest(BufferedReader br) throws IOException {
        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();

        String line = br.readLine();
        logger.debug("request header : {}", line);
        RequestLine requestLine = RequestUriFactory.createRequestUri(line, params);
        while (!StringUtils.isEmpty(line = br.readLine())) {
            logger.debug("request header : {}", line);
            headers.put(line.split(COLON_DELIMITER)[0], line.split(COLON_DELIMITER)[1]);
        }
        RequestHeader requestHeader = new RequestHeader(headers);
        putParameterOfBody(br, headers, params);
        return new HttpRequest(requestLine, requestHeader, params);
    }

    private static void putParameterOfBody(BufferedReader br, Map<String, String> headers, Map<String, String> params) throws IOException {
        if (headers.containsKey("Content-Length")) {
            String body = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
            ParamUtils.putParameter(params, body);
        }
    }
}
