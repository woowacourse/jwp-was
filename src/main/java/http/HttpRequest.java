package http;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final String method;
    private final String path;
    private final String httpVersion;
    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, String> params = new HashMap<>();
    private final Map<String, String> body = new HashMap<>();

    public HttpRequest(final InputStream is) throws IOException {
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        //startLine
        String requestLine = br.readLine();
        String[] split = requestLine.split(" ");
        method = split[0];
        httpVersion = split[2];

        // query params
        String[] splitPath = split[1].split("\\?");
        path = splitPath[0];

        if (splitPath.length > 1) {
            String[] queryParams = URLDecoder.decode(splitPath[1], StandardCharsets.UTF_8.toString()).split("&");
            for (String queryParam : queryParams) {
                String[] pair = queryParam.split("=");
                params.put(pair[0], pair[1]);
            }
        }

        // headers
        String line;
        while (!"".equals(line = br.readLine())) {
            if (line == null) {
                break;
            }
            final String[] splitHeader = line.split(": ");
            headers.put(splitHeader[0], splitHeader[1]);
        }

        // body
        if (headers.get("Content-Length") != null) {
            String requestBody = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
            final String decode = URLDecoder.decode(requestBody, StandardCharsets.UTF_8.toString());
            String[] bodyPairs = decode.split("&");
            for (String bodyPair : bodyPairs) {
                String[] pair = bodyPair.split("=");
                body.put(pair[0], pair[1]);
            }
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

    public String getParam(final String key) {
        return params.get(key);
    }

    public String getBody(final String key) {
        return body.get(key);
    }
}
