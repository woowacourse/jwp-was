package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Request {
    List<String> parsedBufferedReader(BufferedReader br, String line) throws IOException;

    Map<String, String> getParams();

    RequestPath getRequestPath();
}
