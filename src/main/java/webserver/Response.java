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

public class Response {

    private final Status status;
    private final MediaType mediaType;
    private final Map<String, String> headers;
    private final Map<String, String> cookies;
    private final byte[] body;

    private Response(Status status, MediaType mediaType, Map<String, String> headers, Map<String, String> cookies, byte[] body) {
        this.status = status;
        this.mediaType = mediaType;
        this.headers = headers;
        this.cookies = cookies;
        this.body = body;
    }

    public Status getStatus() {
        return status;
    }

    public String getMediaType() {
        if (mediaType == null) {
            return null;
        }
        return mediaType.getContentType();
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

    public byte[] getBody() {
        return body;
    }


    public static final class ResponseBuilder {

        private static final Logger logger = LoggerFactory.getLogger(ResponseBuilder.class);

        private Status status;
        private MediaType mediaType;
        private Map<String, String> headers;
        private Map<String, String> cookies;
        private byte[] body;

        private ResponseBuilder() {
            headers = new HashMap<>();
            cookies = new HashMap<>();
        }

        public static ResponseBuilder createBuilder() {
            return new ResponseBuilder();
        }

        public static ResponseBuilder redirect(String url) {
            return createBuilder()
                    .withStatus(Status.FOUND)
                    .withHeader("Location", url);
        }

        public static ResponseBuilder forward(String url, Object object) {
            try {
                return createBuilder()
                        .withStatus(Status.OK)
                        .withBody(createTemplate(url).apply(object).getBytes());
            } catch (IOException e) {
                logger.error("Error while forward: ", e);
            }
            return null;
        }

        private static Template createTemplate(String url) throws IOException {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);
            handlebars.registerHelper("inc", (Helper<Integer>) (context, options) -> context + 1);

            return handlebars.compile(url);
        }

        public ResponseBuilder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public ResponseBuilder withMediaType(MediaType mediaType) {
            this.mediaType = mediaType;
            return this;
        }

        public ResponseBuilder withHeader(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        public ResponseBuilder withCookie(String key, String value) {
            this.cookies.put(key, value);
            return this;
        }

        public ResponseBuilder withBody(byte[] body) {
            this.body = body;
            return this;
        }

        public Response build() {
            return new Response(status, mediaType, headers, cookies, body);
        }
    }
}
