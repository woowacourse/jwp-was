package http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class HttpResponseSender {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponseSender.class);

    public static void send(final DataOutputStream dos, final HttpResponse httpResponse) {
        try {
            responseHeader(dos, httpResponse);
            responseBody(dos, httpResponse.getHttpResponseBody());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void responseHeader(final DataOutputStream dos, final HttpResponse httpResponse) throws IOException {
        dos.writeBytes(httpResponse.getStatusLine().getHttpVersion() + " " + httpResponse.getStatusLine().getHttpStatus() + "\r\n");

        for (Map.Entry<String, String> element : httpResponse.getHttpHeader().getHeaders().entrySet()) {
            dos.writeBytes(element.getKey() + ": " + element.getValue() + "\r\n");
        }
        dos.writeBytes("\r\n");
    }

    private static void responseBody(DataOutputStream dos, HttpResponseBody httpResponseBody) {
        try {
            dos.write(httpResponseBody.getBody(), 0, httpResponseBody.getLength());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
