package utils;

import java.util.Map;

import web.http.HttpResponseLine;

public class FormatUtils {
    public static String formatResponseLine(HttpResponseLine httpResponseLine) {
        return String.format("%s %s %s\n", httpResponseLine.getProtocol().getProtocol(),
            httpResponseLine.getHttpStatus().getStatusCode(), httpResponseLine.getHttpStatus().name());
    }

    public static String formatHeader(Map.Entry<String, Object> line) {
        return String.format("%s: %s\n", line.getKey(), line.getValue());
    }
}
