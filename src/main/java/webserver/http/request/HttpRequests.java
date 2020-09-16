package webserver.http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequests {
    private static final String PARSE_REGEX = " ";
    private static final String HTTP_METHOD = "HttpMethod";
    private static final String URL = "URL";
    private static final String HTTP_VERSION = "HttpVersion";

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
    }

    private void parseHttpMethodLine(String httpMethodLine) {
        String[] httpMethodLines = httpMethodLine.split(PARSE_REGEX);
        requests.put(HTTP_METHOD, httpMethodLines[0]);
        requests.put(URL, httpMethodLines[1]);
        requests.put(HTTP_VERSION, httpMethodLines[2]);
    }

    public String getMethodType() {
        return requests.get(HTTP_METHOD);
    }

    public String getUrl() {
        return requests.get(URL);
    }
}
