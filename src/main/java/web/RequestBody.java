package web;

import java.io.BufferedReader;
import java.io.IOException;
import utils.IOUtils;

public class RequestBody {

    private String body;

    public RequestBody(BufferedReader bufferedReader, int contentLength) throws IOException {
        if (contentLength == 0) {
            return;
        }
        this.body = IOUtils.readData(bufferedReader, contentLength);
    }

    public String getBody() {
        return body;
    }
}
