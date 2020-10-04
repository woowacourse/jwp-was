package web;

import java.io.IOException;
import java.net.URISyntaxException;

import utils.FileIoUtils;
import utils.RequestUtils;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.HttpStatus;

public abstract class AbstractController implements Controller {
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        if (httpRequest.isGet()) {
            String path = httpRequest.getPath();
            byte[] body = FileIoUtils.findStaticFile(path);
            staticFileResponse(httpRequest, httpResponse, path, body);
        }
        if (httpRequest.isPost()) {
            doPost(httpRequest, httpResponse);
        }
        if (httpRequest.isPut()) {
            doPut(httpRequest, httpResponse);
        }
        if (httpRequest.isDelete()) {
            doDelete(httpRequest, httpResponse);
        }
    }

    private void staticFileResponse(HttpRequest httpRequest, HttpResponse httpResponse, String path, byte[] body) throws
        IOException {
        if (body != null) {
            httpResponse.setHttpStatus(HttpStatus.OK);
            httpResponse.addHeader("Content-Type",
                String.format("text/%s;charset=utf-8", RequestUtils.extractExtension(path)));
            httpResponse.addHeader("Content-Length", String.valueOf(body.length));
            httpResponse.forward(body);
        }
        if (body == null) {
            doGet(httpRequest, httpResponse);
        }
    }

    public abstract void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;

    public abstract void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;

    public abstract void doPut(HttpRequest httpRequest, HttpResponse httpResponse);

    public abstract void doDelete(HttpRequest httpRequest, HttpResponse httpResponse);
}
