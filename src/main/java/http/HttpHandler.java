package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpResponse.class);

    public static HttpRequest parse(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        HttpRequestLine httpRequestLine = parseStartLine(br);
        Map<String, String> headers = parseHeaders(br);
        Map<String, String> body = new HashMap<>();
        if (headers.containsKey("Content-Length")) {
            body = parseBody(br, Integer.valueOf(headers.get("Content-Length")));
        }

        return new HttpRequest(httpRequestLine, headers, body);
    }

    private static HttpRequestLine parseStartLine(BufferedReader br) throws IOException {
        String startLine = br.readLine();
        String[] splitStartLine = startLine.split(" ");
        String[] splitPath = splitStartLine[1].split("\\?");
        String method = splitStartLine[0];
        String path = splitPath[0];
        String httpVersion = splitStartLine[2];
        Map<String, String> params = new HashMap<>();

        if (splitPath.length > 1) {
            String queryString = splitPath[1];
            String[] queryParams = URLDecoder.decode(queryString, StandardCharsets.UTF_8.toString()).split("&");
            for (String queryParam : queryParams) {
                String[] pair = queryParam.split("=");
                params.put(pair[0], pair[1]);
            }
        }
        return new HttpRequestLine(method, path, httpVersion, params);
    }

    private static Map<String, String> parseHeaders(BufferedReader br) throws IOException {
        Map<String, String> headers = new HashMap<>();

        String line;
        while (!"".equals(line = br.readLine()) && line != null) {
            String[] splitHeader = line.split(": ");
            headers.put(splitHeader[0], splitHeader[1]);
        }
        return headers;
    }

    private static Map<String, String> parseBody(BufferedReader br, Integer contentLength) throws IOException {
        Map<String, String> body = new HashMap<>();
        String requestBody = IOUtils.readData(br, contentLength);
        String decodedBody = URLDecoder.decode(requestBody, StandardCharsets.UTF_8.toString());
        String[] bodyPairs = decodedBody.split("&");
        for (String bodyPair : bodyPairs) {
            String[] pair = bodyPair.split("=");
            body.put(pair[0], pair[1]);
        }
        return body;
    }

    public static void send(OutputStream out, HttpResponse httpResponse) {
        try (DataOutputStream dos = new DataOutputStream(out)) {
            writeStartLine(dos, httpResponse);
            writeHeader(dos, httpResponse);
            writeBody(dos, httpResponse);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static void writeStartLine(final DataOutputStream dos, HttpResponse response) throws IOException {
        dos.writeBytes(String.format("%s %s %s\n", response.getHttpVersion(),
                response.getHttpStatus().getCode(), response.getHttpStatus().getPhrase()));
    }

    private static void writeHeader(final DataOutputStream dos, HttpResponse response) throws IOException {
        Map<String, String> headers = response.getHeaders();
        for (String key : headers.keySet()) {
            dos.writeBytes(String.format("%s: %s\n", key, headers.get(key)));
        }
        dos.writeBytes("\n");
    }

    private static void writeBody(final DataOutputStream dos, HttpResponse response) throws IOException {
        byte[] body = response.getBody();
        if (body != null) {
            dos.write(body, 0, body.length);
        }
    }
}
