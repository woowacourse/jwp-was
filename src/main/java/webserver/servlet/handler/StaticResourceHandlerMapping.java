package webserver.servlet.handler;

import java.io.IOException;
import java.net.URISyntaxException;

import utils.FileIoUtils;
import webserver.servlet.exception.MethodNotAllowedException;
import webserver.servlet.exception.NotFoundException;
import webserver.servlet.exception.ResourceNotFoundException;
import webserver.servlet.exception.UnsupportedMimeTypeException;
import webserver.servlet.http.MimeType;
import webserver.servlet.http.request.HttpRequest;
import webserver.servlet.http.response.HttpResponse;
import webserver.servlet.http.response.HttpStatus;

public class StaticResourceHandlerMapping implements HandlerMapping {

    private static final String RUNTIME_EXCEPTION_ERROR_MESSAGE = "서버 내부 에러가 발생했습니다.";

    @Override
    public boolean isSupport(HttpRequest request) {
        return request.isStaticResourceRequest();
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        if (!request.isGetMethod()) {
            throw new MethodNotAllowedException(request.getMethod());
        }
        try {
            MimeType mime = MimeType.of(request.getPath());
            byte[] body = FileIoUtils.loadFileFromClasspath(mime.getFilePosition() + request.getPath());
            makeResourceResponse(response, mime, body);
        } catch (UnsupportedMimeTypeException | ResourceNotFoundException e) {
            throw new NotFoundException(request.getPath());
        } catch (IOException | URISyntaxException | RuntimeException e) {
            throw new RuntimeException(RUNTIME_EXCEPTION_ERROR_MESSAGE);
        }
    }

    private void makeResourceResponse(HttpResponse response, MimeType mime, byte[] body) {
        response.changeHttpStatus(HttpStatus.OK);
        response.addBody(body, mime);
    }
}
