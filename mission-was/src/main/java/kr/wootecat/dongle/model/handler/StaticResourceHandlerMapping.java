package kr.wootecat.dongle.model.handler;

import java.io.IOException;
import java.net.URISyntaxException;

import kr.wootecat.dongle.model.http.HttpStatus;
import kr.wootecat.dongle.model.http.MimeType;
import kr.wootecat.dongle.model.http.exception.MethodNotAllowedException;
import kr.wootecat.dongle.model.http.exception.NotFoundException;
import kr.wootecat.dongle.model.http.exception.UnsupportedMimeTypeException;
import kr.wootecat.dongle.model.http.request.HttpRequest;
import kr.wootecat.dongle.model.http.response.HttpResponse;
import kr.wootecat.dongle.utils.FileIoUtils;

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
        } catch (UnsupportedMimeTypeException e) {
            throw new NotFoundException(request.getPath());
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(RUNTIME_EXCEPTION_ERROR_MESSAGE);
        }
    }

    private void makeResourceResponse(HttpResponse response, MimeType mime, byte[] body) {
        response.changeHttpStatus(HttpStatus.OK);
        response.addBody(body, mime);
    }
}
