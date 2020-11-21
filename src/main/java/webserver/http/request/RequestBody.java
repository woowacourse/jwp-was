package webserver.http.request;

import java.io.BufferedReader;
import java.io.IOException;

import utils.IOUtils;

public class RequestBody {
    private final static RequestBody EMPTY_REQUEST_BODY = new RequestBody("");

    private final String body;

    private RequestBody(String body) {
        this.body = body;
    }

    public static RequestBody of(BufferedReader bufferedReader, String contentLength) throws IOException {
        if (contentLength == null || contentLength.isEmpty()) {
            return EMPTY_REQUEST_BODY;
        }

        String body = IOUtils.readData(bufferedReader, Integer.parseInt(contentLength));
        return new RequestBody(body);
    }

    public String getBody() {
        return body;
    }
}
