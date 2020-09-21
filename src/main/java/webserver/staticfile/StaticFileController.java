package webserver.staticfile;

import utils.FileIoUtils;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseBuilder;
import webserver.http.response.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;


public class StaticFileController {

    public static HttpResponse processStaticFile(String resourcePath, HttpStatus httpStatus) {
        String filePath = StaticFileMatcher.findStaticFilePath(resourcePath);
        byte[] body;
        try {
            body = FileIoUtils.loadFileFromClasspath(filePath);
        } catch (IOException | URISyntaxException e) {
            body = HttpStatus.BAD_REQUEST.getMessage().getBytes();
            httpStatus = HttpStatus.NOT_FOUND;
        }

        HttpResponseBuilder httpResponse = new HttpResponseBuilder();
        httpResponse.httpStatus(httpStatus);
        httpResponse.contentType(filePath);
        httpResponse.contentLength(body);
        httpResponse.body(body);
        return httpResponse.build();
    }

    public static HttpResponse processStaticFile(HttpRequest httpRequest) {
        return processStaticFile(httpRequest.getResourcePath(), HttpStatus.OK);
    }
}
