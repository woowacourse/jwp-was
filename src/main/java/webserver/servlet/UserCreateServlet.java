package webserver.servlet;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class UserCreateServlet extends RequestServlet {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        byte[] body = null;
        Map<String, Object> header = new HashMap<>();
        User user = new User(httpRequest.getBody("userId"), httpRequest.getBody("password"), httpRequest.getBody("name"), httpRequest.getBody("email"));
        logger.debug(">>> User : {}", user);
        int statusCode = 302;
        header.put("location", "/index.html");
        return new HttpResponse(statusCode, header, body);
    }
}
