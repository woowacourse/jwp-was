package web;

import java.io.BufferedReader;
import java.io.IOException;

import utils.IOUtils;

public class RequestBody {

    private String body;

    public RequestBody(BufferedReader br, int contentLength) throws IOException {
        if (hasNoBody(contentLength)) {
            return;
        }
        this.body = IOUtils.readData(br, contentLength);
    }

    private boolean hasNoBody(int contentLength) {
        return contentLength == 0;
    }

    public String getBody() {
        return body;
    }
}
