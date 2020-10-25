package webserver.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.AbstractController;
import webserver.controller.ExceptionHandler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class UserController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        logger.info("지원하지 않는 메서드입니다.");
        ExceptionHandler.processException(new NoSuchMethodException("지원하지 않는 메서드입니다."), httpResponse);
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        UserService.join(httpRequest.getBody());
        httpResponse.redirect("/index.html");
    }
}
