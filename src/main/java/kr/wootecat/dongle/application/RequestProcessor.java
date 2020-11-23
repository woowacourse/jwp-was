package kr.wootecat.dongle.application;

import static kr.wootecat.dongle.model.http.HttpStatus.*;
import static kr.wootecat.dongle.model.http.MimeType.*;

import kr.wootecat.dongle.model.handler.HandlerMapping;
import kr.wootecat.dongle.model.handler.HandlerMappings;
import kr.wootecat.dongle.model.http.HttpStatus;
import kr.wootecat.dongle.model.http.exception.MethodNotAllowedException;
import kr.wootecat.dongle.model.http.exception.NotFoundException;
import kr.wootecat.dongle.model.http.request.HttpRequest;
import kr.wootecat.dongle.model.http.response.HttpResponse;
import kr.wootecat.dongle.model.http.session.SessionValidator;

public class RequestProcessor {

    private final HandlerMappings handlerMappings;
    private final SessionValidator sessionValidator;

    public RequestProcessor(HandlerMappings handlerMappings,
            SessionValidator sessionValidator) {
        this.handlerMappings = handlerMappings;
        this.sessionValidator = sessionValidator;
    }

    public HttpResponse response(HttpRequest request) {
        try {
            HttpResponse response = HttpResponse.withEmpty(request.isParsedSuccessfully());
            sessionValidator.checkRequestSession(request, response);
            HandlerMapping handler = handlerMappings.findHandler(request);
            handler.handle(request, response);
            return response;
        } catch (NotFoundException e) {
            return getHttpResponseWithException(NOT_FOUND, e);
        } catch (MethodNotAllowedException e) {
            return getHttpResponseWithException(METHOD_NOT_ALLOWED, e);
        } catch (RuntimeException e) {
            return getHttpResponseWithException(INTERNAL_SERVER_ERROR, e);
        }
    }

    private HttpResponse getHttpResponseWithException(HttpStatus httpStatus, Exception ex) {
        return HttpResponse.withContent(httpStatus, HTML_UTF_8, ex.getMessage());
    }
}
