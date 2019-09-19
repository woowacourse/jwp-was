package http.request;

import http.HTTP;
import http.HttpRequestCoreInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestHeader {
    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);

    private final Map<String, String> headerContents = new HashMap<>();

    public RequestHeader(BufferedReader bufferedReader) throws IOException {
        createHeader(bufferedReader);
    }

    private void createHeader(BufferedReader bufferedReader) throws IOException {
        createCoreHeader(bufferedReader);
        String line;
        while (true) {
            line = bufferedReader.readLine();

            if (line == null || line.equals("")) {
                break;
            }

            logger.info("request header contents: {}", line);
            String[] keyValue = line.split(": ");
            headerContents.put(keyValue[0], keyValue[1]);
        }
    }

    private void createCoreHeader(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        String[] tokens = line.split(" ");

        headerContents.put(HttpRequestCoreInfo.METHOD.name(), tokens[0]);
        createAddress(tokens[1]);

        headerContents.put(HttpRequestCoreInfo.HTTP_VERSION.name(), tokens[2]);
    }

    private void createAddress(String token) {
        String[] address = token.split("\\?");
        if (address.length > 1) {
            headerContents.put(HttpRequestCoreInfo.QUERY_STRING.name(), address[1]);
        }
        headerContents.put(HttpRequestCoreInfo.PATH.name(), address[0]);
    }

    public boolean contains(HTTP http) {
        return headerContents.containsKey(http.getPhrase());
    }

    public String getHeaderContents(String key) {
        System.out.println(headerContents.getOrDefault(key, ""));
        return headerContents.getOrDefault(key, "");
    }
}
