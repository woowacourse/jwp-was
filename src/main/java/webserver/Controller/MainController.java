package webserver.Controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class MainController extends AbstractController {

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        byte[] staticFile = getStaticFile(httpRequest);

        if (httpResponse.addBody(staticFile)) {
            httpResponse.addStatusLine(httpRequest, "200", "OK");
            httpResponse.addHeader("Content-Type", "text/html;charset=utf-8");
            httpResponse.addHeader("Content-Length", String.valueOf(staticFile.length));
        }
    }

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new IllegalArgumentException("404");
    }
}
