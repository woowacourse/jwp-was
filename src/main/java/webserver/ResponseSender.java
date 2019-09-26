package webserver;

import http.response.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

class ResponseSender {
    public static final String CRLF = "\r\n";

    static void send(OutputStream out, HttpResponse response) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.writeBytes(makeHeaderMessages(response));

        byte[] body = response.getBody();
        if (body != null) {
            dos.write(body);
        }
    }

    private static String makeHeaderMessages(HttpResponse response) {
        return response.getVersion().toString() + " " + response.getStatus().getMessage() + CRLF
                + response.getHeaders()
                + CRLF;
    }
}
