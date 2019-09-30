package webserver.httphandler;

import controller.exception.MethodNotAllowedException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseStatus;
import http.response.exception.HttpVersionNotSupportedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.httphandler.exception.ResourceNotFoundException;

public abstract class AbstractHttpRequestHandler implements HttpRequestHandler {
    private static final Logger log = LoggerFactory.getLogger(AbstractHttpRequestHandler.class);

    @Override
    public boolean canHandle(String path) {
        return false;
    }

    @Override
    public void handleHttpRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            checkHttpVersion(httpRequest);
            handleInternal(httpRequest, httpResponse);
        } catch (HttpVersionNotSupportedException e) {
            log.error(e.getMessage(), e.getCause());
            httpResponse.setResponseStatus(ResponseStatus.HTTP_VERSION_NOT_SUPPORTED);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e.getCause());
            httpResponse.setResponseStatus(ResponseStatus.NOT_FOUND);
        } catch (MethodNotAllowedException e) {
            log.error(e.getMessage(), e.getCause());
            httpResponse.setResponseStatus(ResponseStatus.METHOD_NOT_ALLOWED);
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e.getCause());
            httpResponse.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void handleInternal(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new MethodNotAllowedException();
    }

    private void checkHttpVersion(HttpRequest httpRequest) {
        if (httpRequest.getHttpVersion().isNotSupportedVersion()) {
            throw new HttpVersionNotSupportedException();
        }
    }
}

