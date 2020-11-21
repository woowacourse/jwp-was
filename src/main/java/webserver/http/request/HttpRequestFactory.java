package webserver.http.request;

import static java.lang.Integer.*;
import static java.util.Objects.*;
import static utils.IOUtils.*;
import static utils.Strings.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import webserver.http.HttpHeaderFields;
import webserver.http.HttpVersion;

public class HttpRequestFactory {
    private static final int HTTP_METHOD = 0;
    private static final int REQUEST_URI = 1;
    private static final int HTTP_VERSION = 2;
    private static final String HEADER_DELIMITER = ":";

    public HttpRequest create(BufferedReader br) throws IOException {
        HttpRequestLine requestLine = extractRequestLine(br);
        HttpHeader header = extractHeader(br);
        String body = extractBody(br, header.get(HttpHeaderFields.CONTENT_LENGTH));

        return new HttpRequest(requestLine, header, body);
    }

    private HttpRequestLine extractRequestLine(BufferedReader br) throws IOException {
        String requestLine = br.readLine();
        String[] tokens = requestLine.split(SP);
        String uri = tokens[REQUEST_URI];
        RequestURIType URIType = RequestURIType.of(uri);

        HttpMethod method = HttpMethod.valueOf(tokens[HTTP_METHOD]);
        RequestURI requestURI = createRequestURI(URIType, uri);
        HttpVersion version = HttpVersion.of(tokens[HTTP_VERSION]);
        return new HttpRequestLine(method, requestURI, version);
    }

    private RequestURI createRequestURI(RequestURIType uriType, String uri) {
        if (uriType.hasParams()) {
            return new RequestURIFactory().create(uri);
        }
        return new RequestURI(uri, HttpParams.of(EMPTY));
    }

    private HttpHeader extractHeader(BufferedReader br) throws IOException {
        Map<String, String> fields = new HashMap<>();
        String line = br.readLine();

        while (nonNull(line) && !EMPTY.equals(line)) {
            int indexOfDelimiter = line.indexOf(HEADER_DELIMITER);
            String key = line.substring(0, indexOfDelimiter);
            String value = line.substring(indexOfDelimiter + 2);
            fields.put(key, value);
            line = br.readLine();
        }

        return new HttpHeader(fields);
    }

    private String extractBody(BufferedReader br, String contentLength) throws IOException {
        if (br.ready() && nonNull(contentLength)) {
            return readData(br, parseInt(contentLength));
        }
        return EMPTY;
    }
}
