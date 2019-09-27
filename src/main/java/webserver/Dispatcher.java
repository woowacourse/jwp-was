package webserver;

import utils.FileIoUtils;
import was.HandlerMapping;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class Dispatcher {
    public static void dispatch(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        String fullUrl = httpRequest.getRequestLine().getFullUrl();

        if (fullUrl.startsWith("./")) {
            httpResponse.forward(fullUrl , FileIoUtils.loadFileFromClasspath(fullUrl));
            return;
        }

        HandlerMapping.getHandler(httpRequest).service(httpRequest, httpResponse);
    }
}
