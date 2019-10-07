package webserver;

import webserver.controller.response.HttpResponse;
import webserver.controller.response.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Renderer {
    private Renderer() {
    }

    public static Renderer getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void render(DataOutputStream outputStream, HttpResponse httpResponse) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        HttpStatus httpStatus = httpResponse.getHttpStatus();
        renderResponseLine(httpResponse, dataOutputStream, httpStatus);

        if (httpResponse.hasCookie()) {
            renderResponseCookie(dataOutputStream, httpResponse.getCookieFields());
        }

        renderResponseFields(dataOutputStream, httpResponse.getHeaderFields());

        if (httpResponse.hasBody()) {
            renderResponseBody(httpResponse, dataOutputStream);
        }

        dataOutputStream.flush();
    }

    private void renderResponseCookie(DataOutputStream dataOutputStream, List<String> cookieFields) throws IOException {
        for (String cookieField : cookieFields) {
            dataOutputStream.writeBytes("Set-Cookie: " + cookieField + "; Path=/ \r\n");
        }
    }

    private void renderResponseLine(HttpResponse httpResponse, DataOutputStream dataOutputStream, HttpStatus httpStatus) throws IOException {
        dataOutputStream.writeBytes(httpResponse.getVersion() + " " + httpStatus.getStatusCode() + " " + httpStatus.getStatus() + " \r\n");
    }

    private void renderResponseFields(DataOutputStream dataOutputStream, Map<String, String> responseHeaderFields) throws IOException {
        for (String key : responseHeaderFields.keySet()) {
            dataOutputStream.writeBytes(key + ": " + responseHeaderFields.get(key) + "\n");
        }
        dataOutputStream.writeBytes("\r\n");
    }

    private void renderResponseBody(HttpResponse httpResponse, DataOutputStream dataOutputStream) throws IOException {
        byte[] body = httpResponse.getBody();

        dataOutputStream.write(body, 0, body.length);
    }

    private static class LazyHolder {
        private static final Renderer INSTANCE = new Renderer();
    }
}
