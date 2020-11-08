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
            HttpRequestLine requestLine = parseRequestLine(br);
            HttpRequestHeader requestHeader = parseRequestHeader(br);
            HttpRequestBody requestBody = parseRequestBody(br, requestHeader, requestLine);
            return new HttpRequest(requestLine, requestHeader, requestBody);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    private static HttpRequestLine parseRequestLine(BufferedReader br) throws IOException {
        final String s = br.readLine();
        final String[] s1 = s.split(" ");
        return new HttpRequestLine(HttpMethod.GET, s1[1], s1[2]);
    }

    private static HttpRequestHeader parseRequestHeader(BufferedReader br) throws IOException {
        final Map<String, String> headers = new HashMap<>();
        String oneLine = br.readLine();
        while (!"".equals(oneLine) && oneLine != null) {
            final String[] header = oneLine.split(": ");
            headers.put(header[0], header[1]);
            oneLine = br.readLine();
        }
        return new HttpRequestHeader(headers);
    }

    private static HttpRequestBody parseRequestBody(BufferedReader br,
            HttpRequestHeader header, HttpRequestLine requestLine) throws IOException {
        final HashMap<String, String> requestDatas = new HashMap<>();
        final HttpMethod httpMethod = requestLine.getMethod();
        if (!httpMethod.hasRequestBody()) {
            return new HttpRequestBody(requestDatas);
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
        return new HttpRequestBody(requestDatas);
    }
}
