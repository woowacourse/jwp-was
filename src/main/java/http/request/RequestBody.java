package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import utils.IOUtils;

public class RequestBody {
    private String content;
    private ContentType contentType;

    public RequestBody(String content, ContentType contentType) {
        this.content = content;
        this.contentType = contentType;
    }

    public static RequestBody from(BufferedReader bufferedReader, ContentType contentType, int contentLength) {
        try {
            String content = extractBody(bufferedReader, contentLength);
            content = contentType.parse(content);
            return new RequestBody(content, contentType);
        } catch (IOException e) {
            throw new RuntimeException("IO EXCEPTION 발생");
        }
    }

    private static String extractBody(BufferedReader bufferedReader, int contentLength) throws IOException {
        return IOUtils.readDataWithinLength(bufferedReader, contentLength);
    }

    public String getContent() {
        return content;
    }

    public ContentType getContentType() {
        return contentType;
    }

    private static String decode(String input) {
        try {
            return URLDecoder.decode(input, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError();
        }
    }
}
