package model.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import model.general.ContentType;
import model.general.Header;
import model.general.Method;
import utils.IOUtils;

public class Request {

    private static final String EMPTY_LINE = "";
    private static final String HEADER_KEY_VALUE_SEPARATOR = ": ";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private final RequestLine requestLine;
    private final Map<Header, String> headers;
    private final MessageBody messageBody;

    private Request(RequestLine line, Map<Header, String> headers, MessageBody messageBody) {
        this.requestLine = line;
        this.headers = headers;
        this.messageBody = messageBody;
    }

    public static Request of(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        RequestLine requestLine = RequestLine.of(bufferedReader.readLine());
        Map<Header, String> headers = addHeaders(bufferedReader);

        if (headers.containsKey(Header.CONTENT_LENGTH)) {
            int contentLength = Integer.parseInt(headers.get(Header.CONTENT_LENGTH));
            String requestBody = IOUtils.readData(bufferedReader, contentLength);
            MessageBody messageBody = MessageBody.of(requestBody);

            return new Request(requestLine, headers, messageBody);
        }
        return new Request(requestLine, headers, null);
    }

    private static Map<Header, String> addHeaders(BufferedReader bufferedReader)
        throws IOException {
        Map<Header, String> headers = new HashMap<>();

        String line = bufferedReader.readLine();
        while (Objects.nonNull(line) && !EMPTY_LINE.equals(line)) {
            Header key = Header.of(line.split(HEADER_KEY_VALUE_SEPARATOR)[KEY_INDEX]);
            String value = line.split(HEADER_KEY_VALUE_SEPARATOR)[VALUE_INDEX];
            headers.put(key, value);
            line = bufferedReader.readLine();
        }
        return headers;
    }

    public boolean isSameMethod(Method method) {
        return requestLine.isSameMethod(method);
    }

    public boolean containsUri(String uri) {
        return requestLine.containsUri(uri);
    }

    public ContentType generateContentTypeFromRequestUri() {
        return requestLine.generateContentTypeFromRequestUri();
    }

    public Map<String, String> extractParameters() {
        if (requestLine.isSameMethod(Method.GET)) {
            return requestLine.extractGetParameters();
        }
        return messageBody.extractPostParameters();
    }

    public Method getMethod() {
        return requestLine.getMethod();
    }

    public String getRequestUri() {
        return requestLine.getRequestUri();
    }

    public String getHttpVersion() {
        return requestLine.getHttpVersion();
    }
}
