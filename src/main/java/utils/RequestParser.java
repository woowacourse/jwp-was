package utils;

import http.request.HttpRequest;
import http.request.HttpRequestBody;
import http.request.HttpRequestHeader;
import http.request.HttpRequestLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {

    public static HttpRequest parse(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        HttpRequestLine httpRequestLine = readRequestLine(bufferedReader);
        HttpRequestHeader httpRequestHeader = readHeaders(bufferedReader);

        HttpRequestBody httpRequestBody = new HttpRequestBody(new byte[0]);
        if (httpRequestHeader.getContentLength() != 0) {
            httpRequestBody = readBody(bufferedReader, httpRequestHeader.getContentLength());
        }

        return new HttpRequest(httpRequestLine, httpRequestHeader, httpRequestBody);
    }

    private static HttpRequestLine readRequestLine(BufferedReader bufferedReader) throws IOException {
        return new HttpRequestLine(bufferedReader.readLine());
    }

    private static HttpRequestHeader readHeaders(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        Map<String, String> headers = new HashMap<>();

        while (!"".equals(line)) {
            String[] keyValue = line.split(": ");
            headers.put(keyValue[0], keyValue[1]);
            line = bufferedReader.readLine();
        }

        return new HttpRequestHeader(headers);
    }

    private static HttpRequestBody readBody(BufferedReader bufferedReader, int contentLength) throws IOException {
        return new HttpRequestBody(IOUtils.readData(bufferedReader, contentLength).getBytes());
    }
}
