package controller;

import java.io.IOException;
import java.net.URISyntaxException;

import utils.FileIoUtils;
import utils.RequestUtils;
import webserver.HttpHeader;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.HttpStatus;

public abstract class AbstractController implements Controller {
    public static final String USER_UPDATE = "/user/update";
    public static final String USER_DELETE = "/user/delete";

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
            if (USER_UPDATE.equals(httpRequest.getPath())) {
                doPut(httpRequest, httpResponse);
                return;
            }
            httpResponse.setHttpStatus(HttpStatus.METHOD_NOT_ALLOWED);
            httpResponse.error();
        }
        if (httpRequest.isDelete()) {
            if (USER_DELETE.equals(httpRequest.getPath())) {
                doDelete(httpRequest, httpResponse);
                return;
            }
            httpResponse.setHttpStatus(HttpStatus.METHOD_NOT_ALLOWED);
            httpResponse.error();
        }
    }

    private void staticFileResponse(HttpRequest httpRequest, HttpResponse httpResponse, String path, byte[] body) throws
        IOException {
        if (body != null) {
            httpResponse.setHttpStatus(HttpStatus.OK);
            httpResponse.addHeader(HttpHeader.CONTENT_TYPE,
                String.format("text/%s;charset=utf-8", RequestUtils.extractExtension(path)));
            httpResponse.addHeader(HttpHeader.CONTENT_LENGTH, String.valueOf(body.length));
            httpResponse.forward(body);
        }
        if (body == null) {
            doGet(httpRequest, httpResponse);
        }
    }

    public abstract void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;

    public abstract void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;

    public abstract void doPut(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;

    public abstract void doDelete(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}
