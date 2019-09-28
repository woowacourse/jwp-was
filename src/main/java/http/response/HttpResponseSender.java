package http.response;

import http.exception.EmptyStatusException;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponseSender {

    public static void send(HttpResponse httpResponse, DataOutputStream dos) throws IOException {
        if (httpResponse.getStatus() == null) {
            throw new EmptyStatusException();
        }

        dos.writeBytes(httpResponse.getStatusLine());
        dos.writeBytes(httpResponse.getHeaderLines());

        byte[] body = httpResponse.getBody();
        if (body != null) {
            dos.write(body, 0, body.length);
        }
        dos.flush();
    }
}
