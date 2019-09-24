package webserver;

import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class Dispatcher {
    public static void dispatch(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException, URISyntaxException {
        String fullUrl = httpRequest.getRequestLine().getFullUrl();

        if (fullUrl.startsWith("./")) {
            httpResponse.forward(fullUrl, FileIoUtils.loadFileFromClasspath(fullUrl));
            return;
        }

        HandlerMapping.getHandler(httpRequest).service(httpRequest, httpResponse);
    }
}
