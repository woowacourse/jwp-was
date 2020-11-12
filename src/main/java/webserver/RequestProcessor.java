package webserver;

import static webserver.http.request.MimeType.*;
import static webserver.http.response.HttpStatus.*;

import java.util.Arrays;

import webserver.exception.BadRequestException;
import webserver.exception.MethodNotAllowedException;
import webserver.exception.NotFoundException;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class RequestProcessor {

    private final HandlerMappings handlerMappings;

    public RequestProcessor() {
        handlerMappings = new HandlerMappings(
                Arrays.asList(
                        new StaticResourceHandlerMapping(),
                        new ServletHandlerMapping(ServletMapper.getInstance())
                )
        );
    }

    public RequestProcessor(HandlerMappings handlerMappings) {
        this.handlerMappings = handlerMappings;
    }

    public HttpResponse response(HttpRequest request) {
        try {
            HttpResponse result = new HttpResponse();
            HandlerMapping handler = handlerMappings.findHandler(request);
            handler.handle(request, result);
            return result;
        } catch (BadRequestException e) {
            return HttpResponse.withContent(BAD_REQUEST, HTML_UTF_8, e.getMessage());
        } catch (NotFoundException e) {
            return HttpResponse.withContent(NOT_FOUND, HTML_UTF_8, e.getMessage());
        } catch (MethodNotAllowedException e) {
            return HttpResponse.withContent(METHOD_NOT_ALLOWED, HTML_UTF_8, e.getMessage());
        } catch (RuntimeException e) {
            return HttpResponse.withContent(INTERNAL_SERVER_ERROR, HTML_UTF_8, e.getMessage());
        }
    }
}
