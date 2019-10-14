package http.supoort;

import http.model.HttpResponse;

import java.util.Map;

public class ResponseMessageConverter {
    private static final String SPACE = " ";
    private static final String LINE_BREAK = "\r\n";
    private static final String HEADER_SEPERATOR = ": ";

    public static String convertHeader(HttpResponse httpResponse) {
        Map<String, String> headers = httpResponse.getHttpHeaders().getHeaders();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(httpResponse.getStatusLine().getHttpProtocols().getProtocol() + SPACE);
        stringBuilder.append(httpResponse.getStatusLine().getHttpStatus().getMessage() + LINE_BREAK);
        for (String header : headers.keySet()) {
            stringBuilder.append(header + HEADER_SEPERATOR + headers.get(header) + LINE_BREAK);
        }
        stringBuilder.append(LINE_BREAK);
        return stringBuilder.toString();
    }
}
