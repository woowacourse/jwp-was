package webserver.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.Controller.exception.MethodNotAllowedException;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.FileNotFoundException;

public class MainController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws FileNotFoundException {
        byte[] staticFile = getStaticFile(httpRequest);
        if (httpResponse.addBody(staticFile)) {
            httpResponse.addStatusLine(httpRequest, "200", "OK");
            httpResponse.addHeader("Content-Type", "text/html;charset=utf-8");
            httpResponse.addHeader("Content-Length", String.valueOf(staticFile.length));
        }
    }

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new MethodNotAllowedException("fail to match method.");
    }
}
