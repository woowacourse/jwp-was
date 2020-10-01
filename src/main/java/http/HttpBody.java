package http;

import java.io.BufferedReader;
import java.io.IOException;

import exception.RequestBodyCreateFailException;
import utils.IOUtils;

public class HttpBody {
    private String content;

    public HttpBody(String content) {
        this.content = content;
    }

    public static HttpBody empty() {
        return new HttpBody("");
    }

    public static HttpBody of(String content) {
        return new HttpBody(content);
    }

    public static HttpBody of(BufferedReader bufferedReader, ContentType contentType, int contentLength) {
        try {
            String content = extractBody(bufferedReader, contentLength);
            content = contentType.parse(content);
            return new HttpBody(content);
        } catch (IOException e) {
            throw new RequestBodyCreateFailException();
        }
    }

    private static String extractBody(BufferedReader bufferedReader, int contentLength) throws IOException {
        return IOUtils.readDataWithinLength(bufferedReader, contentLength);
    }

    public String getContent() {
        return content;
    }
}
