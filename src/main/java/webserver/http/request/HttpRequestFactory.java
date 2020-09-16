package webserver.http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import utils.IOUtils;

public class HttpRequestFactory {
    private HttpRequestFactory() {
    }

    public static HttpRequest create(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        RequestStartLine requestStartLine = RequestStartLine.from(bufferedReader.readLine());
        RequestHeaders requestHeaders = getHttpHeaders(bufferedReader);

        String body = IOUtils.readData(bufferedReader, requestHeaders.getContentLength());
        return new HttpRequest(requestStartLine, requestHeaders, new RequestBody(body));
    }

    private static RequestHeaders getHttpHeaders(BufferedReader bufferedReader) throws IOException {
        List<String> headers = new ArrayList<>();
        String headerLine;
        while (Objects.nonNull(headerLine = bufferedReader.readLine()) && !"".equals(headerLine)) {
            headers.add(headerLine);
        }

        return RequestHeaders.from(headers);
    }
}
