package http.factory;

import http.request.Cookie;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.request.RequestHeader;
import http.request.RequestLine;
import http.request.RequestParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.BufferedReaderUtils;
import utils.Extractor;
import utils.IOUtils;
import utils.ParamUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class HttpRequestFactory {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestFactory.class);
    public static final int BASE_CONTNET_LENGTH = 0;

    public static HttpRequest createRequest(BufferedReader br) throws IOException {
        String line = br.readLine();
        logger.debug("request: {}", line);
        RequestLine requestLine = createRequestUri(line);
        String paramOfRequestLine = Extractor.paramFromRequestLine(line);

        Map<String, String> headers = BufferedReaderUtils.readHeader(br);
        Cookie cookie = new Cookie(headers.get(Cookie.NAME));
        RequestHeader requestHeader = createRequestHeader(headers);

        String body = readBody(br, requestHeader);
        RequestParams requestParams = createRequestParams(paramOfRequestLine, body);
        return new HttpRequest(requestLine, requestHeader, requestParams, cookie);
    }

    public static RequestLine createRequestUri(String line) {
        HttpMethod method = HttpMethod.valueOf(Extractor.methodFromRequestLine(line));
        String path = Extractor.pathFromRequestLine(line);
        return new RequestLine(method, path);
    }

    private static RequestHeader createRequestHeader(Map<String, String> headers) {
        headers.remove(Cookie.NAME);
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
