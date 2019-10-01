package http;

import http.view.TemplateViewResolver;
import http.view.View;
import http.view.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpResponse {
    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);

    private HttpRequest request;

    private DataOutputStream dos = null;

    private Map<String, String> headers = new HashMap<String, String>();

    public HttpResponse(HttpRequest request, OutputStream out) {
        this.request = request;
        this.dos = new DataOutputStream(out);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void forward(String path) {
        try {
            ViewResolver viewResolver = new TemplateViewResolver();
            View view = viewResolver.resolveViewName(path);
            view.render(request, this);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void response200Header(int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            processHeaders();
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void sendRedirect(String redirectUrl) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            processHeaders();
            dos.writeBytes("Location: " + redirectUrl + " \r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void processHeaders() {
        try {
            Set<String> keys = headers.keySet();
            for (String key : keys) {
                dos.writeBytes(key + ": " + headers.get(key) + " \r\n");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void response404() {
        try {
            dos.writeBytes("HTTP/1.1 404 Not Found \r\n");
            processHeaders();
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
