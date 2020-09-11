package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.User;
import utils.FileIoUtils;
import web.HttpRequest;
import web.HttpResponse;
import web.RequestHeader;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8));
            DataOutputStream dos = new DataOutputStream(out);
            HttpRequest httpRequest = new HttpRequest(br);
            logger.debug(httpRequest.toString());
            new HttpResponse(dos, process(httpRequest));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public byte[] process(HttpRequest httpRequest) {
        RequestHeader requestHeader = httpRequest.getRequestHeader();
        if (requestHeader.isRootPath() || requestHeader.isStaticFile()) {
            return processHtml(httpRequest);
        } else {
            return processApi(httpRequest);
        }
    }

    private byte[] processHtml(HttpRequest httpRequest) {
        String HTML_PATH_PREFIX = "./templates";
        try {
            return FileIoUtils.loadFileFromClasspath(
                    HTML_PATH_PREFIX + httpRequest.getRequestUri());
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    private byte[] processApi(HttpRequest httpRequest) {
        if (httpRequest.isPost()) {
            Map<String, String> body = httpRequest.getRequestBody().getFormData();
            User user = new User(body.get("userId"), body.get("password"), body.get("name"),
                    body.get("email"));
            logger.debug("USER 회원가입 성공 : " + user.toString());
        }
        return null;
    }
}
