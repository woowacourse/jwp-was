package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class Request {

    private final RequestLine requestLine;
    private final Headers headers;
    private final MessageBody messageBody;

    private Request(RequestLine line, Headers headers, MessageBody messageBody) {
        this.requestLine = line;
        this.headers = headers;
        this.messageBody = messageBody;
    }

    public static Request of(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        RequestLine requestLine = RequestLine.of(bufferedReader.readLine());
        Headers headers = Headers.of(bufferedReader);

        if (headers.hasContent()) {
            MessageBody messageBody = MessageBody.of(bufferedReader.readLine());
            return new Request(requestLine, headers, messageBody);
        }
        return new Request(requestLine, headers, null);
    }

    public boolean isSameMethod(Method method) {
        return requestLine.isSameMethod(method);
    }

    public ContentType generateContentTypeFromRequestUri() {
        return requestLine.generateContentTypeFromRequestUri();
    }

    public String getRequestUri() {
        return requestLine.getRequestUri();
    }

    public Map<String, String> extractParameters() {
        if (requestLine.isSameMethod(Method.GET)) {
            return requestLine.extractParameters();
        }
        return messageBody.extractParameters();
    }
}
