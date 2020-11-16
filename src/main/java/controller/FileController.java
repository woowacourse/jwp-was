package controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

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
        if (Objects.nonNull(body)) {
            httpResponse.setHttpStatus(HttpStatus.OK);
            addContentType(httpResponse, path);
            httpResponse.addHeader(HttpHeader.CONTENT_LENGTH, String.valueOf(body.length));
            httpResponse.forward(body);
        }
        if (Objects.isNull(body)) {
            httpResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            httpResponse.error();
        }
    }

    private void addContentType(HttpResponse httpResponse, String path) {
        String extension = RequestUtils.extractExtension(path);
        String contentType = Extension.getContentType(extension);
        if (Objects.nonNull(contentType)) {
            httpResponse.addHeader(HttpHeader.CONTENT_TYPE, contentType);
        }
    }
}
