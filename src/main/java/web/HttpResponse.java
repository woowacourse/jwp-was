package web;

import static web.RequestHeader.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.User;
import utils.FileIoUtils;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String HTML_PATH_PREFIX = "./templates";

    private DataOutputStream dos;
    private List<String> response;
    private byte[] content;

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
        this.response = new ArrayList<>();
    }

    public void process(RequestHeader request) {
        RequestUri uri = request.getRequestUri();
        if (uri.isRootPath() || uri.isStaticFile()) {
            processHtml(uri);
        } else {
            processApi(uri);
        }
    }

    private void processHtml(RequestUri uri) {
        try {
            this.content = FileIoUtils.loadFileFromClasspath(
                    HTML_PATH_PREFIX + uri);
            response200Header();
            responseBody();
            logger.debug(toString());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void processApi(RequestUri path) {
        Map<String, String> params = path.getParameters();
        User user = new User(params.get("userId"), params.get("password"), params.get("name"),
                params.get("email"));
    }

    private void response200Header() {
        response.add("HTTP/1.1 200 OK");
        response.add("Content-Type: text/html;charset=utf-8");
        response.add("Content-Length: " + content.length);

        response.forEach(value -> {
            try {
                dos.writeBytes(value + NEW_LINE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        try {
            dos.writeBytes(NEW_LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void responseBody() {
        try {
            dos.write(content, 0, content.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "header=" + response +
                '}';
    }
}
