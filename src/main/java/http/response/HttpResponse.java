package http.response;

import java.io.OutputStream;
import java.util.Map;
import java.util.Objects;

import http.HttpStatus;
import http.HttpVersion;
import view.Model;
import view.ModelAndView;
import view.View;

public class HttpResponse {

    public static final String SET_COOKIE = "Set-Cookie";
    private static final String LOCATION = "Location";

    private final OutputStream outputStream;
    private final ResponseHeader header;
    private final HttpVersion version;
    private HttpStatus status;
    private ModelAndView modelAndView;
    private byte[] body;

    private HttpResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.header = new ResponseHeader();
        this.version = HttpVersion.HTTP1_1;
    }

    public static HttpResponse from(OutputStream outputStream) {
        return new HttpResponse(outputStream);
    }

    public void ok(View view) {
        this.status = HttpStatus.OK;
        this.modelAndView = ModelAndView.from(view);
    }

    public void ok(View view, Model model) {
        this.status = HttpStatus.OK;
        this.modelAndView = ModelAndView.of(model, view);
    }

    public void redirect(String uri) {
        this.status = HttpStatus.FOUND;
        this.header.addHeader(LOCATION, uri);
    }

    public void addHeader(String key, String value) {
        this.header.addHeader(key, value);
    }

    public boolean hasResource() {
        return Objects.nonNull(this.modelAndView) && Objects.nonNull(this.modelAndView.getView());
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public Map<String, String> getHeaders() {
        return header.getHeaders();
    }

    public String getHeader(String key) {
        return header.getHeader(key);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public HttpVersion getVersion() {
        return version;
    }

    public ModelAndView getModelAndView() {
        return modelAndView;
    }

    public String getResource() {
        return this.modelAndView.getView().getUri();
    }

    public byte[] getBody() {
        return body;
    }

    public void setCookie(String value) {
        header.addHeader(SET_COOKIE, value);
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public boolean hasModel() {
        return Objects.nonNull(this.modelAndView) && Objects.nonNull(modelAndView.getModel());
    }

    public void error() {
        this.status = HttpStatus.ERROR;
    }
}
