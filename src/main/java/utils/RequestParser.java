package utils;

import http.request.HttpRequest;
import http.request.HttpRequestBody;
import http.request.HttpRequestHeader;
import http.request.HttpRequestLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestParser {

    public static HttpRequest parse(InputStream inputStream) throws IOException {
        List<String> lines = readAllLines(inputStream);

        int lastIndex = getLastIndex(lines);

        HttpRequestLine httpRequestLine = new HttpRequestLine(lines.get(0));
        HttpRequestHeader httpRequestHeader = makeHeader(lines.subList(1, lastIndex));
        HttpRequestBody httpRequestBody = new HttpRequestBody(Collections.emptyList());
        if (httpRequestHeader.getContentLength() != 0) {
            httpRequestBody = new HttpRequestBody(lines.subList(lastIndex + 1, lines.size()));
        }

        return new HttpRequest(httpRequestLine, httpRequestHeader, httpRequestBody);
    }

    private static List<String> readAllLines(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = bufferedReader.readLine();
        List<String> lines = new ArrayList<>();

        String contentLength = "0";

        while (!"".equals(line)) {
            lines.add(line);
            line = bufferedReader.readLine();
            if (line.contains("Content-Length")) {
                contentLength = line.split(": ")[1];
            }
        }
        lines.add("");

        if (bufferedReader.ready()) {
            lines.add(IOUtils.readData(bufferedReader, Integer.parseInt(contentLength)));
        }
        return lines;
    }

    private static int getLastIndex(List<String> lines) {
        int lastIndex = 0;
        String line = lines.get(0);
        while (!"".equals(line)) {
            lastIndex++;
            line = lines.get(lastIndex);
        }
        return lastIndex;
    }

    private static HttpRequestHeader makeHeader(List<String> headerLines) {
        Map<String, String> headers = new HashMap<>();

        headerLines.forEach(header -> {
            String[] keyValue = header.split(": ");
            headers.put(keyValue[0], keyValue[1]);
        });

        return new HttpRequestHeader(headers);
    }
}
