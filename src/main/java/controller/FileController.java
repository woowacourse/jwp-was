package controller;

import java.io.IOException;
import java.net.URISyntaxException;

import utils.Extension;
import utils.FileIoUtils;
import utils.RequestUtils;
import webserver.HttpHeader;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.HttpStatus;

public class FileController extends AbstractController {
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String path = httpRequest.getPath();
        byte[] body = FileIoUtils.findStaticFile(path);
        if (body != null) {
            httpResponse.setHttpStatus(HttpStatus.OK);
            addContentType(httpResponse, path);
            httpResponse.addHeader(HttpHeader.CONTENT_LENGTH, String.valueOf(body.length));
            httpResponse.forward(body);
        }
        if (body == null) {
            httpResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            httpResponse.error();
        }
    }

    private void addContentType(HttpResponse httpResponse, String path) {
        String extension = RequestUtils.extractExtension(path);
        if (Extension.isJS(extension)) {
            httpResponse.addHeader(HttpHeader.CONTENT_TYPE, "application/javascript;charset=utf-8");
        }
        if (Extension.isCSS(extension) || Extension.isHTML(extension)) {
            httpResponse.addHeader(HttpHeader.CONTENT_TYPE,
                String.format("text/%s;charset=utf-8", RequestUtils.extractExtension(path)));
        }
    }
}
