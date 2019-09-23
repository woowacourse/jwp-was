package http.response;

import utils.FileIoUtils;
import webserver.support.ContentTypeHandler;
import webserver.support.PathHandler;

import java.io.IOException;
import java.net.URISyntaxException;

public class Response {
    private StatusLine statusLine;
    private ResponseHeader responseHeader;
    private ResponseBody responseBody;

    public Response(StatusLine statusLine, ResponseHeader responseHeader) {
        this.statusLine = statusLine;
        this.responseHeader = responseHeader;
    }

    private void setResponseBody(byte[] responseBody) {
        this.responseBody = new ResponseBody(responseBody);
    }

    public int getContentLength() {
        return responseBody.getBody().length;
    }

    public String getContentType() {
        return responseHeader.getType();
    }

    private void setContentType(String type) {
        responseHeader.setType(type);
    }

    public boolean isOk() {
        return statusLine.isOk();
    }

    public String getHttpVersion() {
        return statusLine.getHttpVersion();
    }

    public int getStatusCode() {
        return statusLine.getStatusCode();
    }

    public void setStatusCode(int statusCode) {
        statusLine.setStatusCode(statusCode);
    }

    public String getReasonPhrase() {
        return statusLine.getReasonPhrase();
    }

    public void setReasonPhrase(String reasonPhrase) {
        statusLine.setReasonPhrase(reasonPhrase);
    }

    public byte[] getContentBody() {
        return responseBody.getBody();
    }

    public String getLocation() {
        return responseHeader.getLocation();
    }

    public void setLocation(String location) {
        responseHeader.setLocation(location);
    }

    public void configureOKResponse(String url) throws IOException, URISyntaxException {
        setStatusCode(200);
        setReasonPhrase("OK");
        setContentType(ContentTypeHandler.type(url.substring(url.lastIndexOf(".") + 1)));
        configureResponseBody(url);
    }

    private void configureResponseBody(String url) throws IOException, URISyntaxException {
        String absoluteUrl = PathHandler.path(url);
        byte[] body = FileIoUtils.loadFileFromClasspath(absoluteUrl);
        setResponseBody(body);
    }

    public void configureFOUNDResponse(String location) {
        setStatusCode(302);
        setReasonPhrase("FOUND");
        setLocation(location);
    }
}
