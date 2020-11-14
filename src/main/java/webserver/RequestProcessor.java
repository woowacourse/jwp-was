package webserver;

import static webserver.servlet.http.MimeType.*;
import static webserver.servlet.http.response.HttpStatus.*;

import webserver.servlet.exception.BadRequestException;
import webserver.servlet.exception.MethodNotAllowedException;
import webserver.servlet.exception.NotFoundException;
import webserver.servlet.exception.UnAuthorizedException;
import webserver.servlet.handler.HandlerMapping;
import webserver.servlet.handler.HandlerMappings;
import webserver.servlet.http.request.HttpRequest;
import webserver.servlet.http.response.HttpResponse;
import webserver.servlet.http.response.HttpStatus;

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
        } catch (UnAuthorizedException e) {
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
