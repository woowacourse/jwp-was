package webserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.HttpStatus;
import webserver.controller.exception.MethodNotAllowedException;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(AbstractController.class);

    protected static final String HEADER_FIELD_CONTENT_TYPE = "Content-Type";
    protected static final String HEADER_FIELD_CONTENT_LENGTH = "Content-Length";
    protected static final String HEADER_FIELD_LOCATION = "Location";
    protected static final String HEADER_FIELD_HOST = "Host";
    protected static final String CONTENT_TYPE_HTML = "text/html";
    protected static final String CONTENT_TYPE_CSS = "text/css";
    protected static final String HTTP_PROTOCOL = "http://";

    private static final String TEMPLATES_PATH = "./templates";
    private static final String HEADER_FIELD_ACCEPT = "Accept";
    private static final String CHARSET_UTF8 = "charset=utf-8";
    private static final String SEMICOLON = ";";
    private static final String REDIRECT = "redirect:";

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws FileNotFoundException {
        if (httpRequest.isGet()) {
            resolveGet(httpRequest, httpResponse);
        }

        if (httpRequest.isPost()) {
            resolvePost(httpRequest, httpResponse);
        }
    }

    private void resolveGet(HttpRequest httpRequest, HttpResponse httpResponse) throws FileNotFoundException {
        byte[] staticFile = {};
        String filePath = doGet(httpRequest, httpResponse);
        if (httpRequest.containHeaderField(HEADER_FIELD_ACCEPT, CONTENT_TYPE_CSS)) {
            staticFile = getStaticFile(httpRequest);
            httpResponse.addHeader(HEADER_FIELD_CONTENT_TYPE, CONTENT_TYPE_CSS);
        }

        if (httpRequest.containHeaderField(HEADER_FIELD_ACCEPT, CONTENT_TYPE_HTML)) {
            staticFile = getStaticFile(filePath);
            httpResponse.addHeader(HEADER_FIELD_CONTENT_TYPE, CONTENT_TYPE_HTML + SEMICOLON + CHARSET_UTF8);
        }

        forward(httpRequest, httpResponse, staticFile);
    }

    private void resolvePost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String file = doPost(httpRequest, httpResponse);
        if (file.startsWith(REDIRECT)) {
            redirect(httpRequest, httpResponse, file);
        }
    }

    private void forward(HttpRequest httpRequest, HttpResponse httpResponse, byte[] staticFile) {
        httpResponse.addStatusLine(httpRequest, HttpStatus.OK);
        httpResponse.addHeader(HEADER_FIELD_CONTENT_LENGTH, String.valueOf(staticFile.length));
        httpResponse.addBody(staticFile);
    }

    private void redirect(HttpRequest httpRequest, HttpResponse httpResponse, String file) {
        httpResponse.addStatusLine(httpRequest, HttpStatus.FOUND);
        httpResponse.addHeader(HEADER_FIELD_LOCATION, HTTP_PROTOCOL + httpRequest.getHeaderFieldValue(HEADER_FIELD_HOST) + file.substring(REDIRECT.length()));
    }

    protected byte[] getStaticFile(HttpRequest httpRequest) throws FileNotFoundException {
        String file = httpRequest.getSource();
        try {
            return FileIoUtils.loadFileFromClasspath(TEMPLATES_PATH + file);
        } catch (IOException | URISyntaxException e) {
            log.debug("fail to load file", e);
        }
        throw new FileNotFoundException("fail to find file.");
    }

    protected byte[] getStaticFile(String fileName) throws FileNotFoundException {
        try {
            return FileIoUtils.loadFileFromClasspath(TEMPLATES_PATH + fileName);
        } catch (IOException | URISyntaxException e) {
            log.debug("fail to load file", e);
        }
        throw new FileNotFoundException("fail to find file.");
    }


    protected String doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new MethodNotAllowedException();
    }

    protected String doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new MethodNotAllowedException();
    }
}
