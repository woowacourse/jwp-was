package http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class HttpResponseConverter {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponseConverter.class);

    public static void response3xx(final DataOutputStream dos, final HttpResponse httpResponse) {
        try {
            responseHeader(dos, httpResponse);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static void response2xx(final DataOutputStream dos, final HttpResponse httpResponse) {
        try {
            responseHeader(dos, httpResponse);
            responseBody(dos, httpResponse.getHttpResponseBody());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void responseHeader(final DataOutputStream dos, final HttpResponse httpResponse) throws IOException {
        dos.writeBytes(httpResponse.getStartLine() + "\r\n");

        for (Map.Entry<String, String> element : httpResponse.getHttpHeader().getHeaders().entrySet()) {
            dos.writeBytes(element.getKey() + ": " + element.getValue() + "\r\n");
        }
        dos.writeBytes("\r\n");
    }

    private static void responseBody(final DataOutputStream dos, final HttpResponseBody httpResponseBody) {
        try {
            dos.write(httpResponseBody.getBody(), 0, httpResponseBody.getLength());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
