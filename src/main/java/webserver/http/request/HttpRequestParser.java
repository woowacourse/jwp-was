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
            String httpRequestLine = br.readLine();
            String[] requestLines = httpRequestLine.split(" ");
            String pathWithQueryString = requestLines[1];
            String[] pathQueryString = pathWithQueryString.split("\\?");

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
        Map<String, String> queryParams = new HashMap<>();
        if (requestLine.isGetMethod()) {
            if (pathQueryString.length == 2) {
                String queryString = pathQueryString[1];
                String decode = URLDecoder.decode(queryString, "UTF-8");
                String[] queries = decode.split("&");
                for (String query : queries) {
                    String[] keyValue = query.split("=");
                    queryParams.put(keyValue[0], keyValue[1]);
                }
            }
            return new HttpRequestParameters(queryParams);
        }
        requestParameters = parseRequestParameters(br, requestHeader, requestLine);
        return requestParameters;
    }

    private static HttpRequestHeaders parseRequestHeader(BufferedReader br) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String oneLine = br.readLine();
        while (!"".equals(oneLine) && oneLine != null) {
            String[] header = oneLine.split(": ");
            headers.put(header[0], header[1]);
            oneLine = br.readLine();
        }
        return new HttpRequestHeaders(headers);
    }

    private static HttpRequestParameters parseRequestParameters(BufferedReader br,
            HttpRequestHeaders header, HttpRequestLine requestLine) throws IOException {
        Map<String, String> requestDatas = new HashMap<>();
        HttpMethod httpMethod = requestLine.getMethod();
        if (!httpMethod.hasRequestBody()) {
            return new HttpRequestParameters(requestDatas);
        }
        String data = IOUtils.readData(br, Integer.parseInt(header.get(HttpHeaders.CONTENT_LENGTH)));
        if (!"".equals(data)) {
            String decode = URLDecoder.decode(data, "UTF-8");
            String[] split = decode.split("&");
            for (String value : split) {
                String[] keyValue = value.split("=");
                requestDatas.put(keyValue[0], keyValue[1]);
            }
        }
        return new HttpRequestParameters(requestDatas);
    }
}
