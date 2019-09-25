package webserver;

import webserver.controller.response.HttpResponse;
import webserver.controller.response.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Optional;

public class Renderer {
    private Renderer() {
    }

    public static Renderer getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final Renderer INSTANCE = new Renderer();
    }

    public void render(OutputStream outputStream, HttpResponse httpResponse) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        HttpStatus httpStatus = httpResponse.getHttpStatus();
        Map<String, String> responseHeaderFields = httpResponse.getHeaderFields();
        renderResponseLine(httpResponse, dataOutputStream, httpStatus);
        renderResponseFields(dataOutputStream, responseHeaderFields);
        renderResponseBody(httpResponse, dataOutputStream);
    }

    private void renderResponseBody(HttpResponse httpResponse, DataOutputStream dataOutputStream) throws IOException {
        Optional<byte[]> maybeBody = httpResponse.getBody();
        if (maybeBody.isPresent()) {
            byte[] body = maybeBody.get();
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
            return;
        }
        dataOutputStream.flush();
    }

    private void renderResponseFields(DataOutputStream dataOutputStream, Map<String, String> responseHeaderFields) throws IOException {
        for (String key : responseHeaderFields.keySet()) {
            dataOutputStream.writeBytes(key + ": " + responseHeaderFields.get(key) + "\r\n");
        }
        dataOutputStream.writeBytes("\r\n");
    }

    private void renderResponseLine(HttpResponse httpResponse, DataOutputStream dataOutputStream, HttpStatus httpStatus) throws IOException {
        dataOutputStream.writeBytes(httpResponse.getVersion() + " " + httpStatus.getStatusCode() + " " + httpStatus.getStatus() + " \r\n");
    }
}
