package http.model;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import utils.FileIoUtils;

import java.io.IOException;
import java.util.Map;

import static com.google.common.net.HttpHeaders.*;

public class HttpResponse {
    private final static String ROOT_URI = "http://localhost:8080";

    private StatusLine statusLine;
    private HttpHeaders httpHeaders;
    private byte[] body;

    private HttpResponse(Builder builder) {
        this.statusLine = new StatusLine(builder.httpProtocols, builder.httpStatus);
        this.httpHeaders = builder.httpHeaders;
        this.body = builder.body;
    }

    public static Builder builder() {
        return new Builder();
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public String getHeader(String key) {
        return httpHeaders.getHeader(key);
    }

    public byte[] getBody() {
        return body;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public boolean isSameStatus(HttpStatus status) {
        return statusLine.getHttpStatus().equals(status);
    }

    public static class Builder {
        private HttpProtocols httpProtocols;
        private HttpStatus httpStatus;
        private HttpHeaders httpHeaders;
        private byte[] body;

        public Builder() {
            httpHeaders = new HttpHeaders();
        }

        public Builder protocols(HttpProtocols httpProtocols) {
            this.httpProtocols = httpProtocols;
            return this;
        }

        public Builder status(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public Builder body(byte[] body) {
            this.body = body;
            protocols(HttpProtocols.HTTP1_1);
            status(HttpStatus.OK);
            addHeader(CONTENT_TYPE, ContentType.HTML.getType());
            return this;
        }

        public Builder addHeader(String key, String value) {
            httpHeaders.addHeader(key, value);
            return this;
        }

        public Builder forward(String filePath) {
            this.body = FileIoUtils.loadFileFromClasspath(filePath);
            httpHeaders.addHeader(CONTENT_LENGTH, Integer.toString(body.length));
            protocols(HttpProtocols.HTTP1_1);
            status(HttpStatus.OK);
            addHeader(CONTENT_TYPE, ContentType.getContentType(filePath));
            return this;
        }

        public Builder sendRedirect(String url) {
            httpHeaders.addHeader(LOCATION, ROOT_URI + url);
            protocols(HttpProtocols.HTTP1_1);
            status(HttpStatus.FOUND);
            addHeader(CONTENT_TYPE, ContentType.HTML.getType());
            return this;
        }

        public Builder forwardByTemplate(String url, Map<String, Object> model) {
            try {
                Handlebars handlebars = getHandlebars();
                Template template = handlebars.compile(url);
                String page = template.apply(model);
                this.body = page.getBytes();
                protocols(HttpProtocols.HTTP1_1);
                status(HttpStatus.OK);
                addHeader(CONTENT_TYPE, ContentType.HTML.getType());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        public HttpResponse build() {
            return new HttpResponse(this);
        }

        private Handlebars getHandlebars() {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);
            handlebars.registerHelper("idx", ((context, options) -> (Integer) context + 1));
            return handlebars;
        }
    }
}
