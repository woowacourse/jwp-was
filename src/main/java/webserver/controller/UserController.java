package webserver.controller;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FilePathUtils;
import webserver.response.HttpResponse;
import webserver.response.ResponseBody;
import webserver.response.ResponseHeaders;
import webserver.response.ResponseStatus;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public static Responsive goForm() {
        return request -> {
            ResponseHeaders responseHeaders = new ResponseHeaders();
            String path = request.getPath();

            HttpResponse httpResponse = new HttpResponse(
                    ResponseStatus.OK, responseHeaders, new ResponseBody(path)
            );

            httpResponse.buildGetHeader(FilePathUtils.getExtension(path));
            return httpResponse;
        };
    }

    public static Responsive createUser() {
        return request -> {
            User user = new User(
                    request.getBody("userId"),
                    request.getBody("password"),
                    request.getBody("name"),
                    request.getBody("email")
            );
            logger.debug("user : {}", user);

            ResponseHeaders responseHeaders = new ResponseHeaders();
            HttpResponse httpResponse = new HttpResponse(
                    ResponseStatus.FOUND, responseHeaders, null
            );
            httpResponse.buildRedirectHeader("/index.html");

            return httpResponse;
        };
    }
}
