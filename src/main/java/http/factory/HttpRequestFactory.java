package http.factory;

import http.request.HttpMethod;
import http.request.HttpRequest;
import http.request.RequestHeader;
import http.request.RequestLine;
import http.request.RequestParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import utils.Extractor;
import utils.IOUtils;
import utils.ParamUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestFactory {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestFactory.class);
    private static final String COLON_DELIMITER = ": ";
    public static final int BASE_CONTNET_LENGTH = 0;

    public static HttpRequest createRequest(BufferedReader br) throws IOException {
        String line = br.readLine();
        RequestLine requestLine = createRequestUri(line);
        String paramOfRequestLine = Extractor.paramFromRequestLine(line);

        RequestHeader requestHeader = createRequestHeader(br, line);
        
        String body = readBody(br, requestHeader);
        RequestParams requestParams = createRequestParams(paramOfRequestLine, body);
        return new HttpRequest(requestLine, requestHeader, requestParams);
    }

    public static RequestLine createRequestUri(String line) {
        HttpMethod method = HttpMethod.valueOf(Extractor.methodFromRequestLine(line));
        String path = Extractor.pathFromRequestLine(line);
        return new RequestLine(method, path);
    }

    private static RequestHeader createRequestHeader(BufferedReader br, String line) throws IOException {
        Map<String, String> headers = new HashMap<>();
        logger.debug("request header : {}", line);
        while (!StringUtils.isEmpty(line = br.readLine())) {
            logger.debug("request header : {}", line);
            headers.put(line.split(COLON_DELIMITER)[0], line.split(COLON_DELIMITER)[1]);
        }
        return new RequestHeader(headers);
    }

    private static String readBody(BufferedReader br, RequestHeader requestHeader) throws IOException {
        int contentLength = BASE_CONTNET_LENGTH;
        if (requestHeader.containsKey("Content-Length")) {
            contentLength = Integer.parseInt(requestHeader.get("Content-Length"));
        }
        return IOUtils.readData(br, contentLength);
    }

    private static RequestParams createRequestParams(String paramOfRequestLine, String body) throws IOException {
        Map<String, String> params = ParamUtils.createParams(paramOfRequestLine);
        params.putAll(ParamUtils.createParams(body));
        return new RequestParams(params);
    }
}
