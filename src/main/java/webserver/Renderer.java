package webserver;

import webserver.response.HttpResponse;
import webserver.response.ResponseBody;

import java.io.DataOutputStream;
import java.io.IOException;

public class Renderer {
    public static void render(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        renderStatusLine(dos, httpResponse);
        renderHeader(dos, httpResponse);
        renderBody(dos, httpResponse);
    }

    private static void renderStatusLine(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        dos.writeBytes(httpResponse.responseLine());
    }

    private static void renderHeader(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        dos.writeBytes(httpResponse.responseHeader());
    }

    private static void renderBody(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        ResponseBody body = httpResponse.getResponseBody();
        if (body.isEmpty()) {
            return;
        }

        dos.writeBytes("\r\n");
        dos.write(body.getContent(), 0, body.getLengthOfContent());
        dos.flush();
    }
}
