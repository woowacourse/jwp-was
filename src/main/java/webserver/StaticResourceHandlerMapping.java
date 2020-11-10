package webserver;

import java.io.IOException;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.net.HttpHeaders;
import utils.FileIoUtils;
import webserver.exception.MethodNotAllowedException;
import webserver.http.request.HttpRequest;
import webserver.http.request.MimeType;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;

public class StaticResourceHandlerMapping implements HandlerMapping {
    Logger logger = LoggerFactory.getLogger(StaticResourceHandlerMapping.class);

    @Override
    public boolean isSupport(HttpRequest request) {
        return request.isStaticResourceRequest();
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        System.out.println(request.getPath() + "요청 처리하자.");
        System.out.println(request.isGetMethod());
        if (!request.isGetMethod()) {
            throw new MethodNotAllowedException(request.getMethod());
        }
        try {
            final MimeType mime = MimeType.of(request.getPath());
            final byte[] body = FileIoUtils.loadFileFromClasspath(mime.getFilePosition() + request.getPath());
            response.changeHttpStatus(HttpStatus.OK);
            response.addHeader(HttpHeaders.CONTENT_TYPE, mime.getMimeType());
            response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(body.length));
            response.addBody(body);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException();
        }
    }
}
