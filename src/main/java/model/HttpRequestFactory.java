package model;

import static java.lang.Integer.*;
import static utils.IOUtils.*;
import static utils.Strings.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpRequestFactory {
    private static final int HTTP_METHOD = 0;
    private static final int REQUEST_URI = 1;
    private static final int HTTP_VERSION = 2;
    private static final String HEADER_DELIMITER = ":";

    public HttpRequest create(BufferedReader br) throws IOException {
        HttpHeader header = extractHeader(br);
        HttpBody body = extractBody(br, header);
        return new HttpRequest(header, body);
    }

    private HttpHeader extractHeader(BufferedReader br) throws IOException {
        String requestLine = br.readLine();
        String[] tokens = requestLine.split(SP);

        HttpMethod method = HttpMethod.valueOf(tokens[HTTP_METHOD]);
        RequestURI uri = RequestURIType.of(tokens[REQUEST_URI])
                .getFactory()
                .create(tokens[REQUEST_URI]);
        HttpVersion version = HttpVersion.of(tokens[HTTP_VERSION]);
        Map<String, String> fields = extractFields(br);

        return new HttpHeader(method, uri, version, fields);
    }

    private Map<String, String> extractFields(BufferedReader br) throws IOException {
        Map<String, String> fields = new HashMap<>();
        String line = br.readLine();
        while (Objects.nonNull(line) && !EMPTY.equals(line)) {
            int indexOfDelimiter = line.indexOf(HEADER_DELIMITER);
            String key = line.substring(0, indexOfDelimiter);
            String value = line.substring(indexOfDelimiter + 2);
            fields.put(key, value);
            line = br.readLine();
        }
        return fields;
    }

    private HttpBody extractBody(BufferedReader br, HttpHeader header) throws IOException {
        if (br.ready()) {
            int contentLength = parseInt(header.get(HttpHeaderFields.CONTENT_LENGTH));
            return new HttpBody(readData(br, contentLength));
        }
        return HttpBody.EMPTY_BODY;
    }
}
