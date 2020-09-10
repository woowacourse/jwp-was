package webserver;

import db.DataBase;
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
    private static final String STATIC_PATH = "./static";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            RequestHeader requestHeader = new RequestHeader(bufferedReader);
            ResponseHeader responseHeader = new ResponseHeader(new DataOutputStream(out));

            String resourcePath = UrlUtils.extractResourcePath(requestHeader.getFirstLine());

            if (resourcePath.startsWith("/user/create")) {
                if (requestHeader.isGet()) {
                    Map<String, String> requestParam = UrlUtils.extractRequestParamFromUrl(resourcePath);
                    if (isExistRequestParam(requestParam)) {
                        User user = bindParamsToUser(requestParam);
                        DataBase.addUser(user);
                        logger.info("user : {}", user);
                    }
                }

                if (requestHeader.isPost()) {
                    String requestBody = requestHeader.getBody();
                    Map<String, String> requestParam = UrlUtils.extractRequestParam(requestBody);
                    if (isExistRequestParam(requestParam)) {
                        User user = bindParamsToUser(requestParam);
                        DataBase.addUser(user);
                        logger.info("user : {}", user);
                    }

                    byte[] responseFile = FileIoUtils.loadFileFromClasspath(TEMPLATES_PATH + "/index.html");
                    responseHeader.createResponse302Header("/index.html");
                    responseHeader.createResponseBody(responseFile);
                    return;
                }
            }

            if (HttpContentType.isHtmlFile(resourcePath)) {
                byte[] responseFile = FileIoUtils.loadFileFromClasspath(TEMPLATES_PATH + UrlUtils.extractFilePath(resourcePath));
                responseHeader.createResponse200Header(responseFile.length, resourcePath);
                responseHeader.createResponseBody(responseFile);
            }

            if (!HttpContentType.isHtmlFile(resourcePath)) {
                byte[] responseStaticFile = FileIoUtils.loadFileFromClasspath(STATIC_PATH + resourcePath);
                responseHeader.createResponse200Header(responseStaticFile.length, resourcePath);
                responseHeader.createResponseBody(responseStaticFile);
            }
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
}
