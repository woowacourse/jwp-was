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
import utils.UrlUtils;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String DEFAULT_USER_BIND_VALUE = null;
    private static final String CREATE_URL = "/user/create";
    private static final String INDEX_HTML_URL = "/index.html";

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

            if (resourcePath.startsWith(CREATE_URL)) {
                if (requestHeader.isGet()) {
                    Map<String, String> requestParam = UrlUtils.extractRequestParamFromUrl(resourcePath);
                    bindRequestParam(requestParam);
                }

                if (requestHeader.isPost()) {
                    Map<String, String> requestParam = UrlUtils.extractRequestParam(requestHeader.getBody());
                    bindRequestParam(requestParam);
                    responseHeader.createResponse302Header(INDEX_HTML_URL);
                    return;
                }
            }
            responseHeader.createResponse200Header(resourcePath);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void bindRequestParam(Map<String, String> requestParam) {
        if (!requestParam.isEmpty()) {
            User user = bindParamsToUser(requestParam);
            DataBase.addUser(user);
        }
    }

    private User bindParamsToUser(Map<String, String> params) {
        return new User(
            params.get("userId"),
            params.get("password"),
            params.get("name"),
            params.get("email")
        );
    }
}
