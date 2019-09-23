package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private HttpStatus status;
    private MediaType contentType;
    private final Map<String, String> headers;
    private final Map<String, String> cookies;
    private byte[] body;

    public HttpResponse() {
        this.status = null;
        this.contentType = null;
        this.headers = new HashMap<>();
        this.cookies = new HashMap<>();
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setContentType(MediaType contentType) {
        this.contentType = contentType;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public Set<String> getHeaderKeys() {
        return headers.keySet();
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public Set<String> getCookieKeys() {
        return cookies.keySet();
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }

    public void addCookie(String key, String value) {
        cookies.put(key, value);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public MediaType getContentType() {
        return contentType;
    }

    public byte[] getBody() {
        return body;
    }

    public void forward(String viewName, Object model) {
        try {
            status = HttpStatus.OK;
            contentType = MediaType.HTML;
            body = createTemplate(viewName).apply(model).getBytes();
        } catch (IOException e) {
            logger.error("Error occurred while forwarding view", e);
        }
    }

    public void redirect(String redirectUrl) {
        status = HttpStatus.FOUND;
        headers.put("Location", redirectUrl);
    }

    private static Template createTemplate(String viewName) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper("inc", (Helper<Integer>) (context, options) -> context + 1);

        return handlebars.compile(viewName);
    }
}
