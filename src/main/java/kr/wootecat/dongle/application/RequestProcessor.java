package kr.wootecat.dongle.application;

import static kr.wootecat.dongle.application.http.HttpStatus.*;
import static kr.wootecat.dongle.application.http.MimeType.*;

import kr.wootecat.dongle.application.http.HttpStatus;
import kr.wootecat.dongle.application.http.exception.BadRequestException;
import kr.wootecat.dongle.application.http.exception.MethodNotAllowedException;
import kr.wootecat.dongle.application.http.exception.NotFoundException;
import kr.wootecat.dongle.application.http.exception.UnauthorizedException;
import kr.wootecat.dongle.application.http.request.HttpRequest;
import kr.wootecat.dongle.application.http.request.handler.HandlerMapping;
import kr.wootecat.dongle.application.http.request.handler.HandlerMappings;
import kr.wootecat.dongle.application.http.response.HttpResponse;

public class RequestProcessor {

    private final HandlerMappings handlerMappings;

    public RequestProcessor(HandlerMappings handlerMappings) {
        this.handlerMappings = handlerMappings;
    }

    public HttpResponse response(HttpRequest request) {
        try {
            HttpResponse response = HttpResponse.with200Empty();
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
