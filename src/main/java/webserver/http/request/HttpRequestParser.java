package webserver.http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.net.HttpHeaders;
import utils.IOUtils;
import webserver.http.HttpMethod;

public class HttpRequestParser {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestParser.class);

    private HttpRequestParser() {
    }

    public static HttpRequest parse(BufferedReader br) {
        try {
            final String httpRequestLine = br.readLine();
            final String[] requestLines = httpRequestLine.split(" ");
            final String pathWithQueryString = requestLines[1];
            final String[] pathQueryString = pathWithQueryString.split("\\?");

            HttpRequestLine requestLine = new HttpRequestLine(HttpMethod.valueOf(requestLines[0]), pathQueryString[0],
                    requestLines[2]);
            HttpRequestHeaders requestHeader = parseRequestHeader(br);

            HttpRequestParameters requestParameters = getHttpRequestParameters(br, pathQueryString, requestLine,
                    requestHeader);
            return new HttpRequest(requestLine, requestHeader, requestParameters);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    private static HttpRequestParameters getHttpRequestParameters(BufferedReader br, String[] pathQueryString,
            HttpRequestLine requestLine, HttpRequestHeaders requestHeader) throws IOException {
        HttpRequestParameters requestParameters;
        final HashMap<String, String> queryParams = new HashMap<>();
        if (requestLine.isGetMethod()) {
            if (pathQueryString.length == 2) {
                String queryString = pathQueryString[1];
                final String decode = URLDecoder.decode(queryString, "UTF-8");
                final String[] queries = decode.split("&");
                for (String query : queries) {
                    final String[] keyValue = query.split("=");
                    queryParams.put(keyValue[0], keyValue[1]);
                }
            }
            return new HttpRequestParameters(queryParams);
        }
        requestParameters = parseRequestParameters(br, requestHeader, requestLine);
        return requestParameters;
    }

    private static HttpRequestHeaders parseRequestHeader(BufferedReader br) throws IOException {
        final Map<String, String> headers = new HashMap<>();
        String oneLine = br.readLine();
        while (!"".equals(oneLine) && oneLine != null) {
            final String[] header = oneLine.split(": ");
            headers.put(header[0], header[1]);
            oneLine = br.readLine();
        }
        return new HttpRequestHeaders(headers);
    }

    private static HttpRequestParameters parseRequestParameters(BufferedReader br,
            HttpRequestHeaders header, HttpRequestLine requestLine) throws IOException {
        final HashMap<String, String> requestDatas = new HashMap<>();
        final HttpMethod httpMethod = requestLine.getMethod();
        if (!httpMethod.hasRequestBody()) {
            return new HttpRequestParameters(requestDatas);
        }
        final String data = IOUtils.readData(br, Integer.parseInt(header.get(HttpHeaders.CONTENT_LENGTH)));
        if (!"".equals(data)) {
            final String decode = URLDecoder.decode(data, "UTF-8");
            final String[] split = decode.split("&");
            for (String value : split) {
                final String[] keyValue = value.split("=");
                requestDatas.put(keyValue[0], keyValue[1]);
            }
        }
        return new HttpRequestParameters(requestDatas);
    }
}
