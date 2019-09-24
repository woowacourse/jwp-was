package webserver.http.request;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.Cookies;
import webserver.http.HttpHeaders;
import webserver.http.utils.HttpUtils;
import webserver.http.utils.IOUtils;
import webserver.http.utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestFactory {
    private static final Logger log = LoggerFactory.getLogger(HttpRequestFactory.class);

    private HttpRequestFactory() {
    }

    public static HttpRequest generate(final InputStream in) {

        try {
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));

            final RequestLine requestLine = new RequestLine(br.readLine());
            final Parameters parameters = new Parameters(requestLine.getParameters());
            final RequestHeaders requestHeaders = readHeaders(br);
            final Cookies cookies = new Cookies(requestHeaders.get(HttpHeaders.COOKIE));

            readBody(br, parameters, requestHeaders);

            return HttpRequest.builder()
                    .requestLine(requestLine)
                    .headers(requestHeaders)
                    .parameters(parameters)
                    .cookies(cookies)
                    .build();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private static RequestHeaders readHeaders(final BufferedReader br) throws IOException {
        final Map<String, String> headers = new HashMap<>();

        String line;
        while (StringUtils.isNotEmpty(line = br.readLine())) {
            final Pair pair = HttpUtils.parseHeader(line);
            headers.put(pair.getKey(), pair.getValue());
        }
        return new RequestHeaders(headers);
    }

    private static void readBody(final BufferedReader br, final Parameters parameters, final RequestHeaders requestHeaders) throws IOException {
        if (requestHeaders.contains(HttpHeaders.CONTENT_LENGTH)) {
            final String contentLength = requestHeaders.get(HttpHeaders.CONTENT_LENGTH);
            final String params = IOUtils.readData(br, Integer.parseInt(contentLength));
            parameters.addAll(HttpUtils.parseQueryString(params));
        }
    }
}
