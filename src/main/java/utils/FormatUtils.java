package utils;

import webserver.http.response.HttpResponseLine;

import java.util.Map;

public class FormatUtils {
    public static String formatResponseLine(HttpResponseLine httpResponseLine) {
        return String.format("%s %s %s\n", httpResponseLine.getProtocol().getProtocol(),
                httpResponseLine.getHttpStatus().name(), httpResponseLine.getHttpStatus().getStatusCode());
    }

    public static String formatHeader(Map.Entry<String, String> line) {
        return String.format("%s: %s\n", line.getKey(), line.getValue());
    }
}
