package webserver;

import controller.Controller;
import controller.ControllerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.request.HttpRequest;
import web.request.RequestPath;
import web.response.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(in);

            RequestPath requestPath = httpRequest.getRequestPath();
            String requestTarget = requestPath.getTarget();

            HttpResponse httpResponse = new HttpResponse(out);

            Controller controller = ControllerMapper.mappingControllerByPath(requestTarget);
            controller.service(httpRequest, httpResponse);

//            if (requestTarget.equals("/user/create")) {
//                RequestBody requestBody = httpRequest.getRequestBody();
//                User user = User.builder()
//                        .userId(requestBody.getParameterByKey("userId"))
//                        .email(requestBody.getParameterByKey("email"))
//                        .password(requestBody.getParameterByKey("password"))
//                        .name(requestBody.getParameterByKey("name"))
//                        .build();
//                DataBase.addUser(user);
//                logger.debug("New User created! -> {}", user);
//                httpResponse.found("/index.html");
//                return;
//            }
//            httpResponse.ok(requestTarget, httpRequest.getAcceptType());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
