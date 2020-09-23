package webserver.controller;

import webserver.utils.FileIoUtils;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;

public class PageController extends AbstractController {
    private static final String CONTENT_TYPE = "Content-Type";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        try {
            FileMapping fileMapping = FileMapping.findByExtension(httpRequest.getUrl());
            httpResponse.writeBody(FileIoUtils.loadFileFromClasspath(fileMapping.getFilePath() + httpRequest.getUrl()));
            httpResponse.setHeader(CONTENT_TYPE, fileMapping.getContentType());
        } catch (IllegalArgumentException e) {
            httpResponse.sendError(HttpStatus.NOT_FOUND, "페이지가 존재하지 않습니다.");
        }
    }
}
