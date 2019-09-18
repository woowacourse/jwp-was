package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RequestHeader {
    private Map<String, String> headers;

    private RequestHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public static RequestHeader of(InputStream inputStream) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String headerLine;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        String s = bufferedReader.readLine();
        String[] requestLine = s.split(" ");

        headers.put("Method", requestLine[0]);
        String fullUri = requestLine[1];

        if (fullUri.contains("?")) {
            String[] splitFullUri = fullUri.split("\\?");
            headers.put("Uri", splitFullUri[0]);

            Map<String, String> parameters = new HashMap<>();
            String[] queryParameters = splitFullUri[1].split("&");
            for (int i = 0; i < queryParameters.length; i++) {
                String[] queryParameter = queryParameters[i].split("=");
                parameters.put(queryParameter[0], queryParameter[1]);
            }
        }
        headers.put("Uri", fullUri);
        headers.put("Http-Version", requestLine[2]);

        while (!(headerLine = bufferedReader.readLine()).equals("")) {
            String[] splitHeader = headerLine.split(":");
            headers.put(splitHeader[0].trim(), splitHeader[1].trim());
        }

        return new RequestHeader(headers);
    }

    public String getValue(String key) {
        return headers.get(key);
    }

    @Override
    public String toString() {
        return "RequestHeader{" +
                "headers=" + headers +
                '}';
    }
}
