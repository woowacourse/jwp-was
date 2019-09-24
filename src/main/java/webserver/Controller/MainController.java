package webserver.Controller;

import utils.FileIoUtils;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class MainController implements Controller {
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.isGet()) {
            doGet(httpRequest, httpResponse);
        }
    }

    private void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        byte[] staticFile = getStaticFile(httpRequest);

        if (httpResponse.addBody(staticFile)) {
            httpResponse.addHeader(httpRequest, "200", "OK");
        }
    }

    private byte[] getStaticFile(HttpRequest httpRequest) {
        String file = httpRequest.getSource();
        byte[] body = new byte[0];
        try {
            body = FileIoUtils.loadFileFromClasspath("./templates/" + file);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return body;
    }
}
