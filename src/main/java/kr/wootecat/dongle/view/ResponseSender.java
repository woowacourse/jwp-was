package kr.wootecat.dongle.view;

import static kr.wootecat.dongle.view.HttpResponseDataParser.*;

import java.io.DataOutputStream;
import java.io.IOException;

import kr.wootecat.dongle.model.http.response.HttpResponse;

public class ResponseSender {

    private ResponseSender() {
    }

    public static void send(DataOutputStream dos, HttpResponse response) throws IOException {
        sendResponseHeader(dos, response);
        sendResponseBodyIfPresent(dos, response);
    }

    private static void sendResponseHeader(DataOutputStream dos, HttpResponse response) throws IOException {
        String responseLine = parseResponseLine(response.getStatusLine());
        String responseHeaders = parseResponseHeaders(response.getResponseHeaders());
        dos.writeBytes(responseLine + responseHeaders);
    }

    private static void sendResponseBodyIfPresent(DataOutputStream dos, HttpResponse response) throws IOException {
        byte[] body = response.getBody();
        if (body == null) {
            return;
        }
        dos.write(body, 0, body.length);
    }
}
