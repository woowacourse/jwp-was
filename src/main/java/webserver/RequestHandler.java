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
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
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
        logger
            .debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection
            .getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(in, StandardCharsets.UTF_8));
            String line = bufferedReader.readLine();

            if (Objects.isNull(line)) {
                return;
            }
            logger.info("line : {}", line);
            String resourcePath = UrlUtils.extractResourcePath(line);
            logger.info("resourcePath : {}", resourcePath);
            Map<String, String> requestParam = UrlUtils.extractRequestParam(resourcePath);

            if (isExistRequestParam(requestParam)) {
                User user = bindParamsToUser(requestParam);
                logger.info("user : {}", user);
            }
            byte[] body = FileIoUtils
                .loadFileFromClasspath(TEMPLATES_PATH + UrlUtils.extractFilePath(resourcePath));

            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private boolean isExistRequestParam(Map<String, String> requestParam) {
        return !Collections.emptyMap().equals(requestParam);
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
