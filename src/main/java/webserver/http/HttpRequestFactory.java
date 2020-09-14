package webserver.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import utils.IOUtils;

public class HttpRequestFactory {
    public static HttpRequest create(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        HttpStartLine httpStartLine = HttpStartLine.from(bufferedReader.readLine());
        HttpHeaders httpHeaders = getHttpHeaders(bufferedReader);

        String body = IOUtils.readData(bufferedReader, httpHeaders.getContentLength());
        return new HttpRequest(httpStartLine, httpHeaders, new HttpBody(body));
    }

    private static HttpHeaders getHttpHeaders(BufferedReader bufferedReader) throws IOException {
        List<String> headers = new ArrayList<>();
        String headerLine;
        while (Objects.nonNull(headerLine = bufferedReader.readLine()) && !"".equals(headerLine)) {
            headers.add(headerLine);
        }

        return HttpHeaders.from(headers);
    }
}
