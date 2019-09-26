package http.request;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequestFactory {
    public static HttpRequest create(BufferedReader bufferedReader) throws IOException {
        HttpRequestHeader header = new HttpRequestHeader(IOUtils.parseHeader(bufferedReader));

        if ("POST".equals(header.getMethod())) {
            String body = IOUtils.readData(bufferedReader, Integer.parseInt(header.get("content-length")));
            HttpRequestBody httpRequestBody = new HttpRequestBody(body);
            return new HttpRequest(header, httpRequestBody);
        }
        return new HttpRequest(header);
    }
}
