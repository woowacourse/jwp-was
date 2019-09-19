package http.response;

import http.AbstractController;
import http.NotSupportedHttpMethodException;
import http.request.HttpMethod;
import http.request.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class IndexController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        log.debug("Begin");
        if(request.getHttpMethod() == HttpMethod.GET) {
            doGet(request, response);
            return;
        }
        if(request.getHttpMethod() == HttpMethod.POST) {
            doPost(request, response);
            return;
        }
        throw new NotSupportedHttpMethodException();
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        byte[] body = new byte[]{};

        body = FileIoUtils.loadFileFromClasspath(String.format("./templates%s", request.getPath()));
        response.response200Header(body.length);
        response.responseBody(body);
    }
}
