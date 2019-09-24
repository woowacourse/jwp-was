package webserver.resolver;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class RequestResolver {

    public static void route(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        if (httpRequest.isFileRequest()) {
            ResourceRequestResolver.resolve(httpRequest, httpResponse);
            return;
        }
        NormalRequestResolver.resolve(httpRequest, httpResponse);
    }
}
