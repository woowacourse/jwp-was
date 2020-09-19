package http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseEntity {
    public static final Logger logger = LoggerFactory.getLogger(ResponseEntity.class);
    private static final String lineSeparator = System.lineSeparator();

    public static void build(HttpResponse httpResponse, OutputStream out) {
        DataOutputStream dataOutputStream = new DataOutputStream(out);
        try {
            responseStatus(httpResponse, dataOutputStream);
            responseHeader(httpResponse, dataOutputStream);
            responseBody(httpResponse, dataOutputStream);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void responseStatus(HttpResponse httpResponse, DataOutputStream dataOutputStream) throws
        IOException {
        dataOutputStream.writeBytes(
            httpResponse.getVersion().getVersion() + " " + httpResponse.getStatus().getMessage() + lineSeparator);
    }

    private static void responseHeader(HttpResponse httpResponse, DataOutputStream dataOutputStream) throws
        IOException {
        for (Map.Entry<String, String> headerKeyValue : httpResponse.getHeader().getHeaders().entrySet()) {
            dataOutputStream.writeBytes(headerKeyValue.getKey() + ": " + headerKeyValue.getValue() + lineSeparator);
        }
        // HEADER 와 BODY 사이릃 한 줄 띄워야 한다.
        dataOutputStream.writeBytes(lineSeparator);
    }

    private static void responseBody(HttpResponse httpResponse, DataOutputStream dataOutputStream) throws IOException {
        byte[] body = httpResponse.getBody();
        dataOutputStream.write(body, 0, body.length);
        dataOutputStream.flush();
    }

}
