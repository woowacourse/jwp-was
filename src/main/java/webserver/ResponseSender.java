package webserver;

import http.response.HttpResponse;
import view.View;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static http.HttpHeaders.CONTENT_LENGTH;
import static http.HttpHeaders.CONTENT_TYPE;

class ResponseSender {
    static void send(OutputStream out, HttpResponse response) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        View view = response.getView();
        byte[] body = view.render();

        if (body != null) {
            response.setHeader(CONTENT_TYPE, view.getMimeType().toString());
            response.setHeader(CONTENT_LENGTH, Integer.toString(body.length));
        }
        dos.writeBytes(makeHeaderMessages(response));

        if (body != null) {
            dos.write(body);
        }
    }

    private static String makeHeaderMessages(HttpResponse response) {
        return response.getVersion().toString() + " " + response.getStatus().getMessage() + "\r\n"
                + response.getHeaders()
                + "\r\n";
    }
}
