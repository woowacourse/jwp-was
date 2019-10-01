package webserver.response;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HttpStatus;
import webserver.ModelAndView;
import webserver.request.HttpRequest;

import java.io.IOException;
import java.util.Objects;

public class HttpResponse {
    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);

    private static final String HEADER_FIELD_CONTENT_TYPE = "Content-Type";
    private static final String HEADER_FIELD_CONTENT_LENGTH = "Content-Length";
    private static final String HEADER_FIELD_LOCATION = "Location";
    private static final String HTTP_PROTOCOL = "http://";
    private static final String CONTENT_TYPE_HTML = "text/html";
    private static final String CONTENT_TYPE_CSS = "text/css";
    private static final String HEADER_FIELD_HOST = "Host";
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String CHARSET_UTF8 = "charset=utf-8";
    private static final String SEMICOLON = ";";

    private ResponseStatusLine responseStatusLine;
    private ResponseHeader responseHeader;
    private ResponseBody responseBody;

    public HttpResponse() {
        this.responseHeader = new ResponseHeader();
        this.responseBody = new ResponseBody();
    }

    public boolean addBody(byte[] body) {
        return this.responseBody.addBody(body);
    }

    public void addStatusLine(HttpRequest httpRequest, HttpStatus httpStatus) {
        responseStatusLine = ResponseStatusLine.of(httpRequest, httpStatus);
    }

    public boolean addHeader(String key, String value) {
        return responseHeader.addAttribute(key, value);
    }

    public void sendRedirect(HttpRequest httpRequest, String path) {
        addStatusLine(httpRequest, HttpStatus.FOUND);
        addHeader(HEADER_FIELD_LOCATION, HTTP_PROTOCOL + httpRequest.getHeaderFieldValue(HEADER_FIELD_HOST) + path.substring(REDIRECT_PREFIX.length()));
    }

    public void forward(HttpRequest httpRequest, byte[] file) {
        addStatusLine(httpRequest, HttpStatus.OK);
        addHeader(HEADER_FIELD_CONTENT_TYPE, CONTENT_TYPE_CSS);
        addBody(file);
    }

    public void forward(HttpRequest httpRequest, ModelAndView modelAndView) {
        byte[] file = getFile(modelAndView);

        addStatusLine(httpRequest, HttpStatus.OK);
        addHeader(HEADER_FIELD_CONTENT_TYPE, CONTENT_TYPE_HTML + SEMICOLON + CHARSET_UTF8);
        addHeader(HEADER_FIELD_CONTENT_LENGTH, String.valueOf(file.length));
        addBody(file);
    }

    private byte[] getFile(ModelAndView modelAndView) {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        byte[] file = null;
        try {
            String view = modelAndView.getView();
            Template template = handlebars.compile(view);
            file = template.apply(modelAndView.getModel()).getBytes();
        } catch (IOException e) {
            log.debug("fail to compile {}", e.getMessage());
        }
        return file;
    }

    public String responseLine() {
        return responseStatusLine.response();
    }

    public String responseHeader() {
        return responseHeader.response();
    }

    public ResponseBody getResponseBody() {
        return responseBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpResponse that = (HttpResponse) o;
        return Objects.equals(responseStatusLine, that.responseStatusLine) &&
                Objects.equals(responseHeader, that.responseHeader) &&
                Objects.equals(responseBody, that.responseBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseStatusLine, responseHeader, responseBody);
    }
}
