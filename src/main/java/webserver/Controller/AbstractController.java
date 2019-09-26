package webserver.Controller;

import utils.FileIoUtils;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.isGet()) {
            doGet(httpRequest, httpResponse);
        }
        if (httpRequest.isPost()) {
            doPost(httpRequest, httpResponse);
        }
    }

    protected byte[] getStaticFile(HttpRequest httpRequest) {
        String file = httpRequest.getSource();
        byte[] body = new byte[0];
        try {
            body = FileIoUtils.loadFileFromClasspath("./templates/" + file);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return body;
    }

    protected abstract void doGet(HttpRequest httpRequest, HttpResponse httpResponse);

    protected abstract void doPost(HttpRequest httpRequest, HttpResponse httpResponse);
}
