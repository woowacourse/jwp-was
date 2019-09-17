package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private final String method;
    private final String path;
    private final String httpVersion;
    private final Map<String, String> headers = new HashMap<>();

    public Request(final InputStream is) throws IOException {
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String requestLine = br.readLine();
        String[] split = requestLine.split(" ");
        method = split[0];
        path = split[1];
        httpVersion = split[2];

        String line;
        while (!"".equals(line = br.readLine())) {
            if(line == null){
                break;
            }
            final String[] splitHeader = line.split(": ");
            headers.put(splitHeader[0], splitHeader[1]);
        }
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }
}
