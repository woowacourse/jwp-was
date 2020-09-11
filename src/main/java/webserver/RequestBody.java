package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;
import utils.IOUtils;

public class RequestBody {

    private String body;

    public RequestBody(BufferedReader bufferedReader, String contentLength) throws IOException {
        if (Objects.nonNull(contentLength)) {
            body = IOUtils.readData(bufferedReader, Integer.parseInt(contentLength));
        }
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "RequestBody{" +
            "body='" + body + '\'' +
            '}';
    }
}
