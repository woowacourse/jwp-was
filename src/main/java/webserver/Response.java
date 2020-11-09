package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.FileIoUtils;

public class Response {
    public static final String EMPTY = "";
    public static final String BASE_URL = "http://localhost:8080";
    public static final String INDEX_FILE = "/";
    public static final String INDEX_HTML = "/index.html";
    public static final String TEMPLATE_PREFIX = "./templates";
    public static final String STATIC_PREFIX = "./static";
    public static final String HTML_SUFFIX = ".html";
    public static final String ICO_SUFFIX = ".ico";

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final String path;
    private final Status status;
    private final String redirect;

    private Response(String path, Status status, String redirect) {
        this.path = path;
        this.status = status;
        this.redirect = redirect;
    }

    public static Response ok(Request request) {
        return new Response(request.getPath(), Status.OK, EMPTY);
    }

    public static Response found(Request request, String redirect) {
        return new Response(request.getPath(), Status.OK, redirect);
    }

    public void flush(DataOutputStream dataOutputStream) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(getPath());
        header(dataOutputStream, body.length);
        body(dataOutputStream, body);
    }

    private void header(DataOutputStream outputStream, int length) {
        try {
            outputStream.writeBytes("HTTP/1.1 " + status + " \r\n");
            outputStream.writeBytes("Content-Type: " + ContentType.value(path) + ";charset=utf-8\r\n");
            outputStream.writeBytes("Content-Length: " + length + "\r\n");
            redirectIfFound(outputStream);
            outputStream.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void redirectIfFound(DataOutputStream outputStream) throws IOException {
        if (status.equals(Status.FOUND)) {
            outputStream.writeBytes("Location: " + BASE_URL + redirect);
        }
    }

    private void body(DataOutputStream outputStream, byte[] body) {
        try {
            outputStream.write(body, 0, body.length);
            outputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public String getPath() {
        if (!redirect.isEmpty()) {
            return TEMPLATE_PREFIX + redirect;
        }
        if (INDEX_FILE.equals(path)) {
            return TEMPLATE_PREFIX + INDEX_HTML;
        }
        if (!path.contains(".") || path.endsWith(HTML_SUFFIX) || path.endsWith(ICO_SUFFIX)) {
            return TEMPLATE_PREFIX + path;
        }
        return STATIC_PREFIX + path;
    }
}
