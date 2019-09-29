package http.response;

import http.cookie.Cookie;
import http.request.Request;
import utils.FileIoUtils;
import webserver.support.ContentTypeHandler;
import webserver.support.HandlebarsRenderer;
import webserver.support.ModelAndView;
import webserver.support.PathHandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static http.session.SessionStorage.JSESSIONID;

public class Response {
    private Request request;
    private StatusLine statusLine;
    private ResponseHeader responseHeader;
    private ResponseBody responseBody;

    public Response(Request request, StatusLine statusLine, ResponseHeader responseHeader) {
        this.request = request;
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

    public List<Cookie> getCookies() {
        return responseHeader.getCookies();
    }

    public void setCookie() {
        if (!request.hasSession()) {
            responseHeader.setCookie(
                    Cookie.builder().name(JSESSIONID).value(request.getSession().getSessionId()).path("/").build());
        }
    }

    public void forward(ModelAndView modelAndView) throws IOException, URISyntaxException {
        setStatusCode(200);
        setReasonPhrase("OK");
        setContentType(ContentTypeHandler.type(modelAndView.getView()));
        setCookie();
        configureResponseBody(modelAndView);
    }

    private void configureResponseBody(ModelAndView modelAndView) throws IOException, URISyntaxException {
        String view = modelAndView.getView();
        Map<String, Object> model = modelAndView.getModels();

        if (model.isEmpty()) {
            String absoluteUrl = PathHandler.path(view);
            byte[] body = FileIoUtils.loadFileFromClasspath(absoluteUrl);
            setResponseBody(body);
            return;
        }

        byte[] body = HandlebarsRenderer.render(view, model);
        setResponseBody(body);
    }

    public void redirect(String location) {
        setStatusCode(302);
        setReasonPhrase("FOUND");
        setLocation(location);
        setCookie();
    }
}
