package webserver.utils;

import static java.lang.System.lineSeparator;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import webserver.dto.HttpResponse;

public class ResponseUtils {

    private static final String BLANK = " ";
    private static final String HEADER_KEY_VALUE_DELIMITER = ": ";

    private ResponseUtils() {
    }

    public static void response(DataOutputStream dos, HttpResponse httpResponse)
        throws IOException {
        responseLine(dos, httpResponse);
        dos.writeBytes(lineSeparator());

        Map<String, String> headers = httpResponse.getHeaders();
        responseHeaders(dos, headers);
        dos.writeBytes(lineSeparator());

        responseBody(dos, httpResponse.getBody());
    }

    private static void responseLine(DataOutputStream dos, HttpResponse httpResponse)
        throws IOException {
        dos.writeBytes(httpResponse.getProtocol());
        dos.writeBytes(BLANK);
        dos.writeBytes(httpResponse.getHttpStatusCode().getCodeAndMessage());
    }

    private static void responseHeaders(DataOutputStream dos, Map<String, String> headers)
        throws IOException {
        for (String key : headers.keySet()) {
            dos.writeBytes(key);
            dos.writeBytes(HEADER_KEY_VALUE_DELIMITER);
            dos.writeBytes(headers.get(key));
            dos.writeBytes(lineSeparator());
        }
    }

    private static void responseBody(DataOutputStream dos, byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }
}
