package kr.wootecat.dongle.core;

import static kr.wootecat.dongle.http.HttpStatus.*;
import static kr.wootecat.dongle.http.MimeType.*;

import kr.wootecat.dongle.core.handler.HandlerMapping;
import kr.wootecat.dongle.core.handler.HandlerMappings;
import kr.wootecat.dongle.http.HttpStatus;
import kr.wootecat.dongle.http.exception.BadRequestException;
import kr.wootecat.dongle.http.exception.MethodNotAllowedException;
import kr.wootecat.dongle.http.exception.NotFoundException;
import kr.wootecat.dongle.http.exception.UnauthorizedException;
import kr.wootecat.dongle.http.request.HttpRequest;
import kr.wootecat.dongle.http.response.HttpResponse;
import kr.wootecat.dongle.http.session.SessionValidator;

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
            HttpResponse response = HttpResponse.with200Empty();
            sessionValidator.checkRequestSession(request, response);
            HandlerMapping handler = handlerMappings.findHandler(request);
            handler.handle(request, response);
            return response;
        } catch (BadRequestException e) {
            return getHttpResponseWithException(BAD_REQUEST, e);
        } catch (UnauthorizedException e) {
            return getHttpResponseWithException(UNAUTHORIZED, e);
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
