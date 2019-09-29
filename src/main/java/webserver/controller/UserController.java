package webserver.controller;

import db.DataBase;
import factory.UserFactory;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.controller.request.HttpRequest;
import webserver.controller.response.HttpResponse;

import java.util.Map;
import java.util.Optional;

public class UserController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String SAVE_REDIRECT_URL = "/index.html";

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) {
        String path = NON_STATIC_FILE_PATH + httpRequest.getPath();
        Optional<byte []> maybeBody = FileIoUtils.loadFileFromClasspath(path);

        return HttpResponse.ok(httpRequest,maybeBody.get());
    }

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        logger.debug("userDebug ");
        Map<String, String> requestBodyFields = httpRequest.getBodyFields();
        User user = UserFactory.of(requestBodyFields);
        DataBase.addUser(user);
        return HttpResponse.sendRedirect(httpRequest, SAVE_REDIRECT_URL,false);
    }
}
