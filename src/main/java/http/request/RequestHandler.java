package http.request;

import http.common.Cookie;
import http.common.Parameters;
import http.utils.HttpUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RequestHandler {
    private static final String BLANK = " ";
    private static final String EQUAL = "=";
    private static final String SEMICOLON = ";";
    private static final String COOKIE = "Cookie: ";
    private static final String CONTENT_LENGTH_KEY = "Content-Length";
    private static final String UTF_8 = "UTF-8";

    private final BufferedReader br;

    public RequestHandler(BufferedReader br) {
        this.br = br;
    }

    public HttpRequest create() throws IOException {
        RequestLine requestLine = readRequestLine();
        List<Cookie> cookies = new ArrayList<>();
        RequestHeader requestHeader = readRequestHeader(cookies);
        Parameters parameters = readParameters(requestLine);
        readBody(parameters, requestHeader);

        return HttpRequest.of(requestLine, requestHeader, parameters, cookies);
    }

    private RequestLine readRequestLine() throws IOException {
        String startLine = br.readLine();
        return RequestLine.of(startLine);
    }

    private RequestHeader readRequestHeader(List<Cookie> cookies) throws IOException {
        List<String> header = new ArrayList<>();
        String line;
        while (!(line = br.readLine()).isEmpty()) {
            if (line.contains(COOKIE)) {
                readCookie(line, cookies);
                continue;
            }
            header.add(line);
        }
        return RequestHeader.of(header);
    }

    private void readCookie(String rawCookieString, List<Cookie> cookies) {
        String[] rawCookies = rawCookieString.split(BLANK);
        if (rawCookies.length == 2) {
            cookies.add(new Cookie(rawCookies[1].split(EQUAL)[0], rawCookies[1].split(EQUAL)[1]));
        }
        if (rawCookies.length > 2) {
            cookies.addAll(IntStream.range(1, rawCookies.length)
                    .mapToObj(i -> rawCookies[i])
                    .map(c -> c.split(EQUAL))
                    .map(c -> new Cookie(c[0], c[1].split(SEMICOLON)[0]))
                    .collect(Collectors.toList()));
        }
    }

    private Parameters readParameters(RequestLine requestLine) {
        Parameters parameters = new Parameters();
        if (!requestLine.getQueryString().isEmpty()) {
            parameters.addAll(HttpUtils.parseQuery(requestLine.getQueryString()));
        }
        return parameters;
    }

    private void readBody(Parameters parameters, RequestHeader requestHeader) throws IOException {
        String contentLength = requestHeader.getHeader(CONTENT_LENGTH_KEY);
        if (contentLength != null) {
            String params = IOUtils.readData(br, Integer.parseInt(contentLength));
            params = URLDecoder.decode(params, UTF_8);
            parameters.addAll(HttpUtils.parseQuery(params));
        }
    }
}
