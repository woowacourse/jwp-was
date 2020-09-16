package webserver.http.request;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpRequests {
    private static final String PARSE_REGEX = " ";
    private static final String HTTP_METHOD = "HttpMethod";
    private static final String URL = "URL";
    private static final String HTTP_VERSION = "HttpVersion";
    private static final String REQUEST_PARSE_REGEX = ": ";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String BODY = "body";
    private static final String EMPTY_STRING = "";

    private final Map<String, String> requests;

    public HttpRequests(InputStream inputStream) throws IOException {
        requests = new HashMap<>();
        initRequests(inputStream);
    }

    private void initRequests(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String httpMethodLine = bufferedReader.readLine();
        parseHttpMethodLine(httpMethodLine);
        parseHttpRequest(bufferedReader);
    }

    private void parseHttpMethodLine(String httpMethodLine) {
        String[] httpMethodLines = httpMethodLine.split(PARSE_REGEX);
        requests.put(HTTP_METHOD, httpMethodLines[0]);
        requests.put(URL, httpMethodLines[1]);
        requests.put(HTTP_VERSION, httpMethodLines[2]);
    }

    private void parseHttpRequest(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        while (!EMPTY_STRING.equals(line)) {
            if (Objects.isNull(line)) {
                break;
            }
            String[] lines = line.split(REQUEST_PARSE_REGEX);
            requests.put(lines[0], lines[1]);
            line = bufferedReader.readLine();
        }
        putBody(bufferedReader);
    }

    private void putBody(BufferedReader bufferedReader) throws IOException {
        if (requests.containsKey(CONTENT_LENGTH)) {
            String body = IOUtils.readData(bufferedReader, getContentLength());
            requests.put(BODY, body);
        }
    }

    private int getContentLength() {
        return Integer.parseInt(requests.get(CONTENT_LENGTH));
    }

    public String getMethodType() {
        return requests.get(HTTP_METHOD);
    }

    public String getUrl() {
        return requests.get(URL);
    }

    public String getBody() {
        return requests.get(BODY);
    }
}
