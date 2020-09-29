package webserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.RequestHandler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseHeader;
import webserver.http.response.HttpResponseLine;
import webserver.http.response.HttpStatus;
import webserver.user.UserService;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static HttpResponse doPost(HttpRequest httpRequest) {
        UserService.join(httpRequest.getBody());
        HttpStatus httpStatus = HttpStatus.FOUND;

        byte[] body;
        try {
            body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ExceptionHandler.processException(e);
        }


        HttpResponseLine httpResponseLine = new HttpResponseLine(httpStatus);
        HttpResponseHeader httpResponseHeader = new HttpResponseHeader(httpResponseLine);
        httpResponseHeader.add("Content-Type", "text/html;charset=utf-8");
        httpResponseHeader.add("Content-Length", String.valueOf(body.length));
        return new HttpResponse(httpResponseHeader, body);
    }
}
