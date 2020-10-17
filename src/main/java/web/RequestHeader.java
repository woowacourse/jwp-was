package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class RequestHeader {

    public RequestHeader(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();

        while (!line.equals("")) {
            line = bufferedReader.readLine();
            if (Objects.isNull(line)) {
                break;
            }
            System.out.println(line);
        }
    }
}
