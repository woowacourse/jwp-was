package http.response;

import utils.FileIoUtils;
import webserver.support.ContentTypeHandler;
import webserver.support.HandlebarsRenderer;
import webserver.support.ModelAndView;
import webserver.support.PathHandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;

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

    private void setStatusCode(int statusCode) {
        statusLine.setStatusCode(statusCode);
    }

    public String getReasonPhrase() {
        return statusLine.getReasonPhrase();
    }

    private void setReasonPhrase(String reasonPhrase) {
        statusLine.setReasonPhrase(reasonPhrase);
    }

    public byte[] getContentBody() {
        return responseBody.getBody();
    }

    public String getLocation() {
        return responseHeader.getLocation();
    }

    private void setLocation(String location) {
        responseHeader.setLocation(location);
    }

    public Set<Map.Entry<String, Cookie>> getCookie() {
        return responseHeader.getCookie();
    }

    public void setCookie(String key, Cookie cookie) {
        responseHeader.setCookie(key, cookie);
    }

    public void forward(ModelAndView modelAndView) throws IOException, URISyntaxException {
        setStatusCode(200);
        setReasonPhrase("OK");
        setContentType(ContentTypeHandler.type(modelAndView.getView()));
        configureResponseBody(modelAndView);
    }

    private void configureResponseBody(ModelAndView modelAndView) throws IOException, URISyntaxException {
        byte[] body;
        String view = modelAndView.getView();
        Map<String, Object> model = modelAndView.getModels();

        if (model.isEmpty()) {
            String absoluteUrl = PathHandler.path(view);
            body = FileIoUtils.loadFileFromClasspath(absoluteUrl);
            setResponseBody(body);
            return;
        }

        body = HandlebarsRenderer.render(view, model);
        setResponseBody(body);
    }

    public void redirect(String location) {
        setStatusCode(302);
        setReasonPhrase("FOUND");
        setLocation(location);
    }
}
