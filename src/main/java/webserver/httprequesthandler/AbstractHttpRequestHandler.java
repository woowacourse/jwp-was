package webserver.httprequesthandler;

import controller.exception.MethodNotAllowedException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseStatus;
import http.response.exception.HttpVersionNotSupportedException;

public abstract class AbstractHttpRequestHandler implements HttpRequestHandler {
    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            checkHttpVersion(httpRequest);
            handleInternal(httpRequest, httpResponse);
        } catch (HttpVersionNotSupportedException e) {
            e.printStackTrace();
            httpResponse.setResponseStatus(ResponseStatus.HTTP_VERSION_NOT_SUPPORTED);
        } catch (MethodNotAllowedException e) {
            e.printStackTrace();
            httpResponse.setResponseStatus(ResponseStatus.METHOD_NOT_ALLOWED);
        } catch (RuntimeException e) {
            e.printStackTrace();
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
