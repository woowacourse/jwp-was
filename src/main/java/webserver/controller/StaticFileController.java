package webserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.RequestHandler;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseHeader;
import webserver.http.response.HttpResponseLine;
import webserver.http.response.HttpStatus;

public class StaticFileController {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static HttpResponse staticProcess(String filePath, HttpStatus httpStatus) {
        byte[] body;

        try {
            body = FileIoUtils.loadFileFromClasspath(filePath);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ExceptionHandler.processException(e);
        }

        HttpResponseHeader httpResponseHeader = new HttpResponseHeader(new HttpResponseLine(httpStatus));
        httpResponseHeader.add("Content-Type", "text/html;charset=utf-8");
        httpResponseHeader.add("Content-Length", String.valueOf(body.length));

        return new HttpResponse(httpResponseHeader, body);
    }

    public static HttpResponse staticProcess(String filePath) {
        return staticProcess(filePath, HttpStatus.OK);
    }
}
