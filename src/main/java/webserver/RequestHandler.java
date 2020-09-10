package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.UrlUtils;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String DEFAULT_USER_BIND_VALUE = null;
    private static final String TEMPLATES_PATH = "./templates";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            RequestHeader requestHeader = new RequestHeader(bufferedReader);
            String resourcePath = UrlUtils.extractResourcePath(requestHeader.getFirstLine());

            if (resourcePath.startsWith("/user/create")) {
                if (requestHeader.isGet()) {
                    Map<String, String> requestParam = UrlUtils.extractRequestParamFromUrl(resourcePath);
                    if (isExistRequestParam(requestParam)) {
                        User user = bindParamsToUser(requestParam);
                        logger.info("user : {}", user);
                    }
                }

                if (requestHeader.isPost()) {
                    String body = requestHeader.getBody();
                    Map<String, String> requestParam = UrlUtils.extractRequestParam(body);
                    if (isExistRequestParam(requestParam)) {
                        User user = bindParamsToUser(requestParam);
                        logger.info("user : {}", user);
                    }
                }

            }

            byte[] body = FileIoUtils.loadFileFromClasspath(TEMPLATES_PATH + UrlUtils.extractFilePath(resourcePath));

            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private boolean isExistRequestParam(Map<String, String> requestParam) {
        return !requestParam.isEmpty();
    }

    private User bindParamsToUser(Map<String, String> params) {
        return new User.Builder()
            .userId(params.getOrDefault("userId", DEFAULT_USER_BIND_VALUE))
            .password(params.getOrDefault("password", DEFAULT_USER_BIND_VALUE))
            .name(params.getOrDefault("name", DEFAULT_USER_BIND_VALUE))
            .email(params.getOrDefault("email", DEFAULT_USER_BIND_VALUE))
            .build();
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
