package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

public class Body {

    private String body;

    public Body(BufferedReader bufferedReader, Object contentLength) throws IOException {
        parseBody(bufferedReader, contentLength);
    }

    private void parseBody(BufferedReader bufferedReader, Object contentLength) throws IOException {
        int readSize = Optional.ofNullable(contentLength)
            .map(x -> (String) x)
            .map(Integer::parseInt)
            .orElse(0);

        this.body = IOUtils.readData(bufferedReader, readSize);
    }

    public String getBody() {
        return body;
    }
}
