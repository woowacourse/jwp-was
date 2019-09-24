package webserver;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

//파일요청인지 일반요청인지 정하는 클래스
public class Router {
    private static final Logger log = LoggerFactory.getLogger(Router.class);
    private static final ResourceRequestResolver resourceRequestResolver;
    private static final NormalRequestResolver normalRequestResolver;

    static {
        resourceRequestResolver = new ResourceRequestResolver();
        normalRequestResolver = new NormalRequestResolver();
    }

    public static void route(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        if (httpRequest.isFileRequest()) {
            resourceRequestResolver.resolve(httpRequest, httpResponse);
            return;
        }
        normalRequestResolver.resolve(httpRequest, httpResponse);
    }

}
