package http.request.support;

import http.request.HttpRequest;
import http.request.HttpRequestBody;
import http.request.HttpStartLine;
import http.session.HttpSession;
import http.session.support.SessionManager;
import http.support.HttpCookie;
import http.support.HttpHeader;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static http.request.support.HttpMethod.POST;

public class HttpRequestFactory {
    public static HttpRequest create(BufferedReader bufferedReader, SessionManager sessionManager) throws IOException {
        List<String> lines = IOUtils.parseHeader(bufferedReader);

        HttpStartLine httpStartLine = new HttpStartLine(lines.get(0));
        Map<String, String> headers = getHeaders(lines.subList(1, lines.size()));
        HttpCookie cookie = getCookie(headers);
        HttpHeader header = new HttpHeader(headers);
        HttpSession httpSession = sessionManager.getSession(cookie);

        if (POST.equals(httpStartLine.getMethod())) {
            String body = IOUtils.readData(bufferedReader, Integer.parseInt(header.getHeader("Content-Length")));
            HttpRequestBody httpRequestBody = new HttpRequestBody(body);
            return new HttpRequest(httpStartLine, header, cookie, httpSession, httpRequestBody);
        }
        return new HttpRequest(httpStartLine, header, cookie, httpSession);
    }

    private static HttpCookie getCookie(final Map<String, String> headers) {
        if (headers.containsKey("Cookie")) {
            String cookie = headers.get("Cookie");
            headers.remove("Cookie");
            return new HttpCookie(cookie);
        }
        return HttpCookie.empty();
    }

    private static Map<String, String> getHeaders(final List<String> lines) {
        return lines.stream()
                .map(HttpRequestLine::new)
                .collect(Collectors.toMap(HttpRequestLine::getKey, HttpRequestLine::getValue));
    }
}
