package webserver.Controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class StyleSheetController extends AbstractController {

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        byte[] staticFile = getStaticFile(httpRequest);

        if (httpResponse.addBody(staticFile)) {
            httpResponse.addStatusLine(httpRequest, "200", "OK");
            httpResponse.addHeader("Content-Type", "text/css");
        }
    }

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new IllegalArgumentException("404");
    }
}
