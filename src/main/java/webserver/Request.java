package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class Request {
    private final String request;

    public Request(InputStream inputStream) throws IOException {
        if (Objects.isNull(inputStream)) {
            this.request = "";
            return;
        }
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        this.request = parse(bufferedReader);
    }

    private String parse(BufferedReader bufferedReader) throws IOException {
        StringBuilder builder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (Objects.nonNull(line) && !line.isEmpty()) {
            builder.append(line).append(System.lineSeparator());
            line = bufferedReader.readLine();
        }
        return builder.toString();
    }

    public String getPath() {
        String[] firstLine = request
            .split(System.lineSeparator())[0]
            .split(" ");
        return firstLine[1];
    }

    @Override
    public String toString() {
        return request;
    }
}
