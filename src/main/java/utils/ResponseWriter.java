package utils;

import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseWriter {
    private static final Logger logger = LoggerFactory.getLogger(ResponseWriter.class);

    public static void write(DataOutputStream dataOutputStream, HttpResponse httpResponse) throws IOException {
        dataOutputStream.writeBytes(httpResponse.getHttpStatusLine().toString());
        dataOutputStream.writeBytes(httpResponse.getHttpResponseHeader().toString());

        if (httpResponse.getHttpResponseBody() != null) {
            responseBody(dataOutputStream, httpResponse.getHttpResponseBody().getBody());
        }
    }

    private static void responseBody(DataOutputStream dataOutputStream, byte[] body) {
        try {
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
