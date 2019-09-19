package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public interface Request {
    List<String> parsedBufferedReader(BufferedReader br, String line) throws IOException;
}
