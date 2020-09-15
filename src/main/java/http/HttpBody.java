package http;

import java.io.BufferedReader;
import java.io.IOException;

import exception.RequestBodyCreateFailException;
import http.request.ContentType;
import utils.IOUtils;

public class HttpBody {
    private String content;
    private ContentType contentType;

    public HttpBody(String content, ContentType contentType) {
        this.content = content;
        this.contentType = contentType;
    }

    public static HttpBody from(BufferedReader bufferedReader, ContentType contentType, int contentLength) {
        try {
            String content = extractBody(bufferedReader, contentLength);
            content = contentType.parse(content);
            return new HttpBody(content, contentType);
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

    public ContentType getContentType() {
        return contentType;
    }
}
